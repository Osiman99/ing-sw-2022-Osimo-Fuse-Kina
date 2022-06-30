package it.polimi.ingsw.network;

import it.polimi.ingsw.server.model.StudentColor;

/**
 * Message to notify which student was chosen to move.
 */
public class MoveMessage extends Message {

    private static final long serialVersionUID = -7726935391031946027L;
    private final StudentColor studentColor;
    private final int numIsland;


    public MoveMessage(String nickname, StudentColor studentColor, int numIsland) {
        super(nickname, MessageType.MOVE_STUDENT);
        this.studentColor = studentColor;
        this.numIsland = numIsland;
    }

    public StudentColor getStudentColor() {
        return studentColor;
    }

    public int getNumIsland() {
        return numIsland;
    }

    public String toString() {
        return "Chosen student to move:" + studentColor;
    }
}
