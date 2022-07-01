package it.polimi.ingsw.client.view;

import it.polimi.ingsw.server.model.AssistantCard;
import it.polimi.ingsw.server.model.Game;

import java.util.List;

public interface View {

    /**
     * The player has to choose a nickname.
     */
    void onDemandNickname();

    /**
     * choose the players number.
     */
    void onDemandPlayersNumber();

    void onDemandAssistantCard(List<AssistantCard> deck);

    /**
     * Shows to the user if the Login succeeded.
     *
     * @param nicknameAccepted     indicates if the chosen nickname has been accepted.
     * @param connection indicates if the connection has been successful.
     * @param nickname             the nickname of the player to be greeted.
     */
    void showLoginResult(boolean nicknameAccepted, boolean connection, String nickname);

    /**
     * Shows to the user the GenericMessage content.
     *
     * @param genericMessage the message string
     */
    void showGenericMessage(String genericMessage);

    /**
     * Shows to the user if another user is disconnected.
     *
     * @param nicknameDisconnected the disconnected player
     * @param value the message string
     */
    void showDisconnectionMessage(String nicknameDisconnected, String value);

    /**
     * Shows to the user the updated board.
     *
     * @param game the game instance
     */
    void drawBoard(Game game);

    /**
     * Shows the maximum number of steps that mother nature can make this turn
     *
     * @param maxMoves the number of maximum steps
     */
    void onDemandMotherNatureMoves(int maxMoves);

    /**
     * Shows to the user the effects of the three active character cards.
     *
     * @param text the three chosen character card descriptions.
     */
    void onDemandCharacterCard(String[] text);

    /**
     * Shows to the user the error message.
     *
     * @param error the string error message
     */
    void showErrorAndQuit(String error);
}
