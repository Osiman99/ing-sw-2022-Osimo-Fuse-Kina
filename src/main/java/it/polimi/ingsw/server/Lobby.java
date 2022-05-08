package it.polimi.ingsw.server;

import it.polimi.ingsw.server.model.Player;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private int ID;
    private int numPlayers;
    private List<Player> players;

    public Lobby(int numPlayers){
        //ID = ?
        this.numPlayers = numPlayers;
        players = new ArrayList<Player>();
    }

    public void addPlayer(String nickname){
        players.add(new Player(nickname));
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}
