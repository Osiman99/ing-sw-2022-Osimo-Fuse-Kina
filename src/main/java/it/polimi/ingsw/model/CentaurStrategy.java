package it.polimi.ingsw.model;

public class CentaurStrategy implements CharacterCardsStrategy{
    public String name;
    public int price;

    public CentaurStrategy(){
        name = "Centaur";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
