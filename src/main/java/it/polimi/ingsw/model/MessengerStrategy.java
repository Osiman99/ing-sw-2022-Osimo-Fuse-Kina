package it.polimi.ingsw.model;

public class MessengerStrategy implements CharacterCardsStrategy{
    private String name;
    private int price;

    public MessengerStrategy(){
        name = "Messenger";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect(Player player) {

    }
}
