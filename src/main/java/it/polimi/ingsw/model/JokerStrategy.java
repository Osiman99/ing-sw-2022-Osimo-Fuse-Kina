package it.polimi.ingsw.model;

public class JokerStrategy implements CharacterCardsStrategy{
    public String name;
    public int price;

    public JokerStrategy(){
        name = "Joker";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
