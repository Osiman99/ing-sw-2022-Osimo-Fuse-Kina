package it.polimi.ingsw;

/**
 * This enum contains all possible colors that students can get
 */
public enum StudentColor {
    RED("RED"), GREEN("GREEN"), BLUE("BLUE"), YELLOW("YELLOW"), PINK("PINK");

    private final String color;

    /**
     * Default constructor
     *
     * @param color is the string representation of the student color
     */
    StudentColor(String color) {
        this.color = color;
    }

    /**
     *
     * @return a string containing the color of the student
     */
    public String getColor(){
        return this.color;
    }


    // public String getsColor(String color){return StudentColor.this.color;}
}