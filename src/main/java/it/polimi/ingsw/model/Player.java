package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private TowerColor playerColor;
    private int supremacyCont;
    private final String nickname;
    private final Plank plank;
    private PlayerState state;
    private final AssistantDeck deck;
    private int numCoins;
    private int chosenCardValue;
    private CharacterCards characterCards;
    private Game game;

    public Player(String nickname){
        game = Game.getInstance();
        numCoins = 1;
        this.nickname = nickname;
        supremacyCont = 0;
        plank = new Plank();
        state = PlayerState.SLEEP;
        deck = new AssistantDeck();
    }

    public String getNickname(){
        return nickname;
    }

    public int getSupremacyCont() { return supremacyCont; }

    public void setSupremacyCont(int supremacyCont) {
        this.supremacyCont = supremacyCont;
    }

    public Plank getPlank() {
        return plank;
    }

    public PlayerState getState() {
        return state;
    }

    public AssistantDeck getDeck() {
        return deck;
    }

    public int getNumCoins() {
        return numCoins;
    }

    public TowerColor getPlayerColor() {
        return playerColor;
    }

    public int getChosenCardValue() {
        return chosenCardValue;
    }

    public void setPlayerColor(TowerColor playerColor) {
        this.playerColor = playerColor;
    }

    public void moveStudentsFromCloudToEntrance(Cloud cloud) {
        int studentsSize = cloud.getStudentsSize();
        for (int i = 0; i < studentsSize; i++) {
            plank.getEntrance().addStudent(cloud.getFirstStudent());
            cloud.removeStudent();
        }
    }

    public void moveStudentFromEntranceToDiningRoom(Student student){    //la for si fa nel main (GUI)
        plank.getDiningRoom()[student.getColor().getCode()].addStudent(student);
        plank.getEntrance().removeStudent(student);
        if (plank.getDiningRoom()[student.getColor().getCode()].getStudents().size() == 3 || plank.getDiningRoom()[student.getColor().getCode()].getStudents().size() == 6 || plank.getDiningRoom()[student.getColor().getCode()].getStudents().size() == 9){
            numCoins++;
        }
    }

    public void moveStudentFromEntranceToIsland(Student student, Island island){    //la for si fa nel main (GUI)
        island.addStudent(student);
        plank.getEntrance().removeStudent(student);
    }

    public void chooseAssistantCard(int value){
        //controllo value
        chosenCardValue = value;
    }

    public void chooseNumMoves (int numMoves){
        for (int i = 0; i < deck.getDeck().size(); i++) {
            if (deck.getDeck().get(i).getValue() == chosenCardValue) {
                if (numMoves <= deck.getDeck().get(i).getMaxMoves() && numMoves > 0){
                    game.getBoard().moveMotherNature(numMoves);
                }//else throw exception;
                //remove AssistantCard
                break;
            }
        }
    }

    public void chooseCharacterCard(CharacterCards characterCards, Student student, Island island){  //SOMMELIER
        this.characterCards = characterCards;
        this.characterCards.applyEffect(student, island);
    }

    public void chooseCharacterCard(CharacterCards characterCards, Island island){  //MESSENGER
        this.characterCards = characterCards;
        this.characterCards.applyEffect(island);
    }

    public void chooseCharacterCard(CharacterCards characterCards, Player player, int numMoves){  //POSTMAN
        this.characterCards = characterCards;
        this.characterCards.applyEffect(player, numMoves);
    }

    public void chooseCharacterCard(CharacterCards characterCards){     //TUTTI GLI ALTRI
        this.characterCards = characterCards;
        this.characterCards.applyEffect();
    }





}

