package it.polimi.ingsw;

/**
 * This enum contains all possible colors that towers can get
 */
public enum TowerColor {
    BLACK("BLACK"), WHITE("WHITE"), GREY("GREY") ;

    private final String color;

    /**
     * Default constructor
     *
     * @param color is the string representation of the tower color
     */
    TowerColor(String color) {
        this.color = color;
    }

    /**
     *
     * @return a string containing the color of the tower
     */
    public String getColor(){
        return this.color;
    }


   // public String getTowerColor(String towerColor){return TowerColor.this.towerColor;}
}
