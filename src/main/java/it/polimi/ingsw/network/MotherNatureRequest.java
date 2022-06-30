package it.polimi.ingsw.network;

/**
 * Message used to notify the client the maximum number of steps that mother nature can do.
 */
public class MotherNatureRequest extends Message{

    private static final long serialVersionUID = 8947772997797978711L;
    private final int maxMoves;

    public MotherNatureRequest(String nickname,  int maxMoves){
        super(nickname, MessageType.MOTHERNATURE_REQUEST);
        this.maxMoves = maxMoves;
    }

    public int getMaxMoves() {
        return maxMoves;
    }

    public String toString(){
        return "Max number of steps: " + maxMoves;
    }
}
