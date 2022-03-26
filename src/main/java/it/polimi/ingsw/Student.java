package it.polimi.ingsw;

public class Student {

    private StudentColor Color;

    /**
     * Default constructor
     *
     * @param Color is the representation of the student color
     */
    public Student(StudentColor Color) {
        this.Color = Color;
    }

    /**
     * Getter
     *
     * @return the color of the student
     */
    public StudentColor getColor() {
        return Color;
    }
}

