package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private Plank plank;
    private PlayerState state;
    private AssistantDeck deck;
    private int numCoins;

    @BeforeEach
    void setUp() {
        player = new Player("elis");
        plank = player.getPlank();
        deck = player.getDeck();
        numCoins = player.getNumCoins();
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
        assertEquals(plank, player.getPlank());
    }

    @Test
    void getState() {
        assertEquals(PlayerState.SLEEP, player.getState());
    }

    @Test
    void getDeck() {
        assertEquals(deck, player.getDeck());
    }

    @Test
    void getNumCoins() {
        numCoins = numCoins + 3;
        assertEquals(numCoins, 4);
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