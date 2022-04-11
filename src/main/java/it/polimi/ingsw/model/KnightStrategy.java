package it.polimi.ingsw.model;

public class KnightStrategy implements CharacterCardsStrategy{
    public String name;
    public int price;

    public KnightStrategy(){
        name = "Knight";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
