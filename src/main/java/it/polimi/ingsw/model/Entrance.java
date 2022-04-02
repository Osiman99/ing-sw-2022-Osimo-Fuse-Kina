package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Entrance {

    private Game game;
    private List<Student> students;

    public Entrance(ArrayList<Student> students) {
        this.game = Game.getInstance();
        this.students = students;
    }

    public void moveStudentToStudentRow(StudentRow studentRow) {

    }

    public void moveStudentToIsland(Island island){

    }
}
