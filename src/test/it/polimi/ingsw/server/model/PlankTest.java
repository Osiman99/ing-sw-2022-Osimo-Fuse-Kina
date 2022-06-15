package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlankTest {
    private static Game game;
    private static String [] nicknames = {"AlanTuring", "JamesGosling", "GeorgeBoole"};
    private static Plank plank;

    @BeforeAll
    static void setUp() {
        game = Game.getInstance();
        for(int i=0; i<3; i++)
            game.getPlayers().add(new Player(nicknames[i]));
        game.setChosenPlayersNumber(3);
        game.initGame();
        plank = game.getPlayers().get(0).getPlank();
        System.out.println(game.getPlayers().get(0).getPlayerColor());
    }

    @Test
    void isTowerSpaceEmpty() {
        assertFalse(plank.isTowerSpaceEmpty());
        plank.getTowerSpace().getTowersList().clear();
        assertTrue(plank.isTowerSpaceEmpty());
    }

    @Test
    void getEntrance() {
        assertNotNull(plank.getEntrance());
    }

    @Test
    void getDiningRoom() {
        assertNotNull(plank.getDiningRoom());
    }

    @Test
    void getTowerSpace() {
        assertNotNull(plank.getTowerSpace());
    }

}