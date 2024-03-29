package it.polimi.ingsw.server.model;

import java.io.Serializable;

/**
 * This enum contains all possible colors that students can get and we associate
 * a number to each color to correlate with the student row
 */
public enum StudentColor implements Serializable {

    GREEN(0), RED(1), YELLOW(2), PINK(3), BLUE(4);

    private int code;

    /**
     * constructor
     * @param code
     */
    StudentColor(int code) {        //COSTRUTTORE INUTILE
        this.code = code;
    }

    /**
     * Get the color code
     * @return code
     */
    public int getCode() {
        return this.code;
    }

    /**
     * gets the student color given its code.
     *
     * @param code the code corresponding to the color.
     * @return the color
     */
    public static StudentColor getStudentColor(int code) {
        if (StudentColor.GREEN.getCode() == code) {
            return StudentColor.GREEN;
        }
        else if (StudentColor.RED.getCode() == code) {
            return StudentColor.RED;
        }
        else if (StudentColor.YELLOW.getCode() == code) {
            return StudentColor.YELLOW;
        }
        else if (StudentColor.PINK.getCode() == code) {
            return StudentColor.PINK;
        }
        else{
            return StudentColor.BLUE;
        }
    }

    public static String toString(StudentColor studentColor){
        switch(studentColor){
            case GREEN:
                return "Green";
            case RED:
                return "Red";
            case YELLOW:
                return "Yellow";
            case PINK:
                return "Pink";
            case BLUE:
                return "Blue";
            default: return null;
        }
    }
}