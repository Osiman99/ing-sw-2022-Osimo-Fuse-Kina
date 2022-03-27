package it.polimi.ingsw;

public class Student {

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

