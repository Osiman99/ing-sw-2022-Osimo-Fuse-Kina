package it.polimi.ingsw.client.view;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientHandler;

public class VirtualView implements View, Observer {

    private final ClientHandler clientHandler;

    /*
     * Default constructor
     *
     */
    public VirtualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    @Override
    public void askCreateMatch() {

    }

    @Override
    public void askJoinMatch() {

    }

    @Override
    public void askAssistantCard() {

    }

    @Override
    public void askCharacterCard() {

    }

    @Override
    public void askMoveStudentToDiningRoom() {

    }

    @Override
    public void askMoveStudentToIsland() {

    }

    @Override
    public void askMoveMotherNature() {

    }

    @Override
    public void askCloud() {

    }


    @Override
    public void update(Message message) {

    }
}
