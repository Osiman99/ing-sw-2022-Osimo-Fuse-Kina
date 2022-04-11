package it.polimi.ingsw.model;

public class MerchantStrategy implements CharacterCardsStrategy{
    private String name;
    private int price;

    public MerchantStrategy(){
        name = "Merchant";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect(Player player) {

    }
}
