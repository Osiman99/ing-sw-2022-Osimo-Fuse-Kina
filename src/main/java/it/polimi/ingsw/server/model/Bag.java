package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag implements Serializable {
    private static final long serialVersionUID = 2170486071323199195L;

    private List<Student> students;
    private boolean bagEmpty;

    /**
     * private constructor
     * inside the bag initially we have 24 students of each color
     */
    public Bag(){
        bagEmpty = false;
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



    /**
     * always remove the student in the first position of the arraylist when we need to put them in the clouds or entrance.
     */
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

    /**
     * Checks if the bag is empty or not.
     *
     * @return true if bag is empty.
     */
    public boolean isBagEmpty() {
        return bagEmpty;
    }

    public void setBagEmpty(boolean bagEmpty) {
        this.bagEmpty = bagEmpty;
    }
}
