package it.polimi.ingsw.observer;

import it.polimi.ingsw.server.model.*;

import java.util.List;
import java.util.Map;

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

    void onUpdateServerInfo(Map<String, String> serverInfo);

    void onUpdateAssistantCard(int card);

    void onUpdateMoveStudentToDiningRoom(Student student);

    void onUpdateMoveStudentToIsland(Student student, Island island);

    void onUpdateCloud(Cloud cloud);

    void onUpdateCharacterCard(CharacterCard card);

}
