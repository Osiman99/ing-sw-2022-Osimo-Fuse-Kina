package it.polimi.ingsw.model;

public class Musician extends CharacterCards{
    private String name;
    private int price;

    public Musician(){
        name = "Musician";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
