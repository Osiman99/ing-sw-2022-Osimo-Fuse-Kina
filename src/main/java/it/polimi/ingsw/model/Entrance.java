package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Entrance {

    private Game game;
    private List<Student> students;

    public Entrance() {
        this.game = Game.getInstance();
        this.students = new ArrayList<Student>();
    }

    public void moveStudentToStudentRow(StudentRow studentRow) {

    }

    public void moveStudentToIsland(Island island){

    }
}
