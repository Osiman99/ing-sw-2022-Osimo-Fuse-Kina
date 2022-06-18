package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameExpertTest {

    private static GameExpert game;
    private static List<String> nicknames = List.of(new String[]{"AlanTuring", "JamesGosling", "GeorgeBoole"});

    @BeforeAll
    static void setUp() {
        game = new GameExpert();
        for(int i=0; i<3; i++)
            game.getPlayers().add(new Player(nicknames.get(i)));
        game.setChosenPlayersNumber(3);
        game.getPlayers().get(0).setPlayerColor(TowerColor.BLACK);
        game.getPlayers().get(1).setPlayerColor(TowerColor.WHITE);
        game.getPlayers().get(2).setPlayerColor(TowerColor.GREY);
        game.initGame();
        game.initGameExpert();

    }

    @Test
    void getThreeChosenCards() {
        assertEquals(3, game.getThreeChosenCards().size());
    }
}