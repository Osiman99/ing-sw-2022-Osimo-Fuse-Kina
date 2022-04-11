package it.polimi.ingsw.model;

public class SinisterStrategy implements CharacterCardsStrategy{
    public String name;
    public int price;

    public SinisterStrategy(){
        name = "Sinister";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
