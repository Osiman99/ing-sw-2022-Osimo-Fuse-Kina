package it.polimi.ingsw.model;

import java.util.List;

/**
 * in this class we have to use the character cards
 */
public class GameExpert extends Game{

    @Override
    public void initGame(Game game, List<String> nicknames, int chosenPlayersNumber) {
        super.initGame(game, nicknames, chosenPlayersNumber);
        //INIZIALIZZARE LE 3 CARTE EFFETTO
    }
}
