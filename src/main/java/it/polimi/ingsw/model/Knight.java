package it.polimi.ingsw.model;

public class Knight extends CharacterCards{
    private String name;
    private int price;

    public Knight(){
        name = "Knight";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
