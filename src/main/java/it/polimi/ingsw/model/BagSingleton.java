package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class BagSingleton {

    private static List<Student> bag;

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
            bag = new ArrayList<Student>(120);
        }
        return instance;
    }

}
