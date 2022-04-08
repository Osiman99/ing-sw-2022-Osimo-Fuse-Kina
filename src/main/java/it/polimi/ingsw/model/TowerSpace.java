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
            for (int i = 0; i < game.getNumPlayers(); i++) {
                if (game.getPlayers().get(i).getPlayerColor() == TowerColor.BLACK) {
                    for (int j = 0; j < 8; j++){
                        towers.add(new Tower(TowerColor.BLACK));
                    }
                }else if (game.getPlayers().get(i).getPlayerColor() == TowerColor.WHITE){
                    for (int j = 0; j < 8; j++){
                        towers.add(new Tower(TowerColor.BLACK));
                    }
                }
            }
        }else if (game.getNumPlayers() == 3){
            for (int i = 0; i < game.getNumPlayers(); i++) {
                if (game.getPlayers().get(i).getPlayerColor() == TowerColor.BLACK) {
                    for (int j = 0; j < 6; j++){
                        towers.add(new Tower(TowerColor.BLACK));
                    }
                }else if (game.getPlayers().get(i).getPlayerColor() == TowerColor.WHITE){
                    for (int j = 0; j < 6; j++){
                        towers.add(new Tower(TowerColor.WHITE));
                    }
                }else if (game.getPlayers().get(i).getPlayerColor() == TowerColor.GREY){
                    for (int j = 0; j < 6; j++){
                        towers.add(new Tower(TowerColor.GREY));
                    }
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


}
