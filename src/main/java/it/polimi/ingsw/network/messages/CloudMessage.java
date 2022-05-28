package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.server.model.Game;

public class CloudMessage extends Message {
    private static final long serialVersionUID = -3723257555816197045L;
    private final int numCloud;

    public CloudMessage(String nickname,  int numCloud){
        super(nickname, MessageType.CLOUD);
        this.numCloud = numCloud;
    }

    public int getNumCloud() {
        return numCloud;
    }

    public String toString(){
        return "Chosen cloud number: " + numCloud;
    }

}
