package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Bag {

    private List<Student> students;  //si potrebbe anche non mettere static perchè Bag è singleton

    /**
     * singular instance
     */
    private static Bag instance;

    /**
     * private constructor
     */
    private Bag(){
        students = new ArrayList<Student>();
        for (int i = 0; i < 24; i++) {
            students.add(new Student(StudentColor.GREEN));
        }
        for (int i = 0; i < 24; i++) {
            students.add(new Student(StudentColor.RED));
        }
        for (int i = 0; i < 24; i++) {
            students.add(new Student(StudentColor.YELLOW));
        }
        for (int i = 0; i < 24; i++) {
            students.add(new Student(StudentColor.PINK));
        }
        for (int i = 0; i < 24; i++) {
            students.add(new Student(StudentColor.BLUE));
        }
        Collections.shuffle(students);
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

    public List<Student> getStudents() {
        return students;
    }
}
