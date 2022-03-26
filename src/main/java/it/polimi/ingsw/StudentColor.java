package it.polimi.ingsw;

/**
 * This enum contains all possible colors that students can get
 */
public enum StudentColor {
    RED("RED"), GREEN("GREEN"), BLUE("BLUE"), YELLOW("YELLOW"), PINK("PINK");

    private final String Color;

    /**
     * Default constructor
     *
     * @param Color is the string representation of the student color
     */
    StudentColor(String Color) {
        this.Color = Color;
    }

    /**
     *
     * @return a string containing the color of the student
     */
    public String getColor(){
        return this.Color;
    }


    // public String getsColor(String sColor){return StudentColor.this.sColor;}
}