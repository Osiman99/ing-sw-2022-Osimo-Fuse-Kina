package it.polimi.ingsw.model;

public class JokerStrategy implements CharacterCardsStrategy{
    private String name;
    private int price;

    public JokerStrategy(){
        name = "Joker";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect(Player player) {

    }
}
