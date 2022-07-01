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

        for(int i = 0; i < 2*game.getNumPlayers()+3; i++) {
            students.add(game.getBoard().getBag().getFirstStudent());
            game.getBoard().getBag().removeStudent();
        }

    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public void removeStudent(Student student){
        for(int i = 0; i < students.size(); i++){
            if (students.get(i).getColor() == student.getColor()){
                students.remove(i);
                break;
            }
        }
    }


}
