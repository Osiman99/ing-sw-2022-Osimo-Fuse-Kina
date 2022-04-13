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
    private CharacterCardsStrategy characterCardsStrategy;


    /**
     * Board constructor
     */
    public Board(){
        game = Game.getInstance();
        bag = Bag.getInstance();
        professorsControlledBy = new String[5];
        for (int i = 0; i < 5; i++) {
            professorsControlledBy[i] = "";
        }

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
        joinIslands(island);
    }

    public void moveTowerFromIslandToPlank(Island island){      //la for sarà nel conquerIsland
        for (int i = 0; i < game.getNumPlayers(); i++){
            if (island.getFirstTower().getColor() == game.getPlayers().get(i).getPlayerColor()){
                game.getPlayers().get(i).getPlank().getTowerSpace().addTower(island.getFirstTower());
                island.removeTower();
            }
        }//break
    }

    public void moveMotherNature(int numMoves){
        for (int i = 0; i < islands.size(); i++){
            if (islands.get(i).isMotherNature()){
                islands.get(i).setMotherNature(false);
                islands.get((i+numMoves)%12).setMotherNature(true);
                calculateSupremacy();
                break;
            }
        }
    }

    public void moveProfessor(){
        if (game.getNumPlayers() == 2) {
            for (int i = 0; i < 5; i++) {
                if (game.getPlayers().get(0).getPlank().getDiningRoom()[i].getStudents().size() > game.getPlayers().get(1).getPlank().getDiningRoom()[i].getStudents().size()){
                    professorsControlledBy[i] = game.getPlayers().get(0).getNickname();
                }else if (game.getPlayers().get(1).getPlank().getDiningRoom()[i].getStudents().size() > game.getPlayers().get(0).getPlank().getDiningRoom()[i].getStudents().size()){
                    professorsControlledBy[i] = game.getPlayers().get(1).getNickname();
                }
            }
        }else if (game.getNumPlayers() == 3) {
            for (int i = 0; i < 5; i++) {
                if (game.getPlayers().get(0).getPlank().getDiningRoom()[i].getStudents().size() > game.getPlayers().get(1).getPlank().getDiningRoom()[i].getStudents().size() && game.getPlayers().get(0).getPlank().getDiningRoom()[i].getStudents().size() > game.getPlayers().get(2).getPlank().getDiningRoom()[i].getStudents().size()){
                    professorsControlledBy[i] = game.getPlayers().get(0).getNickname();
                }else if (game.getPlayers().get(1).getPlank().getDiningRoom()[i].getStudents().size() > game.getPlayers().get(0).getPlank().getDiningRoom()[i].getStudents().size() && game.getPlayers().get(1).getPlank().getDiningRoom()[i].getStudents().size() > game.getPlayers().get(2).getPlank().getDiningRoom()[i].getStudents().size()){
                    professorsControlledBy[i] = game.getPlayers().get(1).getNickname();
                }else if (game.getPlayers().get(2).getPlank().getDiningRoom()[i].getStudents().size() > game.getPlayers().get(0).getPlank().getDiningRoom()[i].getStudents().size() && game.getPlayers().get(2).getPlank().getDiningRoom()[i].getStudents().size() > game.getPlayers().get(1).getPlank().getDiningRoom()[i].getStudents().size()){
                    professorsControlledBy[i] = game.getPlayers().get(2).getNickname();
                }
            }
        }
    }

    public void chooseCharacterCardsStrategy(Player player, CharacterCardsStrategy characterCardsStrategy){  //forse farlo in player con le classi non strategy
        this.characterCardsStrategy = characterCardsStrategy;
        characterCardsStrategy.applyEffect(player);
    }

    public void calculateSupremacy(){

    }

    public void joinIslands(Island island){
        for (int i = 0; i < islands.size(); i++){
            if (islands.get(i) == island) {
                if (!(islands.get((i+1)%islands.size()).getFirstTower() == null || islands.get((i+1)%islands.size()).getFirstTower().getColor() != island.getFirstTower().getColor())) {
                    for (int j = 0; j < islands.get((i+1)%islands.size()).getTowers().size(); j++){
                        island.addTower(islands.get((i+1)%islands.size()).getFirstTower());
                        islands.get((i+1)%islands.size()).removeTower();
                    }
                    for (int j = 0; j < islands.get((i+1)%islands.size()).getStudents().size(); j++){
                        island.addStudent(islands.get((i+1)%islands.size()).getFirstStudent());
                        islands.get((i+1)%islands.size()).removeStudent();
                    }
                    islands.remove(islands.get((i+1)%islands.size()));
                }if ((i-1) == -1) {
                    if (!(islands.get(5).getFirstTower() == null || islands.get(5).getFirstTower().getColor() != island.getFirstTower().getColor())) {
                        for (int j = 0; j < islands.get(5).getTowers().size(); j++) {
                            island.addTower(islands.get(5).getFirstTower());
                            islands.remove(island);
                        }
                    }
                }
            }
        }
    }


}