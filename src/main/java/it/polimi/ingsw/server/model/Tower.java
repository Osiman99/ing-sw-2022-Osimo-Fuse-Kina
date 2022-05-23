package it.polimi.ingsw.server.model;

import java.io.Serializable;

public class Tower implements Serializable {

    private static final long serialVersionUID = 5898536984563637207L;
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
