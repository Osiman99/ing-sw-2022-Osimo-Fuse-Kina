package it.polimi.ingsw.model;

public class BagSingleton {

    private static Student[] students;

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
            students = new Student[120];
            // dobbiamo inizializzare l'array
        }
        return instance;
    }

    // altri metodi dopo che vengono create le classi Cloud, Plank

}
