package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String nickname;
    private final Plank plank;
    private PlayerState state;
    private final AssistantDeck deck;
    private int numCoins = 1;

    public Player(String nickname){
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

    public void moveTowerFromPlankToIsland(Island island){
        island.addTower(plank.getTowerSpace().getFirstTower());
        plank.getTowerSpace().removeTower();
    }

}

