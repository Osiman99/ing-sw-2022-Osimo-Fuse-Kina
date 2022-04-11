package it.polimi.ingsw.model;

public class PostmanStrategy implements CharacterCardsStrategy{
    public String name;
    public int price;

    public PostmanStrategy(){
        name = "Postman";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
