package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class BagSingleton {

    private static List<Student> students;

    /**
     * singular instance
     */
    private static BagSingleton instance;

    /**
     * private constructor
     */
    private BagSingleton(){}


    public static BagSingleton getInstance(){

        /**
         * create the object only if it does not exist
         */
        if (instance == null){
            instance = new BagSingleton();
            students = new ArrayList<Student>();
        }
        return instance;
    }

    public void moveStudent(){

    }
}
