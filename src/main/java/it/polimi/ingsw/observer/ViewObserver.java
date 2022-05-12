package it.polimi.ingsw.observer;

import it.polimi.ingsw.server.model.*;

public interface ViewObserver {

    void onUpdateCreateMatch(String nickname, int numOfPlayers);

    void onUpdateJoinMatch(String nickname, int matchID);

    void onUpdateAssistantCard(AssistantCard card);

    void onUpdateMoveStudentToDiningRoom(Student student);

    void onUpdateMoveStudentToIsland(Student student, Island island);

    void onUpdateMoveMotherNature(int value);

    void onUpdateCloud(Cloud cloud);

    void onUpdateCharacterCard(CharacterCard card);

}
