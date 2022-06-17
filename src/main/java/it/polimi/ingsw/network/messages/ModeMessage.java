package it.polimi.ingsw.network.messages;

public class ModeMessage extends Message{
    private static final long serialVersionUID = -8399224133726133105L;
    private String mode;
    //

    public ModeMessage(String nickname, String mode) {
        super(nickname, MessageType.MODE_MESSAGE);
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public String toString(){
        return getNickname() + " chose the " + mode + " mode";
    }

}
