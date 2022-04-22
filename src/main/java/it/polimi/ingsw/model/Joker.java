package it.polimi.ingsw.model;

public class Joker extends CharacterCards{
    private String name;
    private int price;

    public Joker(){
        name = "Joker";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
