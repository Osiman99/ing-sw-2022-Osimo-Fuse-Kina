package it.polimi.ingsw;

/**
 * This enum contains all possible colors that towers can get
 */
public enum TowerColor {
    BLACK("BLACK"), WHITE("WHITE"), GREY("GREY") ;

    private final String towerColor;

    /**
     * Default constructor
     *
     * @param towerColor is the string representation of the tower color
     */
    TowerColor(String towerColor) {
        this.towerColor = towerColor;
    }

    /**
     *
     * @return a string containing the color of the tower
     */
    public String getTowerColor(){
        return this.towerColor;
    }


   // public String getTowerColor(String towerColor){return TowerColor.this.towerColor;}
}
