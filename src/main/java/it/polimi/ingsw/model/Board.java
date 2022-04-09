package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board {
    private Game game;
    private List<Island> islands;
    private List<Cloud> clouds;
    private String professorsControlledBy[];  //da rivedere
    private static Board instance;
    private Bag bag;
    private Random random;
    private int randomInt;
    private final int NUM_ISLAND_INIT = 12;
    private ArrayList<Student> studentsIslandInit;


    /**
     * Board constructor
     */
    public Board(){
        game = Game.getInstance();
        bag = Bag.getInstance();
        professorsControlledBy = new String[5];

        islands = new ArrayList<Island>();
        for (int i = 0; i < NUM_ISLAND_INIT; i++) {
            islands.add(new Island());
        }
        studentsIslandInit = new ArrayList<Student>();
        for (int i = 0; i < 2; i++) {
            studentsIslandInit.add(new Student(StudentColor.GREEN));
        }
        for (int i = 0; i < 2; i++) {
            studentsIslandInit.add(new Student(StudentColor.RED));
        }
        for (int i = 0; i < 2; i++) {
            studentsIslandInit.add(new Student(StudentColor.YELLOW));
        }
        for (int i = 0; i < 2; i++) {
            studentsIslandInit.add(new Student(StudentColor.PINK));
        }
        for (int i = 0; i < 2; i++) {
            studentsIslandInit.add(new Student(StudentColor.BLUE));
        }
        Collections.shuffle(studentsIslandInit);
        random = new Random();
        randomInt = random.nextInt(NUM_ISLAND_INIT-1);
        islands.get(randomInt).setMotherNature(true);
        for (int i = 0; i < NUM_ISLAND_INIT; i++){
            if (!(islands.get(i) == islands.get(randomInt) || islands.get(i) == islands.get((randomInt+6)%12))){
                islands.get(i).addStudent(studentsIslandInit.get(0));
                studentsIslandInit.remove(0);
            }
        }

        clouds = new ArrayList<Cloud>();
        if (game.getNumPlayers() == 2) {
            for (int i = 0; i < 2; i++) {
                clouds.add(new Cloud());
            }
        }else if (game.getNumPlayers() == 3){
            for (int i = 0; i < 3; i++){
                clouds.add(new Cloud());
            }
        }
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
            for (int i = 0; i < game.getNumPlayers(); i++){
                for (int j = 0; j < 3; j++) {
                    clouds.get(i).addStudent(bag.getFirstStudent());
                    bag.removeStudent();
                }
            }
        }else if (game.getNumPlayers() == 3) {
            for (int i = 0; i < game.getNumPlayers(); i++){
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

    public void moveTowerFromIslandToPlank(Island island){      //la for sarà nel conquerIsland
        for (int i = 0; i < game.getNumPlayers(); i++){
            if (island.getFirstTower().getColor() == game.getPlayers().get(i).getPlank().getTowerSpace().getFirstTower().getColor()){
                game.getPlayers().get(i).getPlank().getTowerSpace().addTower(island.getFirstTower());
                island.removeTower();
            }
        }

    }

    public void moveMotherNature(int numMoves){
        for (int i = 0; i < islands.size(); i++){
            if (islands.get(i).isMotherNature()){
                islands.get(i).setMotherNature(false);
                islands.get((i+numMoves)%12).setMotherNature(true);
                //calculateSupremacy
                break;
            }
        }
    }


}