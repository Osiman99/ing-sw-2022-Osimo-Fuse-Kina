package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Entrance implements Serializable {
    private static final long serialVersionUID = 5329747176187404122L;

    private Game game;
    private ArrayList<Student> students;
    private Student moving_student;

    /**
     * entrance constructor,
     * if there are 2 players we get 7 students from the bag in it, if there are 3 players we get 9 students.
     */
    public Entrance(){
        game = Game.getInstance();
        students = new ArrayList<Student>();
        if(game.getNumPlayers() == 2){
            for(int i = 0; i < 7; i++) {
                students.add(game.getBoard().getBag().getFirstStudent());
                game.getBoard().getBag().removeStudent();
            }
        }if(game.getNumPlayers() == 3) {
            for (int i = 0; i < 9; i++) {
                students.add(game.getBoard().getBag().getFirstStudent());
                game.getBoard().getBag().removeStudent();
            }
        }
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public void removeStudent(Student student){     //student come parametro o color?
        for(int i = 0; i < students.size(); i++){
            if (students.get(i).getColor() == student.getColor()){  //equals in tutte le cose così?
                students.remove(i);
                break;
            }
        }
    }



    public Student getStudentForDiningRoom(StudentColor color) {      //tramite CLI, nel main si fa il for; student come parametro o color?
        for (Student student : students) {
            if (student.getColor() == color) {
                moving_student = student;
                break;                                                 //forse da fare con eccezioni?
            }
        }
        return moving_student;
    }
}
