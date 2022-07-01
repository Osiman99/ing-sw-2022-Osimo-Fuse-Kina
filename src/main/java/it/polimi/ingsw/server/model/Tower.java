package it.polimi.ingsw.server.model;

import java.io.Serializable;

public class Tower implements Serializable {

    private static final long serialVersionUID = 5898536984563637207L;
    private TowerColor color;

    public Tower(TowerColor color) {
        this.color = color;
    }


    public TowerColor getColor() {
        return color;
    }

}
