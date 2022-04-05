package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private static Player player;

    @BeforeAll
    public static void setUp() {
        player = new Player("elis");
    }

    @Test
    public void getNickname() {
        assertEquals("elis", player.getNickname());
    }


}