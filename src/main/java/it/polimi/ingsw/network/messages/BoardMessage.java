package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.server.model.Game;

import java.io.Serializable;

public class BoardMessage extends Message {
    private static final long serialVersionUID = -1470453323300253394L;
    private final Game game;
    //
    //

    public BoardMessage(String nickname, Game game) {
        super(nickname, MessageType.BOARD);
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public String toString(){
        return "Game: " + game;
    }
}
