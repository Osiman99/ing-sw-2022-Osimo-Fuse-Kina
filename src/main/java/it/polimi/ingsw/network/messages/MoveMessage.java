package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.server.model.AssistantCard;
import it.polimi.ingsw.server.model.Entrance;
import it.polimi.ingsw.server.model.Student;
import it.polimi.ingsw.server.model.StudentColor;

import java.util.ArrayList;
import java.util.List;

public class MoveMessage extends Message{
    private static final long serialVersionUID = 1235798643242654241L;
    private final List<StudentColor> entranceStudentColors;
    //
    //

    public MoveMessage(String nickname, List<StudentColor> entranceStudentsColors) {
        super(nickname, MessageType.MOVE_STUDENT);
        this.entranceStudentColors = entranceStudentsColors;
    }

    public List<StudentColor> getEntranceStudentColors() {
        return entranceStudentColors;
    }

    public String toString(){
        return "Student in entrance: " + entranceStudentColors;
    }
}
