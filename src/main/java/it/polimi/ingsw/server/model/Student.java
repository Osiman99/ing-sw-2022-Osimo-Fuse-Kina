package it.polimi.ingsw.server.model;

import java.io.Serializable;

public class Student implements Serializable {

    private static final long serialVersionUID = -7889859831093658879L;
    private StudentColor color;

    /**
     * Default constructor
     *
     * @param color is the representation of the student color
     */
    public Student(StudentColor color) {
        this.color = color;
    }

    /**
     * Getter
     *
     * @return the color of the student
     */
    public StudentColor getColor() {
        return color;
    }
}
