package it.polimi.ingsw;

/**
 * This enum contains all possible colors that students can get
 */
public enum StudentColor {
    RED("RED"), GREEN("GREEN"), BLUE("BLUE"), YELLOW("YELLOW"), PINK("PINK");

    private final String studentColor;

    /**
     * Default constructor
     *
     * @param studentColor is the string representation of the tower color
     */
    StudentColor(String studentColor) {
        this.studentColor = studentColor;
    }

    /**
     *
     * @return a string containing the color of the student
     */
    public String getStudentColor(){
        return this.studentColor;
    }


    // public String getStudentColor(String studentColor){return StudentColor.this.studentColor;}
}