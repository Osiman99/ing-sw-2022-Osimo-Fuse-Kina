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
    private int greenCont;
    private int redCont;
    private int yellowCont;
    private int pinkCont;
    private int blueCont;


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

        greenCont = 0;
        redCont = 0;
        yellowCont = 0;
        pinkCont = 0;
        blueCont = 0;

        /**
         * creation of the 12 islands
         */
        islands = new ArrayList<Island>();
        for (int i = 0; i < NUM_ISLAND_INIT; i++) {
            islands.add(new Island());
        }

        /**
         * in the beginning of the game we have 2 students of each color in the islands
         */
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

        /**
         * we put the 10 students in a random mode in the islands except the island in which is put motherNature
         * and the opposite island in which is motherNature
         */
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

        /**
         * we create the same number of clouds as there are players in the game(2 or 3)
         */
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

    public List<Cloud> getClouds() {
        return clouds;
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

    public String[] getProfessorsControlledBy() {
        return professorsControlledBy;
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
        if (player.getPlank().getTowerSpace().getTowersList().isEmpty()){
            System.out.println("HAI VINTO!");
        }
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
                calculateSupremacy(islands.get((i+numMoves)%12));
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


    public void calculateSupremacy(Island island){
        if (island.getStudents() != null){
            for (int i = 0; i < island.getStudents().size(); i++) {
                if (island.getStudents().get(i).getColor() == StudentColor.GREEN){
                    greenCont++;
                }else if (island.getStudents().get(i).getColor() == StudentColor.RED){
                    redCont++;
                }else if (island.getStudents().get(i).getColor() == StudentColor.YELLOW) {
                    yellowCont++;
                }else if (island.getStudents().get(i).getColor() == StudentColor.PINK) {
                    pinkCont++;
                }else if (island.getStudents().get(i).getColor() == StudentColor.BLUE) {
                    blueCont++;
                }
            }for (int i = 0; i < 5; i++){
                for (int j = 0; j < game.getNumPlayers(); j++){
                    if (professorsControlledBy[i].equals(game.getPlayers().get(j).getNickname())){
                        if (i == 0){
                            game.getPlayers().get(j).setSupremacyCont(game.getPlayers().get(j).getSupremacyCont() + greenCont);
                        }else if (i == 1){
                            game.getPlayers().get(j).setSupremacyCont(game.getPlayers().get(j).getSupremacyCont() + redCont);
                        }else if (i == 2){
                            game.getPlayers().get(j).setSupremacyCont(game.getPlayers().get(j).getSupremacyCont() + yellowCont);
                        }else if (i == 3) {
                            game.getPlayers().get(j).setSupremacyCont(game.getPlayers().get(j).getSupremacyCont() + pinkCont);
                        }else {
                            game.getPlayers().get(j).setSupremacyCont(game.getPlayers().get(j).getSupremacyCont() + blueCont);
                        }
                    }
                }
            } if (island.getTowers() != null){
                for (int i = 0; i < island.getTowers().size(); i++) {
                    for (int j = 0; j < game.getNumPlayers(); j++){
                        if (island.getFirstTower().getColor() == game.getPlayers().get(j).getPlayerColor()) {
                                game.getPlayers().get(j).setSupremacyCont(game.getPlayers().get(j).getSupremacyCont() + 1);
                        }
                    }
                }
            }conquerIsland(island);
        }
    }

    public void conquerIsland(Island island){
        if (island.getTowers() != null){
            if (game.getNumPlayers() == 2){
                for (int i = 0; i < 2; i++){
                    if (game.getPlayers().get(i).getPlayerColor() == island.getFirstTower().getColor()){
                        if (game.getPlayers().get(i).getSupremacyCont() < game.getPlayers().get((i+1)%2).getSupremacyCont()){
                            int numTowers = island.getTowers().size();
                            for (int j = 0; j < numTowers; j++){
                                moveTowerFromIslandToPlank(island);
                                moveTowerFromPlankToIsland(game.getPlayers().get((i+1)%2), island);
                            }joinIslands(island);
                        }
                    }
                }
            }else if (game.getNumPlayers() == 3) {
                for (int i = 0; i < 3; i++){
                    if (game.getPlayers().get(i).getPlayerColor() == island.getFirstTower().getColor()){
                        if (game.getPlayers().get((i+1)%3).getSupremacyCont() > game.getPlayers().get(i).getSupremacyCont() && game.getPlayers().get((i+1)%3).getSupremacyCont() > game.getPlayers().get((i+2)%3).getSupremacyCont()){
                            int numTowers = island.getTowers().size();
                            for (int j = 0; j < numTowers; j++){
                                moveTowerFromIslandToPlank(island);
                                moveTowerFromPlankToIsland(game.getPlayers().get((i+1)%3), island);
                            }joinIslands(island);
                        }else if (game.getPlayers().get((i+2)%3).getSupremacyCont() > game.getPlayers().get(i).getSupremacyCont() && game.getPlayers().get((i+2)%3).getSupremacyCont() > game.getPlayers().get((i+1)%3).getSupremacyCont()){
                            int numTowers = island.getTowers().size();
                            for (int j = 0; j < numTowers; j++){
                                moveTowerFromIslandToPlank(island);
                                moveTowerFromPlankToIsland(game.getPlayers().get((i+2)%3), island);
                            }joinIslands(island);
                        }
                    }
                }
            }
        }else{
            if (game.getNumPlayers() == 2){
                if (game.getPlayers().get(0).getSupremacyCont() > game.getPlayers().get(1).getSupremacyCont()){
                    moveTowerFromPlankToIsland(game.getPlayers().get(0), island);
                    joinIslands(island);
                }else if (game.getPlayers().get(0).getSupremacyCont() < game.getPlayers().get(1).getSupremacyCont()){
                    moveTowerFromPlankToIsland(game.getPlayers().get(1), island);
                    joinIslands(island);
                }
            }else if (game.getNumPlayers() == 3) {
                if (game.getPlayers().get(0).getSupremacyCont() > game.getPlayers().get(1).getSupremacyCont() && game.getPlayers().get(0).getSupremacyCont() > game.getPlayers().get(2).getSupremacyCont()){
                    moveTowerFromPlankToIsland(game.getPlayers().get(0), island);
                    joinIslands(island);
                }else if (game.getPlayers().get(1).getSupremacyCont() > game.getPlayers().get(0).getSupremacyCont() && game.getPlayers().get(1).getSupremacyCont() > game.getPlayers().get(2).getSupremacyCont()){
                    moveTowerFromPlankToIsland(game.getPlayers().get(1), island);
                    joinIslands(island);
                }else if (game.getPlayers().get(2).getSupremacyCont() > game.getPlayers().get(0).getSupremacyCont() && game.getPlayers().get(2).getSupremacyCont() > game.getPlayers().get(1).getSupremacyCont()){
                    moveTowerFromPlankToIsland(game.getPlayers().get(2), island);
                    joinIslands(island);
                }
            }
        } greenCont = 0;
        redCont = 0;
        yellowCont = 0;
        pinkCont = 0;
        blueCont = 0;
        for (int i = 0; i < game.getNumPlayers(); i++) {
            game.getPlayers().get(i).setSupremacyCont(0);
        }
    }

    public void joinIslands(Island island){
        int numIsland = islands.size();
        for (int i = 0; i < numIsland; i++){
            if (islands.get(i) == island) {
                if (!(islands.get((i+1)%islands.size()).getFirstTower() == null || islands.get((i+1)%islands.size()).getFirstTower().getColor() != island.getFirstTower().getColor())) {
                    int numTowersNextIsland = islands.get((i+1)%islands.size()).getTowers().size();
                    for (int j = 0; j < numTowersNextIsland; j++){
                        island.addTower(islands.get((i+1)%islands.size()).getFirstTower());
                        islands.get((i+1)%islands.size()).removeTower();
                    }int numStudentsNextIsland = islands.get((i+1)%islands.size()).getStudents().size();
                    for (int j = 0; j < numStudentsNextIsland; j++){
                        island.addStudent(islands.get((i+1)%islands.size()).getFirstStudent());
                        islands.get((i+1)%islands.size()).removeStudent();
                    }
                    islands.remove(islands.get((i+1)%islands.size()));
                }if ((i-1) == -1) {
                    if (!(islands.get(islands.size()-1).getFirstTower() == null || islands.get(islands.size()-1).getFirstTower().getColor() != island.getFirstTower().getColor())) {
                        int numTowersLastIsland = islands.get(islands.size()-1).getTowers().size();
                        for (int j = 0; j < numTowersLastIsland; j++) {
                            island.addTower(islands.get(islands.size()-1).getFirstTower());
                            islands.get(islands.size()-1).removeTower();
                        }int numStudentsLastIsland = islands.get(islands.size()-1).getStudents().size();
                        for (int j = 0; j < numStudentsLastIsland; j++) {
                            island.addStudent(islands.get(islands.size()-1).getFirstStudent());
                            islands.get(islands.size()-1).removeStudent();
                        }
                    }
                    islands.remove(islands.get(islands.size()-1));
                }else {
                    if (!(islands.get(i-1).getFirstTower() == null || islands.get(i-1).getFirstTower().getColor() != island.getFirstTower().getColor())) {
                        int numTowersPrevIsland = islands.get(i-1).getTowers().size();
                        for (int j = 0; j < numTowersPrevIsland; j++) {
                            island.addTower(islands.get(i-1).getFirstTower());
                            islands.get(i-1).removeTower();
                        }int numStudentsPrevIsland = islands.get(i-1).getStudents().size();
                        for (int j = 0; j < numStudentsPrevIsland; j++) {
                            island.addStudent(islands.get(i-1).getFirstStudent());
                            islands.get(i-1).removeStudent();
                        }
                    }
                    islands.remove(islands.get(i-1));
                }
            }
        }
    }
}