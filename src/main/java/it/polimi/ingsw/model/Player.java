package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String nickname;
    private Plank plank;
    private PlayerState state;
    private AssistantDeck deck;
    private int numCoins = 1;

    public Player(String nickname){
        this.nickname = nickname;
        plank = new Plank();
        state = PlayerState.SLEEP;
        deck = new AssistantDeck();
    }

    public String getNickname(){
        return nickname;
    }

    public Plank getPlank() {
        return plank;
    }

    public PlayerState getState() {
        return state;
    }

    public AssistantDeck getDeck() {
        return deck;
    }

    public int getNumCoins() {
        return numCoins;
    }


}

