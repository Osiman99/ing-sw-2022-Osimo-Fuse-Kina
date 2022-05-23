package it.polimi.ingsw.server;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.network.messages.AssistantCardResult;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.PlayerNumberReply;
import it.polimi.ingsw.server.model.AssistantCard;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;

import java.io.Serializable;
import java.util.Map;

public class CheckController implements Serializable {

    private static final long serialVersionUID = 7413156215358698632L;

    private final Game game;
    private transient Map<String, VirtualView> virtualViewMap;
    private final GameController gameController;

    /**
     * Constructor of the Input Controller Class.
     *
     * @param virtualViewMap Virtual View Map.
     * @param gameController Game Controller.
     */
    public CheckController(Map<String, VirtualView> virtualViewMap, GameController gameController) {
        game = Game.getInstance();
        this.virtualViewMap = virtualViewMap;
        this.gameController = gameController;
    }


    public boolean checkLoginNickname(String nickname, View view) {
        if (nickname.isEmpty() || nickname.equalsIgnoreCase(Game.SERVER_NICKNAME)) {
            view.showGenericMessage("Forbidden name.");
            view.showLoginResult(false, true, null);
            return false;
        } else if (game.isNicknameTaken(nickname)) {
            view.showGenericMessage("Nickname already taken.");
            view.showLoginResult(false, true, null);
            return false;
        }
        return true;
    }

    public boolean verifyReceivedData(Message message) {

        switch (message.getMessageType()) {
            case LOGIN_REPLY: // server doesn't receive a LOGIN_REPLY.
                return false;
            case PLAYERNUMBER_REPLY:
                return playerNumberReplyCheck(message);
            case PLAYERNUMBER_REQUEST: // server doesn't receive a GenericErrorMessage.
                return false;
            case ASSISTANTCARD_RESULT:
                return assistantCardResultCheck(message);

            default: // Never should reach this statement.
                return false;
        }
    }

    private boolean playerNumberReplyCheck(Message message) {
        PlayerNumberReply playerNumberReply = (PlayerNumberReply) message;

        if (playerNumberReply.getPlayerNumber() < 4 && playerNumberReply.getPlayerNumber() > 1) {
            return true;
        } else {
            VirtualView virtualView = virtualViewMap.get(message.getNickname());
            virtualView.askPlayersNumber();
            return false;
        }
    }

    private boolean assistantCardResultCheck(Message message) {
        AssistantCardResult assistantCardResult = (AssistantCardResult) message;
        try {
            for (Player p : game.getPlayers()) {
                if (p.getNickname().equals(assistantCardResult.getNickname())) {
                    for (AssistantCard assistantCard : p.getDeck().getDeck()) {
                        if (assistantCardResult.getCard() == assistantCard.getValue()) {
                            return true;
                        } else {
                            VirtualView virtualView = virtualViewMap.get(message.getNickname());
                            virtualView.askAssistantCard(p.getDeck().getDeck());
                            return false;
                        }
                    }
                } else {
                    VirtualView virtualView = virtualViewMap.get(message.getNickname());
                    virtualView.showGenericMessage("ERROR");
                    return false;
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }
}
