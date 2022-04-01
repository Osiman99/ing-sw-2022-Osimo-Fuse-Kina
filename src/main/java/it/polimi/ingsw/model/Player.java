package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String nickname;
    private Plank plank;
    private PlayerState state;
    private AssistantDeck deck;
    private int numCoins = 1;

    public Player(){
        //fai cose
    }

    public String getNickname() {
        return nickname;
    }


}

