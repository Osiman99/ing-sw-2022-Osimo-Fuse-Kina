package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.Bag;
import it.polimi.ingsw.server.model.Student;
import it.polimi.ingsw.server.model.StudentColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BagTest {
    private Bag bag;
    private Student student;
    private Student studtest = new Student(StudentColor.GREEN);

    @BeforeEach
    void setUp() {
        bag = Bag.getInstance();
    }


    @Test
    void removeStudent(){
        student = bag.getFirstStudent();
        bag.removeStudent();
        assertNotEquals(student, bag.getFirstStudent());
        bag.addStudent(student);
    }

    @Test
    void addStudent(){
        bag.addStudent(studtest);
        assertEquals(studtest, bag.getStudents().get(120));
    }

    @Test
    void getFirstStudent(){
        assertEquals(bag.getFirstStudent(), bag.getStudents().get(0));
    }

}