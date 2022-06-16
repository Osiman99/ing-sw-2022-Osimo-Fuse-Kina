package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;


public class Game2PlayersTest {
    private Game game;
    private String[] nicknames = {"AlanTuring", "JamesGosling"};

    @Test
    void setUp() {
        game = Game.getInstance();
        for(int i=0; i<2; i++)
            game.addPlayer(new Player(nicknames[i]));
        game.setChosenPlayersNumber(2);
        game.getPlayers().get(0).setPlayerColor(TowerColor.BLACK);
        game.getPlayers().get(1).setPlayerColor(TowerColor.WHITE);
        game.initGame();
    }

    @AfterAll
    static void tearDown() {
        Game.resetInstance();
    }

}
