package it.polimi.ingsw.model;

public enum PlayerState {
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
