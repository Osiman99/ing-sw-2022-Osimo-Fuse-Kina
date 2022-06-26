package it.polimi.ingsw.server;

import it.polimi.ingsw.client.view.CLI.ANSIColor;
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

    private Game game;
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
        this.virtualViewMap = virtualViewMap;
        this.gameController = gameController;
        numCardOtherPlayers = new ArrayList<Integer>();
        nicknamesInChooseOrder = new ArrayList<String>();
    }


    public boolean checkLoginNickname(String nickname, View view) {
        if (nickname.isEmpty() || nickname.equalsIgnoreCase(Game.SERVER_NICKNAME)) {
            view.showGenericMessage(ANSIColor.RED+"Forbidden name."+ANSIColor.RESET);
            view.showLoginResult(false, true, null);
            return false;
        } else if (gameController.getLobby().isNicknameTaken(nickname.toUpperCase())) {
            view.showGenericMessage(ANSIColor.RED+ "Nickname already taken."+ANSIColor.RESET);
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
            case MODE_MESSAGE:
                return modeCheck(message);
            case CHARACTERCARDS_REQUEST:
                return characterCardCheck(message);

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
            game = Game.getInstance();
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
                                        game.notifyBoard();
                                        virtualView.showGenericMessage(ANSIColor.RED+ "Card already chosen by another player! Please try again."+ANSIColor.RESET);
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
                    game.notifyBoard();
                    virtualView.showGenericMessage(ANSIColor.RED+ "There's no cards in your deck with this value! Please try again."+ANSIColor.RESET);
                    virtualView.onDemandAssistantCard(p.getDeck().getDeck());
                    return false;
                }
            }game.notifyBoard();
            VirtualView virtualView = virtualViewMap.get(message.getNickname());
            virtualView.showGenericMessage(ANSIColor.RED+ "ERROR"+ ANSIColor.RESET);
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
                        game.notifyBoard();
                        virtualView.showGenericMessage(ANSIColor.RED+ "The Dining Room is full! Please try again."+ANSIColor.RESET);
                        virtualView.showGenericMessage("Do you want to move a student to your plank or island? [p/i]");
                        return false;
                    }
                } else {
                    if (moveMessage.getNumIsland() <= game.getBoard().getIslands().size()) {
                        return true;
                    } else {
                        game.notifyBoard();
                        virtualView.showGenericMessage(ANSIColor.RED+ "The island number " + moveMessage.getNumIsland() + " doesn't exist! Please try again."+ANSIColor.RESET);
                        virtualView.showGenericMessage("Do you want to move a student to your plank or island? [p/i]");
                        return false;
                    }
                }return true;
            }
        }
        if (moveMessage.getNumIsland() <= game.getBoard().getIslands().size()) {
            game.notifyBoard();
            virtualView.showGenericMessage(ANSIColor.RED+ "You don't have any " + moveMessage.getStudentColor() + " student in your entrance! Please try again."+ANSIColor.RESET);
        }else{
            game.notifyBoard();
            virtualView.showGenericMessage(ANSIColor.RED+ "You don't have any " + moveMessage.getStudentColor() + " student in your entrance and the island number " + moveMessage.getNumIsland() + " doesn't exist! Please try again."+ANSIColor.RESET);
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
                    game.notifyBoard();
                    virtualView.showGenericMessage(ANSIColor.RED+ "The cloud number " + cloudMessage.getNumCloud() + " is empty! Please try again."+ANSIColor.RESET);
                    virtualView.showGenericMessage("Which cloud do you choose? Insert the cloud number.");
                    return false;
                }
            }
        }game.notifyBoard();
        virtualView.showGenericMessage(ANSIColor.RED+ "The cloud number " + cloudMessage.getNumCloud() + " doesn't exist! Please try again."+ANSIColor.RESET);
        virtualView.showGenericMessage("Which cloud do you choose? Insert the cloud number.");
        return false;
    }

    private boolean motherNatureCheck(Message message){
        MotherNatureResult motherNatureMessage = (MotherNatureResult) message;
        VirtualView virtualView = virtualViewMap.get(message.getNickname());
        if(game instanceof GameExpert) {
            GameExpert gameExpert = (GameExpert) game;
            for (CharacterCard characterCard : gameExpert.getThreeChosenCards()) {
                if (characterCard.getCharacterName() == CharacterName.Postman && characterCard.isEnabled()) {
                    if (motherNatureMessage.getNumMoves() > 0 && motherNatureMessage.getNumMoves() <= game.getPlayerByNickname(motherNatureMessage.getNickname()).getChosenAssistantCard().getMaxMoves() + 2) {
                        return true;
                    } else {
                        game.notifyBoard();
                        virtualView.showGenericMessage(ANSIColor.RED+"Invalid input! Please try again."+ANSIColor.RESET);
                        virtualView.onDemandMotherNatureMoves(game.getPlayerByNickname(motherNatureMessage.getNickname()).getChosenAssistantCard().getMaxMoves() + 2);
                        return false;
                    }
                }
            }
        }
        if (motherNatureMessage.getNumMoves() > 0 && motherNatureMessage.getNumMoves() <= game.getPlayerByNickname(motherNatureMessage.getNickname()).getChosenAssistantCard().getMaxMoves()){
            return true;
        }else {
            game.notifyBoard();
            virtualView.showGenericMessage(ANSIColor.RED+"Invalid input! Please try again."+ANSIColor.RESET);
            virtualView.onDemandMotherNatureMoves(game.getPlayerByNickname(motherNatureMessage.getNickname()).getChosenAssistantCard().getMaxMoves());
            return false;
        }
    }

    public boolean modeCheck(Message message){
        ModeMessage modeMessage = (ModeMessage) message;
        VirtualView virtualView = virtualViewMap.get(message.getNickname());
        if (modeMessage.getMode().equals("n") || modeMessage.getMode().equals("e")){
            return true;
        }virtualView.showGenericMessage(ANSIColor.RED+"Invalid input! Please try again."+ANSIColor.RESET);
        virtualView.showGenericMessage("Do you want to play in " + ANSIColor.UNDERLINE +"Normal" +ANSIColor.RESET+  " or " +ANSIColor.UNDERLINE+  "Expert" +ANSIColor.RESET+ " mode? [n/e]");
        return false;
    }

    public boolean characterCardCheck(Message message){
        GameExpert gameExpert = (GameExpert) game;
        VirtualView virtualView = virtualViewMap.get(message.getNickname());
        Player activePlayer = game.getPlayerByNickname(message.getNickname());
        CharacterCardMessage characterCardMessage = (CharacterCardMessage) message;
        if(game.getBoard().isCardActivated()){
            game.notifyBoard();
            virtualView.showGenericMessage(ANSIColor.RED+ "You already activated a card in this turn!"+ANSIColor.RESET);
            return askInterrupted(virtualView, activePlayer);
        }
        switch (characterCardMessage.getCard()) {
            case "sommelier":
                for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                    if (cc.getCharacterName() == CharacterName.Sommelier) {
                        if (eachCharCardCheck(virtualView, activePlayer, cc)) {
                            for (int i = 0; i < cc.getStudents().size(); i++) {
                                if (characterCardMessage.getStudentColor() == cc.getStudents().get(i).getColor()) {
                                    System.out.println(characterCardMessage.getNumIsland());
                                    if (characterCardMessage.getNumIsland() <= game.getBoard().getIslands().size() && characterCardMessage.getNumIsland() > 0) {
                                        return true;
                                    } else {
                                        game.notifyBoard();
                                        virtualView.showGenericMessage(ANSIColor.RED+ "The island number " + characterCardMessage.getNumIsland() + " doesn't exist! Please try again."+ANSIColor.RESET);
                                        virtualView.onDemandCharacterCard(gameController.getText());
                                        return false;
                                    }
                                }
                            }game.notifyBoard();
                            virtualView.showGenericMessage(ANSIColor.RED+ "There's no " + characterCardMessage.getStudentColor() +" student on this card! Please try again."+ANSIColor.RESET);
                            virtualView.onDemandCharacterCard(gameController.getText());
                        }
                        return false;
                    }
                }
                break;
            case "chef":
                for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                    if (cc.getCharacterName() == CharacterName.Chef) {
                        return eachCharCardCheck(virtualView, activePlayer, cc);
                    }
                }
                break;
            case "messenger":
                for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                    if (cc.getCharacterName() == CharacterName.Messenger) {
                        return numIslandCheck(virtualView, activePlayer, characterCardMessage, cc);
                    }
                }
                break;
            case "postman":
                for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                    if (cc.getCharacterName() == CharacterName.Postman) {
                        return eachCharCardCheck(virtualView, activePlayer, cc);
                    }
                }
                break;
            case "herbalist":
                for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                    if (cc.getCharacterName() == CharacterName.Herbalist) {
                        if (numIslandCheck(virtualView, activePlayer, characterCardMessage, cc)){
                            for(int i = 3; i >=0; i--){
                                if (cc.getBanCards()[i]){
                                    return true;
                                }
                            }game.notifyBoard();
                            virtualView.showGenericMessage("There are no more BanCards on this card! Please try again.");
                            virtualView.onDemandCharacterCard(gameController.getText());
                            return false;
                        }return false;
                    }
                }
                break;
            case "centaur":
                for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                    if (cc.getCharacterName() == CharacterName.Centaur) {
                        return eachCharCardCheck(virtualView, activePlayer, cc);
                    }
                }
                break;
            /*case "joker":
                for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                    if (cc.getCharacterName() == CharacterName.Joker) {
                        return eachCharCardCheck(virtualView, activePlayer, cc);
                    }
                }
                break;
            */
            case "knight":
                for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                    if (cc.getCharacterName() == CharacterName.Knight) {
                        return eachCharCardCheck(virtualView, activePlayer, cc);
                    }
                }
                break;
            /*case "merchant":
                for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                    if (cc.getCharacterName() == CharacterName.Merchant) {
                        return eachCharCardCheck(virtualView, activePlayer, cc);
                    }
                }
                break;
            case "musician":
                for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                    if (cc.getCharacterName() == CharacterName.Musician) {
                        return eachCharCardCheck(virtualView, activePlayer, cc);
                    }
                }
                break;
            */
            case "lady":
                for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                    if (cc.getCharacterName() == CharacterName.Lady) {
                        if (eachCharCardCheck(virtualView, activePlayer, cc)) {
                            for (int i = 0; i < cc.getStudents().size(); i++) {
                                if (characterCardMessage.getStudentColor() == cc.getStudents().get(i).getColor()) {
                                    return true;
                                }
                            }game.notifyBoard();
                            virtualView.showGenericMessage(ANSIColor.RED+ "There's no " + characterCardMessage.getStudentColor() +" student on this card! Please try again."+ANSIColor.RESET);
                            virtualView.onDemandCharacterCard(gameController.getText());
                        }
                        return false;
                    }
                }
                break;
            /*case "sinister":
                for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                    if (cc.getCharacterName() == CharacterName.Sinister) {
                        return eachCharCardCheck(virtualView, activePlayer, cc);
                    }
                }
                break;
            */
            case "back":
                game.notifyBoard();
                askInterrupted(virtualView, activePlayer);
                return false;
        }game.notifyBoard();
        virtualView.showGenericMessage(ANSIColor.RED+"Invalid input! Please try again."+ANSIColor.RESET);
        virtualView.onDemandCharacterCard(gameController.getText());
        return false;
    }

    private boolean askInterrupted(VirtualView virtualView, Player activePlayer) {
        if (gameController.getAskInterrupted().equals("s")){
            virtualView.showGenericMessage("Do you want to move a student to your plank or island? [p/i]");
        }else if(gameController.getAskInterrupted().equals("m")){
            virtualView.onDemandMotherNatureMoves(activePlayer.getChosenAssistantCard().getMaxMoves());
        }else if(gameController.getAskInterrupted().equals("c")){
            virtualView.showGenericMessage("Which cloud do you choose? Insert the cloud number.");
        }
        return false;
    }

    private boolean numIslandCheck(VirtualView virtualView, Player activePlayer, CharacterCardMessage characterCardMessage, CharacterCard cc) {
        if (eachCharCardCheck(virtualView, activePlayer, cc)){
            if (characterCardMessage.getNumIsland() > 0 && characterCardMessage.getNumIsland() <= game.getBoard().getIslands().size()){
                return true;
            }else{
                game.notifyBoard();
                virtualView.showGenericMessage(ANSIColor.RED+ "The island number " + characterCardMessage.getNumIsland() + " doesn't exist! Please try again."+ANSIColor.RESET);
                virtualView.onDemandCharacterCard(gameController.getText());
                return false;
            }
        }else{
            return false;
        }
    }

    private boolean eachCharCardCheck(VirtualView virtualView, Player activePlayer, CharacterCard cc) {
        if (cc.getPrice() <= activePlayer.getNumCoins()) {
            return true;
        } else {
            game.notifyBoard();
            virtualView.showGenericMessage(ANSIColor.RED+ "You have not enough coins for this card!"+ANSIColor.RESET);
            return askInterrupted(virtualView, activePlayer);
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