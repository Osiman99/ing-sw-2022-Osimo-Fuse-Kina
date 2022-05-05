package it.polimi.ingsw.server;

public class GameController {
    private GameState state;

    public GameController(){
        state = GameState.PREGAME;
    }

    public void switchState(){
        switch(state){
            case PREGAME:
                //codice del PREGAME
                break;

        }
    }
}
