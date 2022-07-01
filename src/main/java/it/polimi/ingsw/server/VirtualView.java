package it.polimi.ingsw.server;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.model.AssistantCard;
import it.polimi.ingsw.server.model.Game;
import java.util.List;

/**
 * Hides the network implementation from the controller.
 * The controller calls methods from this class as if it was a normal view.
 * Instead, a network protocol is used to communicate with the real view on the client side.
 */
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
     * Sends a generic type message to the client.
     *
     * @param genericMessage the message to be sent.
     */
    public void showGenericMessage(String genericMessage) {
        clientHandler.sendMessage(new GenericMessage(genericMessage));
    }

    @Override
    public void showDisconnectionMessage(String nicknameDisconnected, String value) {
        clientHandler.sendMessage(new DisconnectionMessage(nicknameDisconnected, value));
    }

    @Override
    public void onDemandPlayersNumber() {
        clientHandler.sendMessage(new PlayerNumberRequest());
    }

    @Override
    public void onDemandAssistantCard(List<AssistantCard> deck) {
        clientHandler.sendMessage(new AssistantCardRequest(Game.SERVER_NICKNAME, deck));
    }

    @Override
    public void showLoginResult(boolean nicknameAccepted, boolean connectionResult, String nickname) {
        clientHandler.sendMessage(new LoginResult(nicknameAccepted, connectionResult));

    }

    @Override
    public void onDemandNickname() {
        clientHandler.sendMessage(new LoginResult(false, true));
    }

    @Override
    public void onDemandMotherNatureMoves(int maxMoves){
        clientHandler.sendMessage(new MotherNatureRequest(Game.SERVER_NICKNAME, maxMoves));
    }

    @Override
    public void drawBoard(Game game){
        clientHandler.sendMessage(new BoardMessage(Game.SERVER_NICKNAME, game));
    }

    @Override
    public void onDemandCharacterCard(String[] text){
        clientHandler.sendMessage(new CharacterCardsDescriptionResult(Game.SERVER_NICKNAME, text));
    }

    @Override
    public void showErrorAndQuit(String error) {
        clientHandler.sendMessage(new ErrorMessage(Game.SERVER_NICKNAME, error));
    }

    @Override
    public void update(Message message) {
        clientHandler.sendMessage(message);
    }

}
