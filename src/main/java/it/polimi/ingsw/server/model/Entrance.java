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

        /*  y=2x+3 where x means getNumPlayer and y stands for the number of students that should be added.
            If getNumPlayer = 2 then add 7 students to each entrance;
            else if getNumPlayer = 3 then add 9 students to each entrance.
         */
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

    public void removeStudent(Student student){     //student come parametro o color?
        for(int i = 0; i < students.size(); i++){
            if (students.get(i).getColor() == student.getColor()){  //equals in tutte le cose così?
                students.remove(i);
                break;
            }
        }
    }


}
