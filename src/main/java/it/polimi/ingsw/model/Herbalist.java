package it.polimi.ingsw.model;

public class Herbalist extends CharacterCards{
    private String name;
    private int price;

    public Herbalist(){
        name = "Herbalist";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
