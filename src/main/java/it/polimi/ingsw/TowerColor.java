package it.polimi.ingsw;

/**
 * This enum contains all possible colors that towers can get
 */
public enum TowerColor {
    BLACK("BLACK"), WHITE("WHITE"), GREY("GREY") ;

    private final String Color;

    /**
     * Default constructor
     *
     * @param Color is the string representation of the tower color
     */
    TowerColor(String Color) {
        this.Color = Color;
    }

    /**
     *
     * @return a string containing the color of the tower
     */
    public String getColor(){
        return this.Color;
    }


   // public String getTowerColor(String towerColor){return TowerColor.this.towerColor;}
}
