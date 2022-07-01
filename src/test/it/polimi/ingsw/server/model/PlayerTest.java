package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {


    private static Game game;
    private static String [] nicknames = {"AlanTuring", "JamesGosling", "GeorgeBoole"};
    private static Player player;


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
        player = game.getPlayers().get(0);

    }

    @AfterEach
    void tearDown() {
        Game.resetInstance();
    }

    @Test
    void getNickname() {
        assertEquals("AlanTuring", player.getNickname());
    }

    @Test
    void supremacyCont() {
        player.setSupremacyCont(3);
        assertEquals(3, player.getSupremacyCont());
    }

    @Test
    void getPlank() {
        Plank plank = player.getPlank();
        assertEquals(plank, player.getPlank());
    }

    @Test
    void getDeck() {
        AssistantDeck deck = player.getDeck();
        assertEquals(deck, player.getDeck());
    }

    @Test
    void getChosenAssistantCard() {
        player.chooseAssistantCard(8);
        assertEquals(8, player.getChosenAssistantCard().getValue());
        assertEquals(4, player.getChosenAssistantCard().getMaxMoves());
    }

    @Test
    void playerColor() {
        player.setPlayerColor(TowerColor.GREY);
        assertEquals(TowerColor.GREY, player.getPlayerColor());
    }

    @Test
    void towerCont() {
        player.setTowerCont(4);
        assertEquals(4, player.getTowerCont());
    }

    @Test
    void numCoins() {
        player.setNumCoins(3);
        assertEquals(3, player.getNumCoins());
    }

    @Test
    void moveStudentsFromCloudToEntrance() {
        Cloud cloud = new Cloud();
        for(int i=0; i<4; i++)
            cloud.getStudents().add(new Student(StudentColor.getStudentColor(i)));

        List<Student> students = new ArrayList<>();
        for(int i=0;i<cloud.getStudentsSize();i++)
            students.add(cloud.getStudents().get(i));

        player.moveStudentsFromCloudToEntrance(cloud);
        for(int i=0; i< cloud.getStudentsSize(); i++)
            assertEquals(students.get(i), player.getPlank().getEntrance().getStudents().get(player.getPlank().getEntrance().getStudents().size()-cloud.getStudentsSize()+i));

    }

    @Test
    void moveStudentFromEntranceToDiningRoom() {
        Student stud = player.getPlank().getEntrance().getStudents().get(0);
        player.moveStudentFromEntranceToDiningRoom(stud);
        assertEquals(stud, player.getPlank().getDiningRoom()[stud.getColor().getCode()].getStudents().get(0));

    }

    @Test
    void moveStudentFromEntranceToIsland() {
        Student stud = player.getPlank().getEntrance().getStudents().get(0);
        player.moveStudentFromEntranceToIsland(stud, game.getBoard().getIslands().get(0));
        assertEquals(stud.getColor(), game.getBoard().getIslands().get(0).getStudents().get(game.getBoard().getIslands().get(0).getStudents().size()-1).getColor());
    }

    @Test
    void chooseAssistantCard() {
        player.chooseAssistantCard(4);
        assertEquals(5, player.getDeck().getDeck().get(3).getValue());
        assertEquals(3, player.getDeck().getDeck().get(3).getMaxMoves());
    }

    @Test
    void isTowerSpaceEmpty() {
        assertFalse(game.getPlayerByNickname("AlanTuring").isTowerSpaceEmpty());
    }

    @Test
    void isDeckEmpty() {
        assertFalse(game.getPlayerByNickname("AlanTuring").isDeckEmpty());
    }
}