package it.polimi.ingsw.network;


/**
 * Message to notify an error to the user.
 */
public class ErrorMessage extends Message{

    private static final long serialVersionUID = 3796309698593755714L;
    private final String error;

    public ErrorMessage(String nickname, String error){
        super(nickname, MessageType.ERROR);
        this.error = error;
    }

    public String getError(){
        return error;
    }

    @Override
    public String toString() {
        return "ErrorMessage: Nickname= " + getNickname() + "| Error= " + error;
    }
}
