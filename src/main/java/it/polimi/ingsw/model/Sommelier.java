package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Sommelier extends CharacterCards {

    private String name;
    private int price;
    private Bag bag;
    private List<Student> students;
    private static Sommelier instance;

    private Sommelier(){
        name = "Sommelier";
        bag = Bag.getInstance();
        students = new ArrayList<Student>();
        for (int i = 0; i < 4; i++) {
            students.add(bag.getFirstStudent());
            bag.removeStudent();
        }
    }

    public String getName() {
        return name;
    }

    public static Sommelier getInstance(){
        if (instance == null){
            instance = new Sommelier();
        }
        return instance;
    }

    public List<Student> getStudents() {
        return students;
    }


    public void applyEffect() {

    }
}