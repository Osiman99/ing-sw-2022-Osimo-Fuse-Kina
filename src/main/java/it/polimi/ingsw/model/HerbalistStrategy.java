package it.polimi.ingsw.model;

public class HerbalistStrategy implements CharacterCardsStrategy{
    public String name;
    public int price;

    public HerbalistStrategy(){
        name = "Herbalist";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
