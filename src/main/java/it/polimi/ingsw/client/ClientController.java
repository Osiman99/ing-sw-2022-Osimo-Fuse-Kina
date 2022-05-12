package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;

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
    public void update(Message message) {

    }
}
