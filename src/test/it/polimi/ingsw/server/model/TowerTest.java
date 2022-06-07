package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TowerTest {
    Tower tower = new Tower(TowerColor.BLACK);

    @Test
    void getColor() {
        assertEquals(TowerColor.BLACK, tower.getColor());
    }
}