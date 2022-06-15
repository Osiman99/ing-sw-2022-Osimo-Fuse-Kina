package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.Bag;
import it.polimi.ingsw.server.model.Student;
import it.polimi.ingsw.server.model.StudentColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    private Bag bag;
    private Student student;

    @BeforeEach
    void setUp() {
        bag = new Bag();
    }

    @AfterEach
    void tearDown() {
        bag = null;
    }

    @Test
    void removeStudent() {
        student = bag.getFirstStudent();
        bag.removeStudent();
        assertNotEquals(student, bag.getFirstStudent());
    }

    @Test
    void addStudent() {
        student= new Student(StudentColor.GREEN);
        bag.addStudent(student);
        assertEquals(student, bag.getStudents().get(120));
    }

    @Test
    void getFirstStudent() {
        assertEquals(bag.getFirstStudent(), bag.getStudents().get(0));
    }

    @Test
    void getStudents() {
        assertNotNull(bag.getStudents());
    }

}