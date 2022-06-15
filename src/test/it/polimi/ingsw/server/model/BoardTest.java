package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Game game;
    private String [] nicknames = {"AlanTuring", "JamesGosling", "GeorgeBoole"};
    private Board board;


    @BeforeEach
    void setUp() {
        game = Game.getInstance();
        for(int i=0; i<3; i++)
            game.addPlayer(new Player(nicknames[i]));
        game.setChosenPlayersNumber(3);
        game.getPlayers().get(0).setPlayerColor(TowerColor.BLACK);
        game.getPlayers().get(1).setPlayerColor(TowerColor.WHITE);
        game.getPlayers().get(2).setPlayerColor(TowerColor.GREY);
        game.initGame();
        board = game.getBoard();

    }

    @AfterEach
    void tearDown() {
        game.resetInstance();
    }

    @Test
    void getBag() {
        Bag bag = board.getBag();
        assertEquals(bag, board.getBag());
    }

    @Test
    void getClouds() {
        List<Cloud> clouds = board.getClouds();
        assertEquals(clouds, board.getClouds());
    }

    @Test
    void getIslands() {
        List<Island> islands = board.getIslands();
        assertEquals(islands, board.getIslands());
    }

    @Test
    void getProfessorsControlledBy() {
        board.getProfessorsControlledBy()[0] = nicknames[1];
        board.getProfessorsControlledBy()[3]= nicknames[0];
        board.getProfessorsControlledBy()[4] = nicknames[2];

        String[] professors = {"JamesGosling", "", "", "AlanTuring", "GeorgeBoole"};
        for(int i=0; i<5; i++)
            assertEquals(professors[i], board.getProfessorsControlledBy()[i]);
    }

    @Test
    void moveStudentsFromBagToClouds() {
        List<Student> students = new ArrayList<Student>();
        int i,j,k;

        for(i=0; i< game.getNumPlayers()*(game.getNumPlayers()+1); i++)         // #nuvole x #studentiPerNuvola : sposto i primi 12 studenti dalla Bag
            students.add(game.getBoard().getBag().getStudents().get(i));


        game.getBoard().moveStudentsFromBagToClouds();


        for(i=0, j=0; j<game.getBoard().getClouds().size(); j++){
            for(k=0; k<game.getBoard().getClouds().get(j).getStudentsSize(); k++,i++) {
                assertEquals(students.get(i), game.getBoard().getClouds().get(j).getStudents().get(k));
            }
        }

    }


    //this method tests moveTowerFromPlankToIsland and moveTowerFromIslandToPlank at the same time
    @Test
    void moveTower() {
        Tower tower = game.getPlayers().get(0).getPlank().getTowerSpace().getFirstTower();
        game.getBoard().moveTowerFromPlankToIsland(game.getPlayers().get(0), game.getBoard().getIslands().get(0));
        assertEquals(tower, game.getBoard().getIslands().get(0).getFirstTower());

        game.getBoard().moveTowerFromIslandToPlank(game.getBoard().getIslands().get(0));

        assertEquals(tower.getColor(), game.getPlayers().get(0).getPlank().getTowerSpace().getTowersList().get(game.getPlayers().get(0).getPlank().getTowerSpace().getTowersList().size()-1).getColor());
    }


}