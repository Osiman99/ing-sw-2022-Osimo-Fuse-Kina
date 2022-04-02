package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Student;

import java.util.ArrayList;
import java.util.List;

public class Cloud {

    private Game game;
    private List<Student> students;

    public Cloud(ArrayList<Student> students){
        this.game = Game.getInstance();
        this.students = new ArrayList<Student>();
    }

    public void moveStudentToEntrance(){
        if (this.game.getNumPlayers() == 2){
            //fai cose
        }else if (this.game.getNumPlayers() == 3){
            //fai altre cose
        }
    }







    // if 2 players then 3 students per cloud
    // if 3 players then 4 students per row

}
