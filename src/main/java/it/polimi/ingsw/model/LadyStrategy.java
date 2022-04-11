package it.polimi.ingsw.model;

public class LadyStrategy implements CharacterCardsStrategy{
    private String name;
    private int price;

    public LadyStrategy(){
        name = "Lady";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect(Player player) {

    }
}
