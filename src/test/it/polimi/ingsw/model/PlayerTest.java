package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("elis");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getNickname() {
        assertEquals("elis", player.getNickname());
    }

    @Test
    void getPlank() {
    }

    @Test
    void getState() {
    }

    @Test
    void getDeck() {
    }

    @Test
    void getNumCoins() {
    }

    @Test
    void moveStudentsFromCloudToEntrance() {
    }

    @Test
    void moveStudentFromEntranceToDiningRoom() {
    }

    @Test
    void moveStudentFromEntranceToIsland() {
    }
}