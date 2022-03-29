package it.polimi.ingsw.model;

import it.polimi.ingsw.model.TowerColor;

public class Tower {

    private TowerColor color;


    /**
     * Default constructor
     *
     * @param color is the representation of the tower color
     */
    public Tower(TowerColor color) {
        this.color = color;
    }

    /**
     * Getter
     *
     * @return the color of the tower
     */
    public TowerColor getColor() {
        return color;
    }

    //public void moveTower() capire se è da mettere nella gameLogic
}
