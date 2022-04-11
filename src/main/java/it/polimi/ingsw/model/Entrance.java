package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Entrance {

    private Game game;
    private List<Student> students;
    private Student moving_student;

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
                break;
            }
        }
        return moving_student;
    }
}
