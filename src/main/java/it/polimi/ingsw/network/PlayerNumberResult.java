package it.polimi.ingsw.network;

/**
 * Message used to send to the server the number of players picked by the client.
 */
public class PlayerNumberResult extends Message{

    private static final long serialVersionUID = -4419241297635925047L;
    private final int playerNumber;

    public PlayerNumberResult(String nickname, int playerNumber) {
        super(nickname, MessageType.PLAYERNUMBER_RESULT);
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    @Override
    public String toString() {
        return "PlayerNumberReply: Nickname=" + getNickname() + "| playerNumber= " + playerNumber;
    }
}
