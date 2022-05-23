package it.polimi.ingsw.server;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.model.AssistantCard;
import it.polimi.ingsw.server.model.Game;

import java.util.List;

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

    public void showGenericMessage(String genericMessage) {
        clientHandler.sendMessage(new GenericMessage(genericMessage));
    }

    @Override
    public void showDisconnectionMessage(String nicknameDisconnected, String value) {
        clientHandler.sendMessage(new DisconnectionMessage(nicknameDisconnected, value));
    }

    @Override
    public void askPlayersNumber() {
        clientHandler.sendMessage(new PlayerNumberRequest());
    }

    @Override
    public void askAssistantCard(List<AssistantCard> deck) {
        clientHandler.sendMessage(new AssistantCardRequest(Game.SERVER_NICKNAME, deck));
    }

    @Override
    public void showLoginResult(boolean nicknameAccepted, boolean connectionResult, String nickname) {
        clientHandler.sendMessage(new LoginReply(nicknameAccepted, connectionResult));

    }

    @Override
    public void askNickname() {
        clientHandler.sendMessage(new LoginReply(false, true));
    }

    public void drawBoard(Game game){
        clientHandler.sendMessage(new BoardMessage(Game.SERVER_NICKNAME, game));
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
