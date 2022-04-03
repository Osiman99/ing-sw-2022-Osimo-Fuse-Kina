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
    public void moveStudentsFromBagToCloud(Cloud cloud) {
        if (this.game.getNumPlayers() == 2) {
            for (int i = 0; i < 3; i++) {
                cloud.addStudent(this.bag.getFirstStudent());
                bag.removeStudent();
            }
        } else if (this.game.getNumPlayers() == 3) {
            for (int i = 0; i < 4; i++) {
                cloud.addStudent(this.bag.getFirstStudent());
                bag.removeStudent();
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

    public void moveStudentsFromEntranceToDiningRoom(Player player, List<Student> moving_students){  //la Lista moving_students gli arriva dal main (il player deve scegliere) che fa getStudentsForDiningRoom dalla classe Entrance
        for(int i = 0; i < moving_students.size(); i++){
            player.addStudentToDiningRoom(moving_students.get(i));
        }
    }


}