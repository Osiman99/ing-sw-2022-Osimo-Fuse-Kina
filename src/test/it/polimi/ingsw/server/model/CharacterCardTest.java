package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {
    private static final CharacterCard characterCard = new CharacterCard(CharacterName.Lady);
    private static Game game;
    private static final String [] nicknames = {"AlanTuring", "JamesGosling"};


    @BeforeAll
    static void setUp() {
        game = Game.getInstance();
        for(int i=0; i<2; i++)
            game.addPlayer(new Player(nicknames[i]));
        game.setChosenPlayersNumber(2);
        game.getPlayers().get(0).setPlayerColor(TowerColor.BLACK);
        game.getPlayers().get(1).setPlayerColor(TowerColor.WHITE);
        game.initGame();

    }

    @Test
    void getCharacterName() {
        assertEquals(CharacterName.Lady, characterCard.getCharacterName());
    }

    @Test
    void enabled() {
        characterCard.setEnabled(true);
        assertTrue(characterCard.isEnabled());
    }


    @Test
    void getStudents() {
        List<Student> students = characterCard.getStudents();
        assertEquals(students, characterCard.getStudents());
    }

    @Test
    void getPrice() {
        assertEquals(2, characterCard.getPrice());
    }

    @Test
    void setPrice() {
        characterCard.setPrice(6);
        assertEquals(6, characterCard.getPrice());
    }
}