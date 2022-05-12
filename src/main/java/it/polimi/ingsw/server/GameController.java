package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.model.Game;

import java.io.Serializable;

public class GameController /*implements Observer, Serializable*/ {

    private GameState state;
    private Game game;

    public GameController(){
        game = Game.getInstance();
        state = GameState.PREGAME;

    }

    public void switchState(){
        switch(state){
            case PREGAME:

                break;

        }
    }
}
