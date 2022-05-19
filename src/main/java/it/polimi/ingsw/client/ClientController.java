package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.server.model.*;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientController implements ViewObserver, Observer {
    private final View view;
    private Client client;
    private String nickname;
    private final ExecutorService taskQueue;


    public ClientController(View view) {
        this.view = view;
        taskQueue = Executors.newSingleThreadExecutor();
    }


    @Override
    public void onUpdatePlayersNumber(int playersNumber) {

    }

    /**
     * Sends a message to the server with the updated nickname.
     * The nickname is also stored locally for later usages.
     *
     * @param nickname the nickname to be sent.
     */
    @Override
    public void onUpdateNickname(String nickname) {
        this.nickname = nickname;
        //client.sendMessage(new LoginRequest(this.nickname));
    }

    @Override
    public void onUpdateServerInfo(Map<String, String> serverInfo) {

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

    }
}
