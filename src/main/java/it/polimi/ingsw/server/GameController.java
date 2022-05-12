package it.polimi.ingsw.server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.PlayerNumberReply;
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

    public void switchState(Message receivedMessage){
        switch(state){
            case PREGAME:
                login(receivedMessage);
                break;

        }
    }

    private void login(Message receivedMessage){
        /*if (receivedMessage.getMessageType() == MessageType.PLAYERNUMBER_REPLY) {
            if (inputController.verifyReceivedData(receivedMessage)) {
                game.setChosenPlayersNumber(((PlayerNumberReply) receivedMessage).getPlayerNumber());
                broadcastGenericMessage("Waiting for other Players . . .");
            }
        } else {
            Server.LOGGER.warning("Wrong message received from client.");
        }*/
    }



}
