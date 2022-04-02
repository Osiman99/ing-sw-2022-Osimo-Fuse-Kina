package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int numIslands;
    private List<Island> islands;
    private List<Cloud> clouds;
    private Player professorsControlledBy[];
    private static Board instance;

    /**
     * Board constructor
     */
    public Board(){
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
    public static Board getInstance(){
        if (instance == null){
            instance = new Board();
        }
        return instance;
    }


}
