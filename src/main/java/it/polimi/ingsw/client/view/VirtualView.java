package it.polimi.ingsw.client.view;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.PlayerNumberRequest;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientHandler;

public class VirtualView implements View, Observer {

    private final ClientHandler clientHandler;

    /**
     * Default constructor.
     *
     * @param clientHandler the client handler the virtual view must send messages to.
     */
    public VirtualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    /**
     * Returns the client handler associated to a client.
     *
     * @return client handler.
     */
    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    @Override
    public void askPlayersNumber() {
        clientHandler.sendMessage(new PlayerNumberRequest());
    }


    /**
     * Receives an update message from the model.
     * The message is sent over the network to the client.
     * The proper action based on the message type will be taken by the real view on the client.
     *
     * @param message the update message.
     */
    @Override
    public void update(Message message) {
        clientHandler.sendMessage(message);
    }
}
