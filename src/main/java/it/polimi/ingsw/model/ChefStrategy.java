package it.polimi.ingsw.model;

public class ChefStrategy implements CharacterCardsStrategy{
    public String name;
    public int price;

    public ChefStrategy(){
        name = "Chef";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
