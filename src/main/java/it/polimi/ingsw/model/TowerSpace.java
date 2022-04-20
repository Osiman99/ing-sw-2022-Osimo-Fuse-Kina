package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class TowerSpace {

    private Game game;
    private List<Tower> towers;

    public TowerSpace(){
        towers = new ArrayList<Tower>();
        game = Game.getInstance();
        if (game.getNumPlayers() == 2) {
            if (game.getContPlayer() == 1) {
                for (int j = 0; j < 8; j++){
                    towers.add(new Tower(TowerColor.BLACK));
                }
            }else if (game.getContPlayer() == 2){
                for (int j = 0; j < 8; j++){
                    towers.add(new Tower(TowerColor.WHITE));
                }
            }
        }else if (game.getNumPlayers() == 3){
            if (game.getContPlayer() == 1) {
                for (int j = 0; j < 6; j++){
                    towers.add(new Tower(TowerColor.BLACK));
                }
            }else if (game.getContPlayer() == 2){
                for (int j = 0; j < 6; j++){
                    towers.add(new Tower(TowerColor.WHITE));
                }
            }else if (game.getContPlayer() == 3){
                for (int j = 0; j < 6; j++){
                    towers.add(new Tower(TowerColor.GREY));
                }
            }
        }
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

    public boolean isEmpty(){
        if (towers == null){
            return true;
        }else {
            return false;
        }
    }


}
