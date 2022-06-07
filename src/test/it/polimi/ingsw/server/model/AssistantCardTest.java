package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantCardTest {
    private static AssistantCard assistantCard;

    @BeforeAll
    static void setUp() {
        assistantCard= new AssistantCard(7,4);
    }

    @AfterAll
    static void tearDown() {
        assistantCard = null;
    }

    @Test
    void getValue() {
        assertEquals(7, assistantCard.getValue());
    }

    @Test
    void getMaxMoves() {
        assertEquals(4, assistantCard.getMaxMoves());
    }

}