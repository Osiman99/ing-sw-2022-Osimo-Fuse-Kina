package it.polimi.ingsw.model;

import java.util.List;

public class GameExpert extends Game{

    @Override
    public void initGame(Game game, List<String> nicknames, int chosenPlayersNumber) {
        super.initGame(game, nicknames, chosenPlayersNumber);
        //INIZIALIZZARE LE 3 CARTE EFFETTO
    }
}
