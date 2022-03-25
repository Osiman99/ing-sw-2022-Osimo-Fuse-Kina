package it.polimi.ingsw;

public enum TowerColor {
    WHITE(0), BLACK(1), GREY(2);

    int towerColorCode;

    TowerColor(int towerColorCode) {
        this.towerColorCode = towerColorCode;
    }

    public int getTowerColorCode(){
        return this.towerColorCode;
    }
    public int getTowerColor(int towerColorCode){
        return TowerColor.this.towerColorCode;
    }
}
