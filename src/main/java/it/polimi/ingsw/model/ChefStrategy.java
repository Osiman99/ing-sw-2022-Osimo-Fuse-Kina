package it.polimi.ingsw.model;

public class ChefStrategy implements CharacterCardsStrategy{
    private String name;
    private int price;

    public ChefStrategy(){
        name = "Chef";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect(Player player) {

    }
}
