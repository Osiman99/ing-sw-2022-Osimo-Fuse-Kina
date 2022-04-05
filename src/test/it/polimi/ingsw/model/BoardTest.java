package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    /*
    private static Game game;
    private static Student student;
    private static StudentColor red;
    private static Cloud cloud;
    private static Player player


    @BeforeEach
    void setUp() {
        game = new Game();
        game.addPlayer(player);
        red = StudentColor.RED;
        if (game.getNumPlayers() == 2) {
            for (int i = 0; i < 2; i++) {
                cloud = new Cloud();
                game.getBoard().addCloud(cloud);
                for (int j = 0; j < 3; j++) {
                    student = new Student(red);
                    game.getBoard().getBag().addStudent(student);
                }
            }
        }else if (game.getNumPlayers() == 3) {
            for (int i = 0; i < 3; i++) {
                cloud = new Cloud();
                game.getBoard().addCloud(cloud);
                for (int j = 0; j < 4; j++) {
                    student = new Student(red);
                    game.getBoard().getBag().addStudent(student);
                }
            }

        }

    }
    */

    @AfterEach
    void tearDown() {
    }

    @Test
    void getBag() {
    }

    @Test
    void getInstance() {
    }

    /*
    @Test
    void moveStudentsFromBagToClouds() {
        game.getBoard().moveStudentsFromBagToClouds();
        assertEquals(student, game.getBoard());
    }
    */

    @Test
    void moveTowerFromPlankToIsland() {
    }

    @Test
    void addIsland() {
    }

    @Test
    void addCloud() {
    }

    @Test
    void moveTowerFromIslandToPlank() {
    }
}