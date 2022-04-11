package it.polimi.ingsw.model;

public class KnightStrategy implements CharacterCardsStrategy{
    private String name;
    private int price;

    public KnightStrategy(){
        name = "Knight";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect(Player player) {

    }
}
