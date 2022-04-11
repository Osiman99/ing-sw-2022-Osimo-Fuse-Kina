package it.polimi.ingsw.model;

public class MessengerStrategy implements CharacterCardsStrategy{
    public String name;
    public int price;

    public MessengerStrategy(){
        name = "Messenger";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
