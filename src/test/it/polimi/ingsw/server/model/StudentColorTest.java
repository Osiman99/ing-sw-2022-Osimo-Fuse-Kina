package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentColorTest {
    private StudentColor[] studentColor= {StudentColor.GREEN, StudentColor.RED, StudentColor.YELLOW, StudentColor.PINK, StudentColor.BLUE};
    private String[] color= {"Green", "Red", "Yellow", "Pink", "Blue"};

    @Test
    void getCode() {
        for(int i=0; i<5; i++)
            assertEquals(i, studentColor[i].getCode());
    }

    @Test
    void getStudentColor() {
        for(int i=0; i<5; i++)
            assertEquals(studentColor[i], StudentColor.getStudentColor(i));
    }

    @Test
    void testToString() {
        for(int i=0; i<5; i++)
            assertEquals(color[i], StudentColor.toString(StudentColor.getStudentColor(i)));
    }

}