package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentRowTest {
    private StudentRow studentRow;

    @BeforeEach
    void setUp() {
        studentRow = new StudentRow();
        for(int i=0; i<5; i++)
            studentRow.addStudent(new Student(StudentColor.getStudentColor(0)));
    }

    @AfterEach
    void tearDown() {
        studentRow = null;
    }

    @Test
    void getStudents() {
        for(Student s : studentRow.getStudents())
            assertEquals(StudentColor.getStudentColor(0), s.getColor());
    }

    @Test
    void setStudents() {
        List<Student> students = new ArrayList<Student>();
        for(int i=0; i<8; i++)
            students.add(new Student(StudentColor.GREEN));

        studentRow.setStudents(students);

        for(Student s: students)
            assertTrue(studentRow.getStudents().contains(s));
    }

    @Test
    void addStudent() {
        Student student = new Student(StudentColor.GREEN);
        studentRow.addStudent(student);
        assertEquals(student, studentRow.getStudents().get(studentRow.getStudents().size()-1));
    }
}