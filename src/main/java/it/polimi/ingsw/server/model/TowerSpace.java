package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TowerSpace implements Serializable {

    private static final long serialVersionUID = 2280687268501499016L;
    private Game game;
    private List<Tower> towers;

    public TowerSpace(){
        towers = new ArrayList<Tower>();
        game = Game.getInstance();
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
