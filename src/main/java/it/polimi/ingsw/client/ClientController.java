package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.network.messages.ErrorMessage;
import it.polimi.ingsw.network.messages.LoginRequest;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.PlayerNumberReply;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.server.model.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    this class conncets network and view (cli or gui)
 */

public class ClientController implements ViewObserver, Observer {
    private final View view;
    private final ExecutorService taskQueue;
    private Client client;
    private String nickname;


    public ClientController(View view) {
        this.view = view;
        taskQueue = Executors.newSingleThreadExecutor();
    }


    @Override
    public void onUpdateServerInfo(Map<String, String> serverInfo) {

        try {
            client = new ServerHandler(serverInfo.get("address"), Integer.parseInt(serverInfo.get("port")));
            client.addObserver(this);
            client.readMessage();
            client.enablePinger(true);
            taskQueue.execute(view::askNickname);
        } catch (IOException e) {
            taskQueue.execute(() -> view.showLoginResult(false, false, this.nickname));
        }

    }


    @Override
    public void onUpdatePlayersNumber(int playersNumber) {
        client.sendMessage(new PlayerNumberReply(this.nickname, playersNumber));
    }

    @Override
    public void onUpdateNickname(String nickname) {
        this.nickname = nickname;
        client.sendMessage(new LoginRequest(this.nickname));
    }


    @Override
    public void onUpdateCreateMatch(String nickname, int numOfPlayers) {

    }

    @Override
    public void onUpdateAssistantCard(AssistantCard card) {

    }

    @Override
    public void onUpdateMoveStudentToDiningRoom(Student student) {

    }

    @Override
    public void onUpdateMoveStudentToIsland(Student student, Island island) {

    }

    @Override
    public void onUpdateCloud(Cloud cloud) {

    }

    @Override
    public void onUpdateCharacterCard(CharacterCard card) {

    }

    @Override
    public void update(Message message) {
        switch(message.getMessageType()) {
            case PLAYERNUMBER_REQUEST:
                taskQueue.execute(view::askPlayersNumber);
                break;
            case ERROR:
                /*ErrorMessage em = (ErrorMessage) message;
                view.showErrorAndExit(em.getError());
                break;

                 */
        }
    }
}
