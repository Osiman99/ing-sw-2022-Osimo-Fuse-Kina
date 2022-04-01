package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Island {

    private List<Student> students;
    private List<Tower> towers;

    public Island(){
        students = new ArrayList<Student>();
        towers = new ArrayList<Tower>();
    }





    // possiamo creare una List<Island> che ad ogni isola possiamo associare un valore boolean per indicare se c'è o no
    // motherNature, un int(oppure 5 siccome ci sono 5 professori) che ci da il numero degli studenti di ogni colore e
    // un int per indicare se c'è o no un Tower.
    // Facendo così possiamo sommare il Tower con gli studenti per poi calcolare la supremazia.
}
