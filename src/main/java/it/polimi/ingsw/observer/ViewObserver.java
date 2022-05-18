package it.polimi.ingsw.observer;

import it.polimi.ingsw.server.model.*;

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

    void onUpdateCreateMatch(String nickname, int numOfPlayers);

    void onUpdateAssistantCard(AssistantCard card);

    void onUpdateMoveStudentToDiningRoom(Student student);

    void onUpdateMoveStudentToIsland(Student student, Island island);

    void onUpdateCloud(Cloud cloud);

    void onUpdateCharacterCard(CharacterCard card);

}
