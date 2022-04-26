package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {


    private Game game;
    private Student student;
    private ArrayList<Student> students;
    private StudentColor red;
    private Cloud cloud;
    private Player player;


    @BeforeEach
    void setUp() {
        List<String> nicknames = new ArrayList<String>();
        students = new ArrayList<Student>();
        nicknames.add("davide");
        nicknames.add("riise");
        nicknames.add("elis");
        game = new Game();
        game.initGame(game, nicknames, 3);
        /*
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
    */
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void moveStudentsFromBagToClouds() {

        /*
        for(int i=0; i< 12; i++){           //4 studenti x3 nuvole =12 : sposto i primi 12 studenti dalla Bag
            students.add(game.getBoard().getBag().getFirstStudent());
        }
        game.getBoard().moveStudentsFromBagToClouds();
        int i=0;
        for(int j=0; j<game.getBoard().getClouds().size(); j++){
            for(int k=0; k<game.getBoard().getClouds().get(j).getStudentsSize(); k++,i++) {
                System.out.println(i);
                System.out.println(j);
                System.out.println(k);
                assertEquals(students.get(i), game.getBoard().getClouds().get(j).getStudents().get(k));
            }
        }
        */
    }

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