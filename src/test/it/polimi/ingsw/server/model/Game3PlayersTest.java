package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Game3PlayersTest {
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
        Game.resetInstance();
    }

    @Test
    void setInstance() {
        Game game2 = new Game();
        Game.setInstance(game2);
        assertEquals(game2, Game.getInstance());
    }

    @Test
    void getContPlayer() {
        assertEquals(3, game.getContPlayer());
    }

    @Test
    void getNicknames() {
        for(int i=0; i<3; i++)
            assertEquals(nicknames[i], game.getNicknames().get(i));
    }

    @Test
    void getNumPlayers() {
        assertEquals(3, game.getNumPlayers());
    }

    @Test
    void getPlayerByNickname() {
        Player player = new Player("LinusTorvalds");
        game.addPlayer(player);
        assertEquals(player, game.getPlayerByNickname("LinusTorvalds"));
    }

    @Test
    void isNicknameTaken() {
        assertFalse(game.isNicknameTaken("LinusTorvalds"));
        assertTrue(game.isNicknameTaken("GeorgeBoole"));
    }

    @Test
    void removePlayerByNickname() {
        assertTrue(game.removePlayerByNickname("AlanTuring"));
    }

    @Test
    void getBoard() {
        Board board = game.getBoard();
        assertEquals(board, game.getBoard());
    }

    @Test
    void getPlayers() {
        for(int i=0; i<3; i++)
            assertEquals(nicknames[i], game.getPlayers().get(i).getNickname());
    }

    @Test
    void chosenPlayersNumber() {
        game.setChosenPlayersNumber(2);
        assertEquals(2, game.getChosenPlayerNumber());
    }
}