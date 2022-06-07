package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TowerSpaceTest {
    private TowerSpace towerSpace;
    private Game game;
    private String [] nicknames = {"AlanTuring", "JamesGosling", "GeorgeBoole"};
    private Player player;
    private Tower tower;

    @BeforeEach
    void setUp() {
        game = Game.getInstance();
        for(int i=0; i<3; i++)
            game.getPlayers().add(new Player(nicknames[i]));
        game.setChosenPlayersNumber(3);
        game.initGame();
        player = Game.getInstance().getPlayers().get(0);
        towerSpace = player.getPlank().getTowerSpace();
    }

    @AfterEach
    void tearDown() {
        Game.resetInstance();
    }

    @Test
    void getTowersList() {
        for(Tower t : towerSpace.getTowersList())
            assertEquals(TowerColor.BLACK, t.getColor());
    }

    @Test
    void getFirstTower() {
        assertEquals(TowerColor.BLACK, towerSpace.getFirstTower().getColor());
    }

    @Test
    void addTower() {
        tower = new Tower(TowerColor.BLACK);
        towerSpace.addTower(tower);
        assertEquals(tower, towerSpace.getTowersList().get(towerSpace.getTowersList().size()-1));
    }

    @Test
    void removeTower() {
        tower = towerSpace.getFirstTower();
        towerSpace.removeTower();
        assertNotEquals(tower, towerSpace.getFirstTower());
    }
}