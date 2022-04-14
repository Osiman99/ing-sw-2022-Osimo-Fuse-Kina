package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Game game;
    private Player playerProva;
    private Plank plank;
    private PlayerState state;
    private AssistantDeck deck;
    private int numCoins;
    private ArrayList<Student> students;
    private Cloud cloud;

    @BeforeEach
    void setUp() {
        List<String> nicknames = new ArrayList<String>();

        nicknames.add("davide");
        nicknames.add("riise");
        nicknames.add("elis");
        game = new Game();
        game.initGame(game, nicknames, 3);
        students = new ArrayList<Student>();
        playerProva=game.getPlayers().get(0);
        plank = playerProva.getPlank();
        deck = playerProva.getDeck();
        numCoins = playerProva.getNumCoins();
        cloud = game.getBoard().getClouds().get(0);

        game.getBoard().moveStudentsFromBagToClouds();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getNickname() {
        assertEquals("davide", playerProva.getNickname());
    }

    @Test
    void getPlank() {
        assertEquals(plank, playerProva.getPlank());
    }

    @Test
    void getState() {
        assertEquals(PlayerState.SLEEP, playerProva.getState());
    }

    @Test
    void getDeck() {
        assertEquals(deck, playerProva.getDeck());
    }

    @Test
    void getNumCoins() {
        numCoins = numCoins + 3;
        assertEquals(numCoins, 4);
    }

    @Test
    void moveStudentsFromCloudToEntrance() {

        for(int i=0;i<cloud.getStudentsSize();i++) {
            students.add(cloud.getStudents().get(i));
        }
        playerProva.moveStudentsFromCloudToEntrance(game.getBoard().getClouds().get(0));
        for(int i=0; i< cloud.getStudentsSize(); i++){
            assertEquals(students.get(i), plank.getEntrance().getStudents().get(plank.getEntrance().getStudents().size()-game.getBoard().getClouds().get(0).getStudentsSize()+i));
        }

    }

    @Test
    void moveStudentFromEntranceToDiningRoom() {
        /*
        playerProva.moveStudentsFromCloudToEntrance(game.getBoard().getClouds().get(0));
        System.out.println(game.getBoard().getClouds().size());
        System.out.println(plank.getEntrance().getStudents().size());
        playerProva.moveStudentFromEntranceToDiningRoom(playerProva.getPlank().getEntrance().getStudents().get(0));
        assertEquals(playerProva.getPlank().getDiningRoom()[playerProva.getPlank().getEntrance().getStudents().get(0).getColor().getCode()].getStudents().get(0), playerProva.getPlank().getEntrance().getStudents().get(0));
        */
    }

    @Test
    void moveStudentFromEntranceToIsland() {
    }
}