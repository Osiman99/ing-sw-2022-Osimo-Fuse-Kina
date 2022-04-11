package it.polimi.ingsw.model;

public class CentaurStrategy implements CharacterCardsStrategy{
    private String name;
    private int price;

    public CentaurStrategy(){
        name = "Centaur";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect(Player player) {

    }
}
