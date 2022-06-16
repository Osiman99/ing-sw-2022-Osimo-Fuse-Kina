package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class Board2PlayersTest {
    private Game game;
    private final String [] nicknames = {"AlanTuring", "JamesGosling"};
    private Board board;


    @BeforeEach
    void setUp() {
        game = Game.getInstance();
        for(int i=0; i<2; i++)
            game.addPlayer(new Player(nicknames[i]));
        game.setChosenPlayersNumber(2);
        game.getPlayers().get(0).setPlayerColor(TowerColor.BLACK);
        game.getPlayers().get(1).setPlayerColor(TowerColor.WHITE);
        game.initGame();
        board = game.getBoard();

    }

    @AfterEach
    void tearDown() {
        Game.resetInstance();
    }

    @Test
    void calculateSupremacy() {
        Island island = new Island();

        //salvo l'isola in cui c'è MN e aggiungo una torre WHITE e aggiungo una torre BLACK all'isola successiva
        for (int i=0; i<12; i++) {
            if (board.getIslands().get(i).isMotherNature()) {
                board.moveTowerFromPlankToIsland(game.getPlayerByNickname("AlanTuring"), board.getIslands().get((i+1)%12));
                board.moveTowerFromPlankToIsland(game.getPlayerByNickname("JamesGosling"), board.getIslands().get(i));
                island = board.getIslands().get(i);
            }
        }

        //aggiungo all'isola 1 studente per colore e uno studente RED aggiuntivo
        for(int i=0; i<5; i++)
            island.addStudent(new Student(StudentColor.getStudentColor(i)));
        island.addStudent(new Student(StudentColor.RED));

        //setto manualmente quali giocatori controllani quali professori
        board.setProfessorsControlledBy(new String[]{"AlanTuring", "AlanTuring", "AlanTuring", "JamesGosling", "AlanTuring"});

        board.calculateSupremacy(island);

        assertEquals(TowerColor.BLACK, island.getTowers().get(0).getColor());
        assertEquals(2, island.getTowers().size());
        assertEquals(11, board.getIslands().size());
    }

    @Test
    void calculateSupremacyEmptyIsland() {
        Island island = new Island();

        //salvo l'isola in cui c'è MN e aggiungo una torre WHITE e aggiungo una torre BLACK all'isola successiva
        for (int i=0; i<12; i++) {
            if (board.getIslands().get(i).isMotherNature()) {
                island = board.getIslands().get(i);
            }
        }

        //aggiungo all'isola 1 studente per colore e uno studente RED aggiuntivo
        for(int i=0; i<5; i++)
            island.addStudent(new Student(StudentColor.getStudentColor(i)));
        island.addStudent(new Student(StudentColor.RED));

        //setto manualmente quali giocatori controllani quali professori
        board.setProfessorsControlledBy(new String[]{"AlanTuring", "AlanTuring", "AlanTuring", "JamesGosling", "AlanTuring"});

        board.calculateSupremacy(island);

        assertEquals(TowerColor.BLACK, island.getTowers().get(0).getColor());
        assertEquals(1, island.getTowers().size());
    }

}
