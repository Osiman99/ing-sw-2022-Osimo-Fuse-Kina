package it.polimi.ingsw.network;

/**
 * Message used to send to the server the number of mother nature steps chosen by the client.
 */
public class MotherNatureResult extends  Message{

    private static final long serialVersionUID = 8947772997797978711L;
    private final int numMoves;

    public MotherNatureResult(String nickname,  int numMoves){
        super(nickname, MessageType.MOTHERNATURE_RESULT);
        this.numMoves = numMoves;
    }

    public int getNumMoves() {
        return numMoves;
    }

    public String toString(){
        return "Chosen number of steps: " + numMoves;
    }
}
