package it.polimi.ingsw.network.messages;

public class EndMessage extends Message {
    private static final long serialVersionUID = -4383477752986220587L;
    private String quit;
    //

    public EndMessage(String nickname, String quit) {
        super(nickname, MessageType.END_MESSAGE);
        this.quit = quit;
    }

    public String getQuit() {
        return quit;
    }

    public String toString(){
        return getNickname() + " wants to quit";
    }
}
