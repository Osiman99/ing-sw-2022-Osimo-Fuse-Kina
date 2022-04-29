package it.polimi.ingsw.model;

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

        for(int i=0; i< game.getNumPlayers()*(game.getNumPlayers()+1); i++){           // #nuvole x #studentiPerNuvola : sposto i primi 12 studenti dalla Bag
            students.add(game.getBoard().getBag().getStudents().get(i));
        }
        game.getBoard().moveStudentsFromBagToClouds();
        int i=0;
        for(int j=0; j<game.getBoard().getClouds().size(); j++){
            for(int k=0; k<game.getBoard().getClouds().get(j).getStudentsSize(); k++,i++) {
                assertEquals(students.get(i), game.getBoard().getClouds().get(j).getStudents().get(k));
            }
        }

    }


    //this method tests moveTowerFromPlankToIsland and moveTowerFromIslandToPlank both
    @Test
    void moveTower() {
        Tower tower = game.getPlayers().get(0).getPlank().getTowerSpace().getFirstTower();
        game.getBoard().moveTowerFromPlankToIsland(game.getPlayers().get(0), game.getBoard().getIslands().get(0));
        assertEquals(tower, game.getBoard().getIslands().get(0).getFirstTower());
        game.getBoard().moveTowerFromIslandToPlank(game.getBoard().getIslands().get(0));
        assertEquals(tower, game.getPlayers().get(0).getPlank().getTowerSpace().getTowersList().get(game.getPlayers().get(0).getPlank().getTowerSpace().getTowersList().size()-1));
    }


    @Test
    void moveProfessor() {
        //creo degli studenti da aggiungere alle Dining Rooms dei players per ricreare delle situazioni di gioco
        Student sRed=new Student(StudentColor.RED);
        Student sGreen=new Student(StudentColor.GREEN);
        Student sPink=new Student(StudentColor.PINK);
        Student sYellow=new Student(StudentColor.YELLOW);
        //player 0 ha 2 REDstudent, player 1 ha 1 REDstudents, player 2 ha 1 REDstudent
        game.getPlayers().get(1).getPlank().getDiningRoom()[StudentColor.RED.getCode()].addStudent(sRed);
        game.getBoard().moveProfessor();
        game.getPlayers().get(0).getPlank().getDiningRoom()[StudentColor.RED.getCode()].addStudent(sRed);
        game.getBoard().moveProfessor();
        game.getPlayers().get(0).getPlank().getDiningRoom()[StudentColor.RED.getCode()].addStudent(sRed);
        game.getBoard().moveProfessor();
        game.getPlayers().get(2).getPlank().getDiningRoom()[StudentColor.RED.getCode()].addStudent(sRed);
        game.getBoard().moveProfessor();
        //player 2 ha 1 GREENstudent
        game.getPlayers().get(2).getPlank().getDiningRoom()[StudentColor.GREEN.getCode()].addStudent(sGreen);
        game.getBoard().moveProfessor();
        //tutti i player hanno 1 PINKstudent (il professore è del primo player ad aver spostato lo studente)
        game.getPlayers().get(1).getPlank().getDiningRoom()[StudentColor.PINK.getCode()].addStudent(sPink);
        game.getBoard().moveProfessor();
        game.getPlayers().get(0).getPlank().getDiningRoom()[StudentColor.PINK.getCode()].addStudent(sPink);
        game.getBoard().moveProfessor();
        game.getPlayers().get(2).getPlank().getDiningRoom()[StudentColor.PINK.getCode()].addStudent(sPink);
        game.getBoard().moveProfessor();
        //player 1 ha 2 YELLOWstudent, player 2 ha 3 YELLOWstudent
        game.getPlayers().get(2).getPlank().getDiningRoom()[StudentColor.YELLOW.getCode()].addStudent(sYellow);
        game.getBoard().moveProfessor();
        game.getPlayers().get(1).getPlank().getDiningRoom()[StudentColor.YELLOW.getCode()].addStudent(sYellow);
        game.getBoard().moveProfessor();
        game.getPlayers().get(1).getPlank().getDiningRoom()[StudentColor.YELLOW.getCode()].addStudent(sYellow);
        game.getBoard().moveProfessor();
        game.getPlayers().get(2).getPlank().getDiningRoom()[StudentColor.YELLOW.getCode()].addStudent(sYellow);
        game.getBoard().moveProfessor();
        game.getPlayers().get(2).getPlank().getDiningRoom()[StudentColor.YELLOW.getCode()].addStudent(sYellow);
        game.getBoard().moveProfessor();
        //nessun player ha BLUEstudent (nessuno possiede il professore)

        assertEquals(game.getPlayers().get(0).getNickname(), game.getBoard().getProfessorsControlledBy()[StudentColor.RED.getCode()]);
        assertEquals(game.getPlayers().get(2).getNickname(), game.getBoard().getProfessorsControlledBy()[StudentColor.GREEN.getCode()]);
        assertEquals(game.getPlayers().get(1).getNickname(), game.getBoard().getProfessorsControlledBy()[StudentColor.PINK.getCode()]);
        assertEquals(game.getPlayers().get(2).getNickname(), game.getBoard().getProfessorsControlledBy()[StudentColor.YELLOW.getCode()]);
        assertEquals("", game.getBoard().getProfessorsControlledBy()[StudentColor.BLUE.getCode()]);
    }

    @Test
    void moveMotherNature() {
        //salvo l'indice della lista dell'isola attiva
        int i=0;
        for(; i<game.getBoard().getIslands().size(); i++)
            if(game.getBoard().getIslands().get(i).isMotherNature())
                break;

        //genero un numero a caso da 1 a 5 che rappresenta il numero di mosse scelte dal player
        Random random =new Random();
        int randomInt = random.nextInt(4)+1;
        game.getBoard().moveMotherNature(randomInt);

        //salvo l'indice della nuova isola attiva
        int j=0;
        for(; j<game.getBoard().getIslands().size(); j++)
            if(game.getBoard().getIslands().get(j).isMotherNature())
                break;
        assertEquals(game.getBoard().getIslands().get(i+randomInt), game.getBoard().getIslands().get(j));

    }
}