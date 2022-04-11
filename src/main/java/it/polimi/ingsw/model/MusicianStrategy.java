package it.polimi.ingsw.model;

public class MusicianStrategy implements CharacterCardsStrategy{
    private String name;
    private int price;

    public MusicianStrategy(){
        name = "Musician";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect(Player player) {

    }
}
