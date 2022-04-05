package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class TowerSpace {

    private List<Tower> towers;

    public TowerSpace(){
        towers = new ArrayList<Tower>();
    }

    public List<Tower> getTowersList() {
        return towers;
    }

    public Tower getFirstTower(){
        return towers.get(0);
    }

    public void addTower(Tower tower){
        towers.add(tower);
    }

    public void removeTower(){
        towers.remove(0);
    }


}
