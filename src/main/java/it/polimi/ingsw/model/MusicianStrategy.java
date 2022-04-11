package it.polimi.ingsw.model;

public class MusicianStrategy implements CharacterCardsStrategy{
    public String name;
    public int price;

    public MusicianStrategy(){
        name = "Musician";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
