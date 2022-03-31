package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Entrance {
    private List<Student> students = new ArrayList<Student>();

    public Entrance(ArrayList<Student> students) {
        this.students = students;
    }

    public void moveStudentToStudentRow(StudentRow studentRow) {

    }

    public void moveStudentToIsland(Island island){

    }
}
