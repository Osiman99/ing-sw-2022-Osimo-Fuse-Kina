package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String nickname;
    private Plank plank;
    private PlayerState state;
    private int numCoins = 1;
    private List<Plank> planks = new ArrayList<Plank>();
}
