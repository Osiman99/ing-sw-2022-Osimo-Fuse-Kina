package it.polimi.ingsw.server.model;

import java.io.Serializable;

public class Plank implements Serializable {

    private static final long serialVersionUID = -1773083561094488517L;
    private TowerSpace towerSpace;
    private StudentRow diningRoom[];
    private Entrance entrance;

    /**
     * class constructor
     */
    public Plank(){
        towerSpace = new TowerSpace();
        diningRoom = new StudentRow[5];
        for (int i = 0; i < 5; i++){
            diningRoom[i] = new StudentRow(i);
        }
        entrance = new Entrance();
    }

    /**
     * checks if the tower space is empty or not
     * @return
     */
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
