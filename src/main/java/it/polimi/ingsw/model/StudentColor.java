package it.polimi.ingsw.model;

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
    StudentColor(int code) {
        this.code = code;
    }

    /**
     * Get the color code
     * @return code
     */
    public int getCode() {
        return this.code;
    }

    /*
     * Get the color given the color code
     * @param code
     * @return color

     public int getStudentColor(int code) {
        return StudentColor.this.code;
    }*/
}