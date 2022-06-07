package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {
    private Island island;
    private Student student;
    private Tower tower;

    @BeforeEach
    void setUp() {
        island = new Island();
    }

    @Test
    void addStudent() {
        student = new Student(StudentColor.YELLOW);
        island.addStudent(student);
        assertEquals(student, island.getStudents().get(island.getStudents().size()-1));
    }

    @Test
    void addTower() {
        tower = new Tower(TowerColor.BLACK);
        island.addTower(tower);
        assertEquals(tower, island.getTowers().get(island.getTowers().size()-1));
    }

    @Test
    void setMotherNature() {
        island.setMotherNature(true);
        assertTrue(island.isMotherNature());
    }

    @Test
    void setBanCard() {
        island.setBanCard(true);
        assertTrue(island.isBanCard());
    }

    @Test
    void isBanCard() {
        assertFalse(island.isBanCard());
    }

    @Test
    void isMotherNature() {
        assertFalse(island.isMotherNature());
    }

    @Test
    void getFirstTower() {
        tower = new Tower(TowerColor.WHITE);
        island.addTower(tower);
        assertEquals(tower, island.getFirstTower());
    }

    @Test
    void getFirstStudent() {
        student = new Student(StudentColor.YELLOW);
        island.addStudent(student);
        student = island.getStudents().get(0);
        assertEquals(student, island.getFirstStudent());
    }

    @Test
    void getTowers() {
        island.addTower(new Tower(TowerColor.BLACK));
        island.addTower(new Tower(TowerColor.BLACK));
        assertEquals(2, island.getTowers().size());
    }

    @Test
    void getStudents() {
        island.addStudent(new Student(StudentColor.PINK));
        assertEquals(1, island.getStudents().size());
    }

    @Test
    void removeTower() {
        tower = new Tower(TowerColor.BLACK);
        island.addTower(tower);
        island.removeTower();
        assertEquals(0, island.getTowers().size());
    }

    @Test
    void removeStudent() {
        student = new Student(StudentColor.BLUE);
        island.addStudent(student);
        island.removeStudent();
        assertEquals(0, island.getStudents().size());
    }

}