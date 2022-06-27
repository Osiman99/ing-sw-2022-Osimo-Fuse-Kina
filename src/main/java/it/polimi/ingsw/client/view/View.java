package it.polimi.ingsw.client.view;

import it.polimi.ingsw.server.model.AssistantCard;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Island;
import it.polimi.ingsw.server.model.StudentColor;

import java.util.ArrayList;
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

    void showGenericMessage(String genericMessage);

    void showDisconnectionMessage(String nicknameDisconnected, String value);

    void drawBoard(Game game);

    void onDemandMotherNatureMoves(int maxMoves);

    void onDemandCharacterCard(String[] text);

    void showErrorAndQuit(String error);

    //void showErrorAndQuit(String error);

}
