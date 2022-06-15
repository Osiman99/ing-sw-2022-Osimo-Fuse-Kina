package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStateTest {
    private Player player;

    @Test
    void state() {
        player = new Player("JJ");
        player.getState().setState("ACTION");
        assertEquals("ACTION", player.getState().getState());
    }
}