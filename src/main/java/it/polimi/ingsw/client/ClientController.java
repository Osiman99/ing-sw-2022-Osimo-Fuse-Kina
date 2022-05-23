package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.server.model.*;

import java.io.IOException;
import java.util.List;
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
    public void onUpdateAssistantCard(int card) {
        client.sendMessage(new AssistantCardResult(this.nickname, card));
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
            case GENERIC_MESSAGE:
                taskQueue.execute(() -> view.showGenericMessage(((GenericMessage) message).getMessage()));
                break;
            case LOGIN_REPLY:
                LoginReply loginReply = (LoginReply) message;
                taskQueue.execute(() -> view.showLoginResult(loginReply.isNicknameAccepted(), loginReply.isConnectionSuccessful(), this.nickname));
                break;
            case ASSISTANTCARD_REQUEST:
                AssistantCardRequest assistantCardRequest = (AssistantCardRequest) message;
                taskQueue.execute(()-> view.askAssistantCard(assistantCardRequest.getDeck()));
                break;
            case BOARD:
                BoardMessage boardMessage = (BoardMessage) message;
                taskQueue.execute(()-> view.drawBoard(boardMessage.getGame()));
                break;

                /*case ERROR:
                ErrorMessage errorMessage = (ErrorMessage) message;
                view.showErrorAndQuit(errorMessage.getError());
                break;
                */
            default:
                throw new IllegalStateException("Unexpected value: " + message.getMessageType());
        }
    }
}
