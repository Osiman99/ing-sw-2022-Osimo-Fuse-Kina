package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Board3PlayersTest {

    private Game game;
    private final String [] nicknames = {"AlanTuring", "JamesGosling", "GeorgeBoole"};
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
        Game.resetInstance();
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
    void gameInstance() {
        Game game2 = new Game();
        board.setGameInstance(game2);
        assertEquals(game2, board.getGameInstance());
    }

    @Test
    void moveStudentsFromBagToClouds() {
        List<Student> students = new ArrayList<Student>();
        int i,j,k;

        for(i=0; i< game.getNumPlayers()*(game.getNumPlayers()+1); i++)         // #nuvole x #studentiPerNuvola : sposto i primi 12 studenti dalla Bag
            students.add(board.getBag().getStudents().get(i));

        board.moveStudentsFromBagToClouds();

        for(i=0, j=0; j< board.getClouds().size(); j++){
            for(k=0; k< board.getClouds().get(j).getStudentsSize(); k++,i++) {
                assertEquals(students.get(i), board.getClouds().get(j).getStudents().get(k));
            }
        }

    }


    //this method tests moveTowerFromPlankToIsland and moveTowerFromIslandToPlank at the same time
    @Test
    void moveTower() {
        Tower tower = game.getPlayers().get(0).getPlank().getTowerSpace().getFirstTower();
        board.moveTowerFromPlankToIsland(game.getPlayers().get(0), board.getIslands().get(0));
        assertEquals(tower, board.getIslands().get(0).getFirstTower());

        board.moveTowerFromIslandToPlank(game.getBoard().getIslands().get(0));

        assertEquals(tower.getColor(), game.getPlayers().get(0).getPlank().getTowerSpace().getTowersList().get(game.getPlayers().get(0).getPlank().getTowerSpace().getTowersList().size()-1).getColor());
    }


    @Test
    void moveProfessorNormal() {
        Student sRed=new Student(StudentColor.RED);

        //player 0 ha 2 REDstudent, player 1 ha 1 REDstudents, player 2 ha 1 REDstudent
        game.getPlayers().get(1).getPlank().getDiningRoom()[StudentColor.RED.getCode()].addStudent(sRed);
        board.moveProfessor(game.getPlayers().get(1));
        game.getPlayers().get(0).getPlank().getDiningRoom()[StudentColor.RED.getCode()].addStudent(sRed);
        board.moveProfessor(game.getPlayers().get(0));
        game.getPlayers().get(0).getPlank().getDiningRoom()[StudentColor.RED.getCode()].addStudent(sRed);
        board.moveProfessor(game.getPlayers().get(0));
        game.getPlayers().get(2).getPlank().getDiningRoom()[StudentColor.RED.getCode()].addStudent(sRed);
        board.moveProfessor(game.getPlayers().get(2));

        assertEquals(game.getPlayers().get(0).getNickname(), board.getProfessorsControlledBy()[StudentColor.RED.getCode()]);
    }

    @Test
    void moveProfessorExpert() {

    }

    @Test
    void moveProfessorChef() {
        board.setProfessorsControlledBy(new String[]{"AlanTuring", "", "AlanTuring", "JamesGosling", "GeorgeBoole"});

        for(int i=0; i<4; i++) {
            game.getPlayerByNickname("AlanTuring").getPlank().getDiningRoom()[0].addStudent(new Student(StudentColor.GREEN));
            game.getPlayerByNickname("JamesGosling").getPlank().getDiningRoom()[0].addStudent(new Student(StudentColor.GREEN));
        }

        board.moveProfessorChef(game.getPlayerByNickname("JamesGosling"));
        assertEquals("JamesGosling", board.getProfessorsControlledBy()[0]);
    }

    @Test
    void moveMotherNature() {

        //salvo l'indice della lista dell'isola attiva
        int i=0;
        for(; i<board.getIslands().size(); i++)
            if(board.getIslands().get(i).isMotherNature())
                break;

        //genero un numero a caso da 1 a 5 che rappresenta il numero di mosse scelte dal player
        Random random =new Random();
        int randomInt = random.nextInt(4)+1;

        board.moveMotherNature(randomInt);

        //salvo l'indice della nuova isola attiva
        int j=0;
        for(; j<board.getIslands().size()-1; j++)
            if(board.getIslands().get(j).isMotherNature())
                break;
        assertEquals(board.getIslands().get((i+randomInt)%12), board.getIslands().get(j));

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
        board.setProfessorsControlledBy(new String[]{"AlanTuring", "AlanTuring", "AlanTuring", "JamesGosling", "GeorgeBoole"});

        board.calculateSupremacy(island);

        assertEquals(TowerColor.BLACK, island.getTowers().get(0).getColor());
        assertEquals(2, island.getTowers().size());
        assertEquals(11, board.getIslands().size());
    }

    @Test
    void conquerIsland() {
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
        board.setProfessorsControlledBy(new String[]{"JamesGosling", "JamesGosling", "AlanTuring", "JamesGosling", "GeorgeBoole"});

        board.calculateSupremacy(island);

        assertEquals(TowerColor.WHITE, island.getTowers().get(0).getColor());
        assertEquals(1, island.getTowers().size());

    }

    @Test
    void applyEffectSommelier() {
        CharacterCard characterCard = new CharacterCard(CharacterName.Sommelier);
        Student student = characterCard.getStudents().get(0);
        Player player = game.getPlayerByNickname("AlanTuring");
        Island island = board.getIslands().get(0);
        int numStudents = island.getStudents().size();
        player.setNumCoins(10);

        board.applyEffectSommelier(player, characterCard, student, island);

        assertEquals(numStudents+1, island.getStudents().size());
        assertEquals(student, board.getIslands().get(0).getStudents().get(board.getIslands().get(0).getStudents().size()-1));
        assertEquals(4, characterCard.getStudents().size());
        assertEquals(2, characterCard.getPrice());
    }

    @Test
    void applyEffectMessenger() {
        CharacterCard characterCard = new CharacterCard(CharacterName.Messenger);
        Island island = board.getIslands().get(0);
        Player player = game.getPlayerByNickname("AlanTuring");

        player.setNumCoins(10);

        for(int i=0; i<5; i++)
            island.addStudent(new Student(StudentColor.getStudentColor(i)));

        //setto manualmente quali giocatori controllani quali professori
        board.setProfessorsControlledBy(new String[]{"AlanTuring", "AlanTuring", "AlanTuring", "JamesGosling", "GeorgeBoole"});

        board.applyEffectMessenger(player, characterCard, 1);

        assertEquals(TowerColor.BLACK, island.getFirstTower().getColor());
    }


}