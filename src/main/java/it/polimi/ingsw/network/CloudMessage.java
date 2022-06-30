package it.polimi.ingsw.network;

/**
 * Message to notify the chosen cloud number by a player.
 */
public class CloudMessage extends Message {

    private static final long serialVersionUID = -3723257555816197045L;
    private final int numCloud;

    public CloudMessage(String nickname,  int numCloud){
        super(nickname, MessageType.CLOUD_MESSAGE);
        this.numCloud = numCloud;
    }

    public int getNumCloud() {
        return numCloud;
    }

    public String toString(){
        return "Chosen cloud number: " + numCloud;
    }

}
