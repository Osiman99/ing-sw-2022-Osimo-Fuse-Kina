package it.polimi.ingsw.observer;

import it.polimi.ingsw.server.model.*;
import java.util.Map;

/**
 * Custom observer interface for views. It supports different types of notification.
 */
public interface ViewObserver {

    /**
     * Sends a message to the server with the player number chosen by the user.
     *
     * @param playersNumber the number of players.
     */
    void onUpdatePlayersNumber(int playersNumber);

    /**
     * Sends a message to the server with the updated nickname.
     *
     * @param nickname the nickname to be sent.
     */
    void onUpdateNickname(String nickname);

    /**
     * Create a new connection to the server with the updated info.
     *
     * @param serverInfo a map of server address and server port.
     */
    void onUpdateServerInfo(Map<String, String> serverInfo);

    /**
     * Sends a message to the server with the choice of the user about his Assistant card.
     *
     * @param card chosen by the player
     */
    void onUpdateAssistantCard(int card);

    /**
     * Sends a message to the server with the choice of the user about the student color he wants to move to the dining room.
     *
     * @param studentColor chosen by the player
     */
    void onUpdateMoveStudentToDiningRoom(StudentColor studentColor);

    /**
     * Sends a message to the server with the choice of the user about the student color he wants to move and to which island.
     *
     * @param studentColor the color of the student chosen by the player
     * @param island chosen by the player
     */
    void onUpdateMoveStudentToIsland(StudentColor studentColor, int island);

    /**
     * Sends a message to the server with the choice of the user about the cloud number he wants to take.
     *
     * @param numCloud the cloud number chosen by the player
     */
    void onUpdateCloud(int numCloud);

    /**
     * Sends a message to the server with the choice of the user about the number of moves he wants to do with mother nature.
     *
     * @param numMoves number of mother nature moves chosen by the player.
     */
    void onUpdateMotherNatureMoves(int numMoves);


    void onUpdateCharacterCardsDescription(String askInterrupted);

    /**
     * Sends a message to the server with the choice of the user about the Character card.
     *
     * @param card character card chosen by the player
     * @param studentColor
     * @param numIsland
     */
    void onUpdateCharacterCard(String card, StudentColor studentColor, int numIsland);

    /**
     * Sends a message to the server if the user types "quit" in order to quit the game.
     *
     * @param quit the string typed by the user
     */
    void onUpdateQuit(String quit);

    /**
     * Sends a message to the server about the first client connected and the modality he wants to play with(n/e).
     *
     * @param mode the modality chosen by the first client.
     */
    void onUpdateMode(String mode);

}
