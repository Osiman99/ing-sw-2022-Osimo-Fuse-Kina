package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class BagSingleton {

    private Game game;
    private static List<Student> students;

    /**
     * singular instance
     */
    private static BagSingleton instance;

    /**
     * private constructor
     */
    private BagSingleton(){
        students = new ArrayList<Student>();
    }


    public static BagSingleton getInstance(){

        /**
         * create the object only if it does not exist
         */
        if (instance == null){
            instance = new BagSingleton();
        }
        return instance;
    }



    /**
     * if there are 2 players Cloud gets 3 students from the Bag each round,
     * if there are 3 players it gets 4.
     */
    public void moveStudentToCloud() {

           if (this.game.getNumPlayers() == 2){
               for(int i = 0; i < 3; i++){
                   students.remove(0);
               }
           }else if (this.game.getNumPlayers() == 3){
               for(int i = 0; i < 4; i++){
                   students.remove(0);
               }
           }

    }

}
