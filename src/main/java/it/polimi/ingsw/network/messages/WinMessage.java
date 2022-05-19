package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.server.model.Game;

/**
 * Message to notify that a player has won the game.
 */
public class WinMessage extends Message{

    private static final long serialVersionUID = 4516402749630283459L;
    private final String winnerNickname;

    public String getWinnerNickname(){
        return winnerNickname;
    }

    public WinMessage(String winnerNickname){
        super(Game.SERVER_NICKNAME, MessageType.WIN_FX);
        this.winnerNickname = winnerNickname;
    }

    @Override
    public String toString() {
        return "WinMessage: Nickname= " + getNickname() + "| winner=" + winnerNickname;
    }
}
