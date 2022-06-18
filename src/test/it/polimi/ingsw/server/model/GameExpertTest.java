package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameExpertTest {
    private static GameExpert gameExpert;
    private static final List<String> nicknames = List.of(new String[]{"AlanTuring", "JamesGosling", "GeorgeBoole"});

    @BeforeAll
    static void setUp() {
        gameExpert = (GameExpert) Game.getInstance();
        for(int i=0; i<3; i++)
            gameExpert.getPlayers().add(new Player(nicknames.get(i)));
        gameExpert.setChosenPlayersNumber(3);
        gameExpert.getPlayers().get(0).setPlayerColor(TowerColor.BLACK);
        gameExpert.getPlayers().get(1).setPlayerColor(TowerColor.WHITE);
        gameExpert.getPlayers().get(2).setPlayerColor(TowerColor.GREY);
        gameExpert.initGame();

    }

    @Test
    void getThreeChosenCards() {
        assertEquals(3, gameExpert.getThreeChosenCards().size());
    }
}