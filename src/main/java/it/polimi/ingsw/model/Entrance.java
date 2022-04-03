package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Entrance {

    private Game game;
    private List<Student> students;
    private List<Student> moving_students;

    public Entrance() {
        this.game = Game.getInstance();
        this.students = new ArrayList<Student>();
    }

    public void moveStudentToStudentRow(StudentRow studentRow) {

    }

    public void moveStudentToIsland(Island island){

    }

    public void addStudent(Student student){
        students.add(student);
    }

    public void removeStudent(Student student){
        for(int i = 0; i < students.size(); i++){
            if (student.equals(students.get(i))){
                students.remove(i);
            }
        }
    }

    public List<Student> getStudentsForDiningRoom(int a, int b, int c){      //tramite CLI
        moving_students = new ArrayList<Student>();
        moving_students.add(students.get(a));
        moving_students.add(students.get(b));
        moving_students.add(students.get(c));
        return moving_students;
    }

    //OVERLOADING
    public List<Student> getStudentsForDiningRoom(int a, int b, int c, int d){   //tramite CLI
        moving_students = new ArrayList<Student>();
        moving_students.add(students.get(a));
        moving_students.add(students.get(b));
        moving_students.add(students.get(c));
        moving_students.add(students.get(d));
        return moving_students;
    }
}
