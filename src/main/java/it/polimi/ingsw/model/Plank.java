package it.polimi.ingsw.model;

import it.polimi.ingsw.model.StudentRow;
import it.polimi.ingsw.model.Tower;

import java.util.ArrayList;
import java.util.List;

public class Plank {
    private TowerSpace towerSpace;
    private StudentRow diningRoom[];
    private Entrance entrance;

    public Plank(){
        towerSpace = new TowerSpace();
        diningRoom = new StudentRow[5];
        for (int i = 0; i < 5; i++){
            diningRoom[i] = new StudentRow(i);
        }
        entrance = new Entrance();
    }

    public boolean isTowerSpaceEmpty(){
        if(towerSpace.getTowersList().size() == 0)
            return true;
        else
            return false;
    }

    public Entrance getEntrance() {
        return entrance;
    }

    public StudentRow[] getDiningRoom() {
        return diningRoom;
    }

    public TowerSpace getTowerSpace() {
        return towerSpace;
    }




    /*public Plank() {
        Student[] diningRoom = {null};

        diningRoom = new StudentRow[10];
        for(int i=0; i<StudentRow.length;)


    }*/

}
