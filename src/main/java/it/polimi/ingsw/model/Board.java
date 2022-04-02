package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Gameboard {
    private int numIslands;
    private List<Island> islands;
    private List<Cloud> clouds;
    private Player professorsControlledBy[];
    private static Gameboard instance;

    /**
     * Gameboard constructor
     */
    public Gameboard(){
        islands = new ArrayList<Island>();
        clouds = new ArrayList<Cloud>();
        professorsControlledBy = new Player[5];
        numIslands = 12;
    }

    /**
     * Singleton,create the object only if it does not exist
     *
     * @return
     */
    public static Gameboard getInstance(){
        if (instance == null){
            instance = new Gameboard();
        }
        return instance;
    }


}
