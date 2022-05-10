package it.polimi.ingsw.network.messages;


/**
 * Message used to ask the client the maximum number of players of the game.
 */
public class PlayerNumberRequest extends Message{

    private static final long serialVersionUID = -2155556142315548857L;

    public PlayerNumberRequest() {
        super(Game.SERVER_NICKNAME, MessageType.PLAYERNUMBER_REQUEST);
    }

    @Override
    public String toString() {
        return "PlayerNumberRequest{" +
                "nickname=" + getNickname() +
                '}';
    }
}
