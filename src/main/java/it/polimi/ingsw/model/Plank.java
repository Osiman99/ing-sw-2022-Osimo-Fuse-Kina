package it.polimi.ingsw.model;

import it.polimi.ingsw.model.StudentRow;
import it.polimi.ingsw.model.Tower;

import java.util.ArrayList;
import java.util.List;

public class Plank {
    private List<Tower> towers;
    private List<StudentRow> diningRoom;
    private Entrance entrance;

    public Plank(){
        towers = new ArrayList<Tower>();
        diningRoom = new ArrayList<StudentRow>();
        entrance = new Entrance();
    }

    public boolean isTowerSpaceEmpty(){
        if(towers.size() == 0)
            return true;
        else
            return false;
    }

    public  void addStudentsEntrance(){

    }






    /*public Plank() {
        Student[] diningRoom = {null};

        diningRoom = new StudentRow[10];
        for(int i=0; i<StudentRow.length;)


    }*/

}
