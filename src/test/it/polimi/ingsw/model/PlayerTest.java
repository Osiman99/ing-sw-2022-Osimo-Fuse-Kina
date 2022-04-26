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
        playerProva.moveStudentsFromCloudToEntrance(cloud);
        for(int i=0; i< cloud.getStudentsSize(); i++){
            assertEquals(students.get(i), plank.getEntrance().getStudents().get(plank.getEntrance().getStudents().size()-cloud.getStudentsSize()+i));
        }

    }

    @Test
    void moveStudentFromEntranceToDiningRoom() {
        Student stud = playerProva.getPlank().getEntrance().getStudents().get(0);
        playerProva.moveStudentFromEntranceToDiningRoom(stud);
        assertEquals(playerProva.getPlank().getDiningRoom()[stud.getColor().getCode()].getStudents().get(0), stud);

    }

    @Test
    void moveStudentFromEntranceToIsland() {
        Student stud = playerProva.getPlank().getEntrance().getStudents().get(0);
        playerProva.moveStudentFromEntranceToIsland(stud, game.getBoard().getIslands().get(0));
        assertEquals(stud.getColor(), game.getBoard().getIslands().get(0).getStudents().get(game.getBoard().getIslands().get(0).getStudents().size()-1).getColor());
    }

    @Test
    void chooseAssistantCard() {
    }

    @Test
    void chooseNumMoves () {
        /*playerProva.moveStudentsFromCloudToEntrance(game.getBoard().getClouds().get(0));
        Student stud = playerProva.getPlank().getEntrance().getStudents().get(0);
        playerProva.moveStudentFromEntranceToIsland(stud, game.getBoard().getIslands().get(0));
        assertEquals(game.getBoard().getIslands().get(0).getStudents().get(0), stud);
        */
    }
}