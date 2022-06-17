package it.polimi.ingsw.server;

import it.polimi.ingsw.server.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * it's a room where the players are put before a match(to manage multiple matches)
 */
public class Lobby {
    private int ID;
    private int numPlayers;
    private int realTimeNumPlayer;
    private List<Player> players;
    private boolean full;

    public Lobby(){
        //ID = ?
        players = new ArrayList<Player>();
        realTimeNumPlayer = 0;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void addPlayer(String nickname){
        players.add(new Player(nickname));
        increaseRealTimeNumPlayer();
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public boolean isFull() {
        return full;
    }


    public void setFull(boolean full) {
        this.full = full;
    }



    public void increaseRealTimeNumPlayer(){
        if (realTimeNumPlayer == 0){
            realTimeNumPlayer = 1;
        }else if (realTimeNumPlayer == 1 && numPlayers == 2){
            realTimeNumPlayer = 2;
        }else if (realTimeNumPlayer == 2 && numPlayers == 3){
            realTimeNumPlayer = 3;
            setFull(true);
        }
    }

}
