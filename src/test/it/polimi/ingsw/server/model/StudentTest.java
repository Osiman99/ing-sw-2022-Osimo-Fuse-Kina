package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private Student student = new Student(StudentColor.PINK);

    @Test
    void getColor() {
        assertEquals(StudentColor.PINK, student.getColor());
    }
}