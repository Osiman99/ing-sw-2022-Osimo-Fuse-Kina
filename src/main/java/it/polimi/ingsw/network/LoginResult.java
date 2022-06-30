package it.polimi.ingsw.network;

import it.polimi.ingsw.server.model.Game;

/**
 * Message used to confirm or discard a login request of a client.
 */
public class LoginResult extends Message{

    private static final long serialVersionUID = -1423312065079102467L;
    private final boolean nicknameAccepted;
    private final boolean connectionSuccessful;

    public LoginResult(boolean nicknameAccepted, boolean connectionSuccessful){
        super(Game.SERVER_NICKNAME, MessageType.LOGIN_RESULT);
        this.nicknameAccepted = nicknameAccepted;
        this.connectionSuccessful = connectionSuccessful;
    }

    public boolean isConnectionSuccessful() {
        return connectionSuccessful;
    }

    public boolean isNicknameAccepted() {
        return nicknameAccepted;
    }

    public String toString(){
        return "LoginReply: Nickname=" + getNickname() + "| nicknameAccepted=" + nicknameAccepted + "| connection=" + connectionSuccessful;
    }
}
