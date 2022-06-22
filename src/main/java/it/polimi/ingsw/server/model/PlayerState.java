package it.polimi.ingsw.server.model;

import java.io.Serializable;

/**
 * The states in which a player can be during the game
 */
public enum PlayerState implements Serializable {

    PLAN("PLAN"), ACTION("ACTION"), SLEEP("SLEEP");

    private String state;

    PlayerState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
