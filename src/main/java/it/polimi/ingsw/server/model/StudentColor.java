package it.polimi.ingsw.server.model;

/**
 * This enum contains all possible colors that students can get and we associate
 * a number to each color to correlate with the student row
 */
public enum StudentColor {
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

    /*
     * Get the color given the color code
     * @param code
     * @return color

     public int getStudentColor(int code) {
        return StudentColor.this.code;
    }*/
}