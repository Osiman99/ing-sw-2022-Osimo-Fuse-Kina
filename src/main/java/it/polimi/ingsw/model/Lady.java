package it.polimi.ingsw.model;

public class Lady extends CharacterCards{
    private String name;
    private int price;

    public Lady(){
        name = "Lady";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
