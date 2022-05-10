package it.polimi.ingsw.server;

import it.polimi.ingsw.server.model.Player;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private int ID;
    private int numPlayers;
    private int realTimeNumPlayer;
    private List<Player> players;
    private boolean full;

    public Lobby(int numPlayers){
        //ID = ?
        this.numPlayers = numPlayers;
        players = new ArrayList<Player>();
        realTimeNumPlayer = 0;
    }

    public void addPlayer(String nickname){
        players.add(new Player(nickname));
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
        }else if (realTimeNumPlayer == 1){
            realTimeNumPlayer = 2;
        }else if (realTimeNumPlayer == 2 && numPlayers == 3){
            realTimeNumPlayer = 3;
            setFull(true);
        }
    }

}
