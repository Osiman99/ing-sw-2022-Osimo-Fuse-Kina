package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Island implements Serializable {

    private static final long serialVersionUID = -2272232649575815385L;
    private List<Student> students;
    private List<Tower> towers;
    private boolean motherNature;
    private boolean banCard;

    public Island(){
        students = new ArrayList<Student>();
        towers = new ArrayList<Tower>();
        motherNature = false;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public void addTower(Tower tower){
        towers.add(tower);
    }

    public void setMotherNature(boolean motherNature) {
        this.motherNature = motherNature;
    }

    public void setBanCard(boolean banCard) {
        this.banCard = banCard;
    }

    public boolean isBanCard() {
        return banCard;
    }

    public boolean isMotherNature() {
        return motherNature;
    }

    public Tower getFirstTower(){
        return towers.get(0);
    }

    public Student getFirstStudent(){
        return students.get(0);
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void removeTower(){
        towers.remove(0);
    }

    public void removeStudent(){
        students.remove(0);
    }


    // possiamo creare una List<Island> che ad ogni isola possiamo associare un valore boolean per indicare se c'è o no
    // motherNature, un int(oppure 5 siccome ci sono 5 professori) che ci da il numero degli studenti di ogni colore e
    // un int per indicare se c'è o no un Tower.
    // Facendo così possiamo sommare il Tower con gli studenti per poi calcolare la supremazia.
}
