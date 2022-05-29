package it.polimi.ingsw.server;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.server.model.*;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckController implements Serializable {

    private static final long serialVersionUID = 7413156215358698632L;

    private final Game game;
    private transient Map<String, VirtualView> virtualViewMap;
    private final GameController gameController;
    private List<Integer> numCardOtherPlayers;
    private List<String> nicknamesInChooseOrder;
    private List<String> sortedNicknames;
    private String firstPlayerInAction;

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
        numCardOtherPlayers = new ArrayList<Integer>();
        nicknamesInChooseOrder = new ArrayList<String>();
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
            case MOVE_STUDENT:
                return moveStudentCheck(message);
            case MOTHERNATURE_RESULT:
                return motherNatureCheck(message);
            case CLOUD:
                return cloudCheck(message);

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
            virtualView.onDemandPlayersNumber();
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
                            if (numCardOtherPlayers.size() == 0){
                                numCardOtherPlayers.add(assistantCardResult.getCard());
                                nicknamesInChooseOrder.add(assistantCardResult.getNickname());
                                return true;
                            }else{
                                for (int i = 0; i < game.getNumPlayers(); i++){
                                    if (assistantCardResult.getCard() == numCardOtherPlayers.get(i)){
                                        VirtualView virtualView = virtualViewMap.get(message.getNickname());
                                        virtualView.showGenericMessage("Card already chosen by another player! Please try again.");
                                        virtualView.onDemandAssistantCard(p.getDeck().getDeck());
                                        return false;
                                    }else if (numCardOtherPlayers.size() - 1 == i) {
                                        numCardOtherPlayers.add(assistantCardResult.getCard());
                                        nicknamesInChooseOrder.add(assistantCardResult.getNickname());
                                        return true;
                                    }
                                }
                            }return true;
                        }
                    }
                    VirtualView virtualView = virtualViewMap.get(message.getNickname());
                    virtualView.showGenericMessage("There's no cards in your deck with this value! Please try again.");
                    virtualView.onDemandAssistantCard(p.getDeck().getDeck());
                    return false;
                }
            }
            VirtualView virtualView = virtualViewMap.get(message.getNickname());
            virtualView.showGenericMessage("ERROR");
            return false;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }

    private boolean moveStudentCheck(Message message) {
        MoveMessage moveMessage = (MoveMessage) message;
        VirtualView virtualView = virtualViewMap.get(message.getNickname());
        for (Student student : game.getPlayerByNickname(moveMessage.getNickname()).getPlank().getEntrance().getStudents()) {
            if (student.getColor() == moveMessage.getStudentColor()) {
                if (moveMessage.getNumIsland() == 0) {
                    if (game.getPlayerByNickname(moveMessage.getNickname()).getPlank().getDiningRoom()[moveMessage.getStudentColor().getCode()].getStudents().size() == 10) {                        //prima c'era if else di controllo 0 e 13
                        virtualView.showGenericMessage("The Dining Room is full! Please try again.");
                        virtualView.showGenericMessage("Do you want to move a student to your plank or island? [p/i]");
                        return false;
                    }
                } else {
                    if (moveMessage.getNumIsland() <= game.getBoard().getIslands().size()) {
                        return true;
                    } else {
                        virtualView.showGenericMessage("The island number " + moveMessage.getNumIsland() + " doesn't exist! Please try again.");
                        virtualView.showGenericMessage("Do you want to move a student to your plank or island? [p/i]");
                        return false;
                    }
                }return true;
            }
        }
        if (moveMessage.getNumIsland() <= game.getBoard().getIslands().size()) {
            virtualView.showGenericMessage("You don't have any " + moveMessage.getStudentColor() + " student in your entrance! Please try again.");
        }else{
            virtualView.showGenericMessage("You don't have any " + moveMessage.getStudentColor() + " student in your entrance and the island number " + moveMessage.getNumIsland() + " doesn't exist! Please try again.");
        }
        virtualView.showGenericMessage("Do you want to move a student to your plank or island? [p/i]");
        return false;
    }

    private boolean cloudCheck(Message message){
        CloudMessage cloudMessage = (CloudMessage) message;
        VirtualView virtualView = virtualViewMap.get(message.getNickname());
        for (int i = 0; i < game.getBoard().getClouds().size(); i++) {
            if(cloudMessage.getNumCloud() == i+1){
                if (!game.getBoard().getClouds().get(i).isEmpty()) {
                    return true;
                }else{
                    virtualView.showGenericMessage("The cloud number " + cloudMessage.getNumCloud() + " is empty! Please try again.");
                    virtualView.showGenericMessage("Which cloud do you choose? Insert the cloud number.");
                    return false;
                }
            }
        }
        virtualView.showGenericMessage("The cloud number " + cloudMessage.getNumCloud() + " doesn't exist! Please try again.");
        virtualView.showGenericMessage("Which cloud do you choose? Insert the cloud number.");
        return false;
    }

    private boolean motherNatureCheck(Message message){
        MotherNatureResult motherNatureMessage = (MotherNatureResult) message;
        VirtualView virtualView = virtualViewMap.get(message.getNickname());
        if (motherNatureMessage.getNumMoves() > 0 && motherNatureMessage.getNumMoves() <= game.getPlayerByNickname(motherNatureMessage.getNickname()).getChosenAssistantCard().getMaxMoves()){
            return true;
        }else{
            virtualView.showGenericMessage("Invalid input! Please try again.");
            virtualView.onDemandMotherNatureMoves(game.getPlayerByNickname(motherNatureMessage.getNickname()).getChosenAssistantCard().getMaxMoves());
            return false;
        }
    }


    public void initializeFirstPlayerInAction(){
        sortNicknames();
        setFirstPlayerInAction(nicknamesInChooseOrder.get(0));
    }

    public void sortNicknames(){                 //ordina i nickname dal più basso valore dell'asstant card scelta al più alto
        for (int i = 0; i < numCardOtherPlayers.size()-1; i++){
            int lowerNumIndex = i;
            for (int j = i + 1; j < numCardOtherPlayers.size(); j++){
                if(numCardOtherPlayers.get(j) < numCardOtherPlayers.get(lowerNumIndex)){
                    lowerNumIndex = j;
                }
            }

            int tmp = numCardOtherPlayers.get(i);
            String sTmp = nicknamesInChooseOrder.get(i);
            numCardOtherPlayers.set(i, numCardOtherPlayers.get(lowerNumIndex));
            nicknamesInChooseOrder.set(i, nicknamesInChooseOrder.get(lowerNumIndex));
            numCardOtherPlayers.set(lowerNumIndex, tmp);
            nicknamesInChooseOrder.set(lowerNumIndex, sTmp);

        }


        /*if (game.getPlayers().size() == 2) {
            int lowerNum = numCardOtherPlayers.get(0);
            sortedNicknames.add(0, nicknamesInChooseOrder.get(0));
            for (int i = 1; i < numCardOtherPlayers.size(); i++) {
                if (lowerNum > numCardOtherPlayers.get(i)) {
                    lowerNum = numCardOtherPlayers.get(i);
                    sortedNicknames.add(0, nicknamesInChooseOrder.get(i));
                } else {
                    sortedNicknames.add(nicknamesInChooseOrder.get(i));
                }
            }
        }else{

        }*/
    }

    public List<Integer> getNumCardOtherPlayers() {
        return numCardOtherPlayers;
    }

    public String getFirstPlayerInAction() {
        return firstPlayerInAction;
    }

    public void setFirstPlayerInAction(String firstPlayerInAction) {
        this.firstPlayerInAction = firstPlayerInAction;
    }

    public List<String> getNicknamesInChooseOrder() {
        return nicknamesInChooseOrder;
    }
}