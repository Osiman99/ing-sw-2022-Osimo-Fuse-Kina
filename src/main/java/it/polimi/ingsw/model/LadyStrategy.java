package it.polimi.ingsw.model;

public class LadyStrategy implements CharacterCardsStrategy{
    public String name;
    public int price;

    public LadyStrategy(){
        name = "Lady";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
