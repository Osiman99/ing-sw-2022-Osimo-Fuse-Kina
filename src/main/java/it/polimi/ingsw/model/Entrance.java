package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Entrance {

    private Game game;
    private List<Student> students;
    private Student moving_student;

    public Entrance() {
        this.game = Game.getInstance();
        this.students = new ArrayList<Student>();
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public void removeStudent(Student student){
        for(int i = 0; i < students.size(); i++){
            if (student.equals(students.get(i))){
                students.remove(i);
                break;
            }
        }
    }

    public Student getStudentForDiningRoom(StudentColor color) {      //tramite CLI, nel main si fa il for
        for (Student student : students) {
            if (student.getColor() == color) {
                moving_student = student;
                break;
            }
        }
        return moving_student;
    }
}
