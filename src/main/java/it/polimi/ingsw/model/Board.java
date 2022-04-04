package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Game game;
    private static int numIslands;   //si potrebbe anche non mettere static perchè Board è singleton
    private List<Island> islands;
    private List<Cloud> clouds;
    private Player professorsControlledBy[];  //da rivedere
    private static Board instance;
    private Bag bag;

    /**
     * Board constructor
     */
    public Board(){
        islands = new ArrayList<Island>();
        clouds = new ArrayList<Cloud>();
        professorsControlledBy = new Player[5];  //da rivedere
        numIslands = 12;
        bag = Bag.getInstance();
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

    /**
     * it moves students from cloud to entrance
     * @param player
     * @param cloud
     */
    public void moveStudentsFromCloudToEntrance(Player player, Cloud cloud) {
        for (int i = 0; i < cloud.getStudentsSize(); i++) {
            player.addStudentToEntrance(cloud.getFirstStudent());
            cloud.removeStudent();
        }
    }

    public void moveStudentFromEntranceToDiningRoom(Player player, Student moving_student){    //la for si fa nel main (GUI)
        player.addStudentToDiningRoom(moving_student);
    }

    public void moveStudentFromEntranceToIsland(Player player, Island island, Student moving_student){    //la for si fa nel main (GUI)
        player.addStudentToIsland(moving_student, island);
    }




}