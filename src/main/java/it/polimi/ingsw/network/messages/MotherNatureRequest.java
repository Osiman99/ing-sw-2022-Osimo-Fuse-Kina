package it.polimi.ingsw.network.messages;

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
