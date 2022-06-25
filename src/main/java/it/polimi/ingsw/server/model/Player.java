package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.messages.BoardMessage;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.GameState;

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

    /**
     * class constructor(in the beginning the player has 1 coin & is in SLEEP State
     * @param nickname
     */
    public Player(String nickname){
        this.nickname = nickname;
        numCoins = 1;
        supremacyCont = 0;
        state = PlayerState.SLEEP;
    }

    /**
     * initializes the Player
     */
    public void initPlayer(){
        game = Game.getInstance();
        plank = new Plank();
        deck = new AssistantDeck();
    }

    /**
     * getters and setters
     */

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
        notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));
    }

    public void moveStudentFromEntranceToDiningRoom(Student student){    //la for si fa nel main (GUI)
        plank.getDiningRoom()[student.getColor().getCode()].addStudent(student);
        plank.getEntrance().removeStudent(student);
        if (plank.getDiningRoom()[student.getColor().getCode()].getStudents().size() == 3 || plank.getDiningRoom()[student.getColor().getCode()].getStudents().size() == 6 || plank.getDiningRoom()[student.getColor().getCode()].getStudents().size() == 9){
            numCoins++;
        } game.getBoard().moveProfessor(this);
        notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));
    }

    public void moveStudentFromEntranceToIsland(Student student, Island island){    //la for si fa nel main (GUI)
        island.addStudent(student);
        plank.getEntrance().removeStudent(student);
        notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));
    }

    /**
     * you can choose one Assistant Card based on its value and then it's removed from the deck
     * @param value
     */
    public void chooseAssistantCard(int value){
        chosenAssistantCard = new AssistantCard(value, (value-1)/2 + 1);
        chosenACValue = value;
        deck.getDeck().removeIf(assistantCard -> chosenACValue == assistantCard.getValue());
    }

    public boolean isTowerSpaceEmpty(){
        if(plank.getTowerSpace().getTowersList().isEmpty()){
            notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));
            return true;
        }
        return false;
    }

    public boolean isDeckEmpty(){
        if(deck.getDeck().isEmpty()){
            notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));
            return true;
        }
        return false;
    }

}

