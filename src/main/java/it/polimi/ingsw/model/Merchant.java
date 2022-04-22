package it.polimi.ingsw.model;

public class Merchant extends CharacterCards{
    private String name;
    private int price;

    public Merchant(){
        name = "Merchant";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
