package it.polimi.ingsw.model;

public class MerchantStrategy implements CharacterCardsStrategy{
    public String name;
    public int price;

    public MerchantStrategy(){
        name = "Merchant";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
