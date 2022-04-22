package it.polimi.ingsw.model;

public class Messenger extends CharacterCards{
    private String name;
    private int price;

    public Messenger(){
        name = "Messenger";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
