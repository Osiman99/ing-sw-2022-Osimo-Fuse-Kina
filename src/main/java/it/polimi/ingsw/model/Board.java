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
        //isEmpty? if yes finisce il gioco
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
                calculateSupremacy(islands.get(i));
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
                            for (int j = 0; j < island.getTowers().size(); j++){
                                moveTowerFromIslandToPlank(island);
                                moveTowerFromPlankToIsland(game.getPlayers().get((i+1)%2), island);
                            }
                        }
                    }
                }
            }else if (game.getNumPlayers() == 3) {
                for (int i = 0; i < 3; i++){
                    if (game.getPlayers().get(i).getPlayerColor() == island.getFirstTower().getColor()){
                        if (game.getPlayers().get(i).getSupremacyCont() < game.getPlayers().get((i+1)%3).getSupremacyCont()){
                            for (int j = 0; j < island.getTowers().size(); j++){
                                moveTowerFromIslandToPlank(island);
                                moveTowerFromPlankToIsland(game.getPlayers().get((i+1)%3), island);
                            }
                        }if (game.getPlayers().get(i).getSupremacyCont() < game.getPlayers().get((i+2)%3).getSupremacyCont()){

                        }
                    }
                }
            }
        }else{
            if (game.getNumPlayers() == 2){
                if (game.getPlayers().get(0).getSupremacyCont() > game.getPlayers().get(1).getSupremacyCont()){
                    moveTowerFromPlankToIsland(game.getPlayers().get(0), island);
                }else if (game.getPlayers().get(0).getSupremacyCont() < game.getPlayers().get(1).getSupremacyCont()){
                    moveTowerFromPlankToIsland(game.getPlayers().get(1), island);
                }
            }else if (game.getNumPlayers() == 3) {
                if (game.getPlayers().get(0).getSupremacyCont() > game.getPlayers().get(1).getSupremacyCont() && game.getPlayers().get(0).getSupremacyCont() > game.getPlayers().get(2).getSupremacyCont()){
                    moveTowerFromPlankToIsland(game.getPlayers().get(0), island);
                }else if (game.getPlayers().get(1).getSupremacyCont() > game.getPlayers().get(0).getSupremacyCont() && game.getPlayers().get(1).getSupremacyCont() > game.getPlayers().get(2).getSupremacyCont()){
                    moveTowerFromPlankToIsland(game.getPlayers().get(1), island);
                }else if (game.getPlayers().get(2).getSupremacyCont() > game.getPlayers().get(0).getSupremacyCont() && game.getPlayers().get(2).getSupremacyCont() > game.getPlayers().get(1).getSupremacyCont()){
                    moveTowerFromPlankToIsland(game.getPlayers().get(2), island);
                }
            }
        }
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
                    if (!(islands.get(islands.size()-1).getFirstTower() == null || islands.get(islands.size()-1).getFirstTower().getColor() != island.getFirstTower().getColor())) {
                        for (int j = 0; j < islands.get(islands.size()-1).getTowers().size(); j++) {
                            island.addTower(islands.get(islands.size()-1).getFirstTower());
                            islands.get(islands.size()-1).removeTower();
                        }
                        for (int j = 0; j < islands.get(islands.size()-1).getStudents().size(); j++) {
                            island.addStudent(islands.get(islands.size()-1).getFirstStudent());
                            islands.get(islands.size()-1).removeStudent();
                        }
                    }
                    islands.remove(islands.get(islands.size()-1));
                }else {
                    if (!(islands.get(i-1).getFirstTower() == null || islands.get(i-1).getFirstTower().getColor() != island.getFirstTower().getColor())) {
                        for (int j = 0; j < islands.get(i-1).getTowers().size(); j++) {
                            island.addTower(islands.get(i-1).getFirstTower());
                            islands.get(i-1).removeTower();
                        }
                        for (int j = 0; j < islands.get(i-1).getTowers().size(); j++) {
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