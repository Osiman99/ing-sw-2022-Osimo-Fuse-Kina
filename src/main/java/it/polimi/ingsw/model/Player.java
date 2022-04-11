package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private TowerColor playerColor;
    private final String nickname;
    private final Plank plank;
    private PlayerState state;
    private final AssistantDeck deck;
    private int numCoins = 1;
    private int chosenCardValue;
    private Game game;
    private CharacterCardsStrategy characterCardsStrategy;

    public Player(String nickname){
        game = Game.getInstance();
        this.nickname = nickname;
        plank = new Plank();
        state = PlayerState.SLEEP;
        deck = new AssistantDeck();
    }

    public String getNickname(){
        return nickname;
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
        for (int i = 0; i < cloud.getStudentsSize(); i++) {
            plank.getEntrance().addStudent(cloud.getFirstStudent());
            cloud.removeStudent();
        }
    }

    public void moveStudentFromEntranceToDiningRoom(Student student){    //la for si fa nel main (GUI)
        plank.getDiningRoom()[student.getColor().getCode()].addStudent(student);
        plank.getEntrance().removeStudent(student);
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
            }
        }
    }

    public void useEffect(){
        switch(characterCardsStrategy.getName()){
            case "Sommelier":
                break;
            case "Chef":
                break;
            case "Messenger":
                break;
            case "Postman":
                break;
            case "Herbalist":
                break;
            case "Centaur":
                break;
            case "Joker":
                break;
            case "Knight":
                break;
            case "Merchant":
                break;
            case "Musician":
                break;
            case "Lady":
                break;
            case "Sinister":
                break;
        }
    }

    public void setCharacterCardsStrategy(CharacterCardsStrategy characterCardsStrategy){
        this.characterCardsStrategy = characterCardsStrategy;
    }

}

