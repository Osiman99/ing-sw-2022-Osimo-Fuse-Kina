package it.polimi.ingsw.model;

public class HerbalistStrategy implements CharacterCardsStrategy{
    private String name;
    private int price;

    public HerbalistStrategy(){
        name = "Herbalist";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect(Player player) {

    }
}
