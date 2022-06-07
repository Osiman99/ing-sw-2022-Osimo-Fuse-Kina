package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantDeckTest {
    private static AssistantDeck assistantDeck;

    @BeforeAll
    static void setUp() {
        assistantDeck = new AssistantDeck();
    }

    @AfterAll
    static void tearDown() {
        assistantDeck = null;
    }

    @Test
    void getDeck() {
        for(int i = 0; i < 10; i++) {
            assertEquals(i+1, assistantDeck.getDeck().get(i).getValue());
            assertEquals(i/2+1, assistantDeck.getDeck().get(i).getMaxMoves());
        }
    }
}