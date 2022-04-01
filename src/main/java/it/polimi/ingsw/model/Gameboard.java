package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Gameboard {
    private int numIslands;
    private List<Island> islands;
    private List<Cloud> clouds;
    private Player professorsControlledBy[];
    private static Gameboard instance;

    public Gameboard(){
        islands = new ArrayList<Island>();
        clouds = new ArrayList<Cloud>();
        professorsControlledBy = new Player[5];
        numIslands = 12;
    }

    public static Gameboard getInstance(){
        if (instance == null){
            instance = new Gameboard();
        }
        return instance;
    }


}
