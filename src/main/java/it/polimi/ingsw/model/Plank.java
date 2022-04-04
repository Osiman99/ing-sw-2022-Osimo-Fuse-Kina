package it.polimi.ingsw.model;

import it.polimi.ingsw.model.StudentRow;
import it.polimi.ingsw.model.Tower;

import java.util.ArrayList;
import java.util.List;

public class Plank {
    private List<Tower> towers;
    private StudentRow diningRoom[];
    private Entrance entrance;

    public Plank(){
        towers = new ArrayList<Tower>();
        diningRoom = new StudentRow[5];
        entrance = new Entrance();
    }

    public boolean isTowerSpaceEmpty(){
        if(towers.size() == 0)
            return true;
        else
            return false;
    }

    public void giveStudentToEntrance(Student student){
        entrance.addStudent(student);
    }

    public void giveStudentToDiningRoom(Student student){
        diningRoom[student.getColor().getCode()].addStudent(student);
        entrance.removeStudent(student);
    }

    public void giveStudentToIsland(Student student, Island island){
        island.addStudent(student);
        entrance.removeStudent(student);
    }




    /*public Plank() {
        Student[] diningRoom = {null};

        diningRoom = new StudentRow[10];
        for(int i=0; i<StudentRow.length;)


    }*/

}
