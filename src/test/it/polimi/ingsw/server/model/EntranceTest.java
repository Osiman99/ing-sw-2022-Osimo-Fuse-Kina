package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntranceTest {
    private Entrance entrance;
    private Student student = new Student(StudentColor.GREEN);;
    private Game game;
    private Player player;
    private String [] nicknames = {"AlanTuring", "JamesGosling", "GeorgeBoole"};

    @BeforeEach
    void setUp() {
        game = Game.getInstance();
        for(int i=0; i<3; i++)
            game.getPlayers().add(new Player(nicknames[i]));
        game.setChosenPlayersNumber(3);
        game.initGame();
        player = Game.getInstance().getPlayers().get(0);
        entrance = player.getPlank().getEntrance();
    }

    @AfterEach
    void tearDown() {
        Game.resetInstance();
    }

    @Test
    void getStudents() {
        assertNotNull(entrance.getStudents());
    }

    @Test
    void addStudent() {

        entrance.addStudent(student);
        assertEquals(student, entrance.getStudents().get(entrance.getStudents().size()-1));
    }

    @Test
    void removeStudent() {
        int x=0, y=0;

        entrance.addStudent(student);

        for(Student s : entrance.getStudents())
            if(s.getColor() == StudentColor.GREEN)
                x++; //counts how many green students are inside the entrance before removeStudent

        entrance.removeStudent(student);

        for(Student s : entrance.getStudents())
            if(s.getColor() == StudentColor.GREEN)
                y++; //counts how many green students are inside the entrance after removeStudent

        assertEquals(x-1, y);

    }

}