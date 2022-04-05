package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Game game;
    private List<Island> islands;
    private List<Cloud> clouds;
    private String professorsControlledBy[];  //da rivedere
    private static Board instance;
    private Bag bag;


    /**
     * Board constructor
     */
    public Board(){
        islands = new ArrayList<Island>();
        clouds = new ArrayList<Cloud>();
        professorsControlledBy = new String[5];
        bag = Bag.getInstance();
    }

    public Bag getBag() {
        return bag;
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

    /**
     * if there are 2 players Cloud gets 3 students from the Bag each round,
     * if there are 3 players it gets 4.
     */
    public void moveStudentsFromBagToClouds() {
        if (game.getNumPlayers() == 2) {
            for (int i = 0; i < 2; i++){
                for (int j = 0; j < 3; j++) {
                    clouds.get(i).addStudent(bag.getFirstStudent());
                    bag.removeStudent();
                }
            }
        }else if (game.getNumPlayers() == 3) {
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 4; j++) {
                    clouds.get(i).addStudent(bag.getFirstStudent());
                    bag.removeStudent();
                }
            }
        }
    }

    public void moveTowerFromPlankToIsland(Player player, Island island){
        island.addTower(player.getPlank().getTowerSpace().getFirstTower());
        player.getPlank().getTowerSpace().removeTower();
    }

    public void addIsland(Island island){
        islands.add(island);
    }

    public void addCloud(Cloud cloud){
        clouds.add(cloud);
    }

    public void moveTowerFromIslandToPlank(){

    }


}