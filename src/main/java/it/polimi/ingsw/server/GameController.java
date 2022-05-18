package it.polimi.ingsw.server;

import it.polimi.ingsw.client.view.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.PlayerNumberReply;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.model.Game;

import java.io.Serializable;
import java.util.Map;

public class GameController implements Observer, Serializable {

    private GameState state;
    private Game game;
    private static final long serialVersionUID = 4951303731052728724L;

    private transient Map<String, VirtualView> virtualViewMap;

    //private TurnController turnController;
    //private InputController inputController;

    private static final String STR_INVALID_STATE = "Invalid game state!";
    public static final String SAVED_GAME_FILE = "match.bless";


    public GameController(){
        game = Game.getInstance();
        state = GameState.PREGAME;

    }

    public void switchState(Message receivedMessage){
        VirtualView virtualView = virtualViewMap.get(receivedMessage.getNickname());
        switch(state){
            case PREGAME:
                login(receivedMessage);
                break;
            case INGAME:



            case ENDGAME:
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


    @Override
    public void update(Message message) {

    }
}
