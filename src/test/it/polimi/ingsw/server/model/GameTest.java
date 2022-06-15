package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    private String[] nicknames = {"AlanTuring", "JamesGosling", "GeorgeBoole"};

    @BeforeEach
    void setUp() {
        game = Game.getInstance();
        for(int i=0; i<3; i++)
            game.addPlayer(new Player(nicknames[i]));
        game.setChosenPlayersNumber(3);
        game.getPlayers().get(0).setPlayerColor(TowerColor.BLACK);
        game.getPlayers().get(1).setPlayerColor(TowerColor.WHITE);
        game.getPlayers().get(2).setPlayerColor(TowerColor.GREY);
        game.initGame();
    }

    @AfterEach
    void tearDown() {
        game.resetInstance();
    }

    @Test
    void setInstance() {
    }


    @Test
    void getContPlayer() {
        assertEquals(0, game.getContPlayer());
    }

    @Test
    void getNicknames() {
        for(int i=0; i<3; i++)
            assertEquals(nicknames[i], game.getNicknames().get(i));
    }

    @Test
    void getNumPlayers() {
    }

    @Test
    void getPlayerByNickname() {
    }

    @Test
    void isNicknameTaken() {
    }

    @Test
    void removePlayerByNickname() {
    }

    @Test
    void resetInstance() {
    }

    @Test
    void getBoard() {
    }

    @Test
    void getPlayers() {
    }

    @Test
    void setChosenPlayersNumber() {
    }
}