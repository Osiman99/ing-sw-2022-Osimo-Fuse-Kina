package it.polimi.ingsw.model;

public class Postman extends CharacterCards{
    private String name;
    private int price;

    public Postman(){
        name = "Postman";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
