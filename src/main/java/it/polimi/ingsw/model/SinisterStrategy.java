package it.polimi.ingsw.model;

public class SinisterStrategy implements CharacterCardsStrategy{
    private String name;
    private int price;

    public SinisterStrategy(){
        name = "Sinister";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect(Player player) {

    }
}
