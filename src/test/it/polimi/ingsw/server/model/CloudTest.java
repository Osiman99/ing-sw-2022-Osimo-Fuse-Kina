package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {

    private Cloud cloud;
    private Student student;

    @BeforeEach
    void setUp() {
        cloud = new Cloud();
        for(int i=0; i<4; i++)
            cloud.getStudents().add(new Student(StudentColor.getStudentColor(i)));
    }

    @AfterEach
    void tearDown() {
        cloud.getStudents().clear();
    }

    @Test
    void getStudentsSize() {
        assertEquals(4, cloud.getStudentsSize());
    }

    @Test
    void getFirstStudent() {
        student = cloud.getStudents().get(0);
        assertEquals(student, cloud.getFirstStudent());
    }

    @Test
    void removeStudent() {
        student = cloud.getFirstStudent();
        cloud.removeStudent();
        assertNotEquals(student, cloud.getFirstStudent());
    }

    @Test
    void addStudent() {
        student = new Student(StudentColor.BLUE);
        cloud.addStudent(student);
        assertEquals(student, cloud.getStudents().get(4));
    }

    @Test
    void getStudents() {
        assertNotNull(cloud.getStudents());
    }

    @Test
    void isEmpty() {
        assertFalse(cloud.isEmpty());
        cloud.getStudents().clear();
        assertTrue(cloud.isEmpty());
    }


}