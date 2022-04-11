package it.polimi.ingsw.model;

public class PostmanStrategy implements CharacterCardsStrategy{
    private String name;
    private int price;

    public PostmanStrategy(){
        name = "Postman";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect(Player player) {

    }
}
