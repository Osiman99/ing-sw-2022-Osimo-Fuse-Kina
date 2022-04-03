package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Bag {

    private static List<Student> students;  //si potrebbe anche non mettere static perchè Bag è singleton

    /**
     * singular instance
     */
    private static Bag instance;

    /**
     * private constructor
     */
    private Bag(){
        students = new ArrayList<Student>();
    }


    public static Bag getInstance(){

        /**
         * create the object only if it does not exist
         */
        if (instance == null){
            instance = new Bag();
        }
        return instance;
    }

    public void removeStudent(){
        students.remove(0);
    }

    public void addStudent(Student student){   //servirà per l'inizializzazione e per i CharacterCards
        students.add(student);
    }

    public Student getFirstStudent(){
        return students.get(0);
    }



}
