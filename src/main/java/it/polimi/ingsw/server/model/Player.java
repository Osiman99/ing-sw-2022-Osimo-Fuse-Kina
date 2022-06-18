package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.messages.BoardMessage;
import it.polimi.ingsw.observer.Observable;

import java.io.Serializable;

public class Player extends Observable implements Serializable {

    private static final long serialVersionUID = -2704011782548707041L;
    private TowerColor playerColor;
    private int towerCont;
    private int supremacyCont;
    private final String nickname;
    private Plank plank;
    private PlayerState state;
    private AssistantDeck deck;
    private int numCoins;
    private AssistantCard chosenAssistantCard;
    private int chosenACValue;
    private Game game;

    public Player(String nickname){
        game = Game.getInstance();
        this.nickname = nickname;
        numCoins = 1;
        supremacyCont = 0;
        state = PlayerState.SLEEP;
    }
    public void initPlayer(){
        plank = new Plank();
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

    public AssistantCard getChosenAssistantCard() {
        return chosenAssistantCard;
    }

    public int getTowerCont() {
        return towerCont;
    }

    public void setTowerCont(int towerCont) {
        this.towerCont = towerCont;
    }

    public void setPlayerColor(TowerColor playerColor) {
        this.playerColor = playerColor;
    }

    public void setNumCoins(int numCoins) {
        this.numCoins = numCoins;
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
        } game.getBoard().moveProfessor(this);
    }

    public void moveStudentFromEntranceToIsland(Student student, Island island){    //la for si fa nel main (GUI)
        island.addStudent(student);
        plank.getEntrance().removeStudent(student);
    }

    public void chooseAssistantCard(int value){
        chosenAssistantCard = new AssistantCard(value, (value-1)/2 + 1);
        chosenACValue = value;
        deck.getDeck().removeIf(assistantCard -> chosenACValue == assistantCard.getValue());
    }

}

