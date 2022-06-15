package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.model.*;

import java.io.Serializable;
import java.util.*;

public class GameController implements Observer, Serializable {

    private GameState state;
    private Game game;
    private static final long serialVersionUID = 4951303731052728724L;
    private transient Map<String, VirtualView> virtualViewMap;
    private Player activePlayer;
    private List<String> nicknames; //forse final
    private CheckController checkController;
    private static final String STR_INVALID_STATE = "Invalid game state!";
    public static final String SAVED_GAME_FILE = "match.bless";
    private int turnCont;
    private int moveCont;
    private boolean cloudFlag;
    private boolean motherNatureFlag;
    private Server server;


    public GameController(){
        this.game = Game.getInstance();
        server = Server.getInstance();
        virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        checkController = new CheckController(virtualViewMap, this);
        setGameState(GameState.PREGAME);
        turnCont = 0;
        moveCont = 0;
        cloudFlag = false;
        motherNatureFlag = true;
    }

    public void switchState(Message receivedMessage){
        VirtualView virtualView = virtualViewMap.get(receivedMessage.getNickname());
        switch(state){
            case PREGAME:
                login(receivedMessage);
                break;
            case PLAN:
                plan(receivedMessage);
                break;
            case ACTION:
                action(receivedMessage);
                break;
            case ENDGAME:
                end(receivedMessage);
                break;

        }
    }

    public void loginHandler(String nickname, VirtualView virtualView) {
        game.getBoard().setGameInstance(game);
        if (virtualViewMap.isEmpty()) { // First player logged. Ask number of players.
            addVirtualView(nickname, virtualView);
            game.addPlayer(new Player(nickname));
            game.getPlayers().get(0).setPlayerColor(TowerColor.BLACK);

            virtualView.showLoginResult(true, true, Game.SERVER_NICKNAME);
            virtualView.onDemandPlayersNumber();

        } else if (virtualViewMap.size() < game.getNumPlayers()) {
            if (virtualViewMap.size() == 1) {
                addVirtualView(nickname, virtualView);
                game.addPlayer(new Player(nickname));
                game.getPlayers().get(1).setPlayerColor(TowerColor.WHITE);
                virtualView.showLoginResult(true, true, Game.SERVER_NICKNAME);
            }else if (virtualViewMap.size() == 2){
                addVirtualView(nickname, virtualView);
                game.addPlayer(new Player(nickname));
                game.getPlayers().get(2).setPlayerColor(TowerColor.GREY);
                virtualView.showLoginResult(true, true, Game.SERVER_NICKNAME);
            }


            if (game.getContPlayer() == game.getNumPlayers()) { // If all players logged
                //PERSISTENZA FA

                /*StorageData storageData = new StorageData();
                GameController savedGameController = storageData.restore();
                if (savedGameController != null &&
                        game.getPlayersNicknames().containsAll(savedGameController.getTurnController().getNicknameQueue())) {
                    restoreControllers(savedGameController);
                    broadcastRestoreMessages();
                    Server.LOGGER.info("Saved Match restored.");
                    turnController.newTurn();
                } else {
                    initGame();
                }*/
                initGame();
            }else{
                if (virtualViewMap.size() != 1){
                    virtualView.showGenericMessage("Waiting for other players...");
                }
            }
        } else {
            virtualView.showLoginResult(true, false, Game.SERVER_NICKNAME);
        }
    }

    private void login(Message receivedMessage){
        if (receivedMessage.getMessageType() == MessageType.PLAYERNUMBER_REPLY) {
            if (checkController.verifyReceivedData(receivedMessage)) {
                game.setChosenPlayersNumber(((PlayerNumberReply) receivedMessage).getPlayerNumber());
                broadcastGenericMessage("Waiting for other Players . . .");
            }
        } else {
            Server.LOGGER.warning("Wrong message received from client.");
        }
    }



    public void initGame(){
        nicknames = new ArrayList<>(game.getNicknames());
        for (int i = 0; i < game.getNumPlayers(); i++)
            if (game.getPlayers().get(i).getNickname().equals(nicknames.get(0))) {
                activePlayer = game.getPlayers().get(i);
            }
        game.initGame();
        setGameState(GameState.PLAN);
        game.getBoard().moveStudentsFromBagToClouds();
        broadcastBoardMessage();
        broadcastGenericMessage("All Players are connected. " + activePlayer.getNickname() + " is choosing the Assistant Card...");

        VirtualView virtualView = virtualViewMap.get(activePlayer.getNickname());
        virtualView.onDemandAssistantCard(activePlayer.getDeck().getDeck());
    }

    public void broadcastGenericMessage(String message) {
        for (VirtualView virtualView : virtualViewMap.values()) {
            virtualView.showGenericMessage(message);
        }
    }

    public void broadcastBoardMessage(){
        for (VirtualView virtualView : virtualViewMap.values()) {
            virtualView.drawBoard(game);
        }
    }

    private void setGameState(GameState state) {
        this.state = state;
    }

    public void addVirtualView(String nickname, VirtualView virtualView) {
        virtualViewMap.put(nickname, virtualView);
        game.addObserver(virtualView);
        game.getBoard().addObserver(virtualView);
    }

    public boolean isGameStarted() {
        return state != GameState.PREGAME;
    }

    public boolean checkLoginNickname(String nickname, View view) {
        return checkController.checkLoginNickname(nickname, view);
    }


    public void removeVirtualView(String nickname, boolean notifyEnabled) {
        VirtualView vv = virtualViewMap.remove(nickname);

        game.removeObserver(vv);
        game.getBoard().removeObserver(vv);
        game.removePlayerByNickname(nickname, notifyEnabled);
        nicknames.remove(nickname);
    }

    public List<String> getNicknames() {
        return nicknames;
    }


    public void broadcastDisconnectionMessage(String nicknameDisconnected, String text) {
        for (VirtualView virtualView : virtualViewMap.values()) {
            virtualView.showDisconnectionMessage(nicknameDisconnected, text);
        }
    }


    public void end(Message receivedMessage){
        if (receivedMessage.getMessageType() == MessageType.END_MESSAGE) {
            ClientHandler clientHandler = server.getClientHandlerMap().get(receivedMessage.getNickname());
            clientHandler.disconnect();
        }
    }

    public void endGame() {

        Game.resetInstance();

        //PERSISTENZA FA
        /*StorageData storageData = new StorageData();
        storageData.delete();*/

        initGameController();
        Server.LOGGER.info("Game finished. Server ready for a new Game.");
    }

    public void initGameController() {
        this.game = Game.getInstance();
        this.virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        this.checkController = new CheckController(virtualViewMap, this);
        setGameState(GameState.PREGAME);
        this.game = Game.getInstance();
        turnCont = 0;
        moveCont = 0;
        cloudFlag = false;
        motherNatureFlag = true;
    }

    public void plan(Message receivedMessage){
        if (receivedMessage.getMessageType() == MessageType.ASSISTANTCARD_RESULT){
            if (checkController.verifyReceivedData(receivedMessage)) {
                activePlayer.chooseAssistantCard(((AssistantCardResult) receivedMessage).getCard());
                broadcastGenericMessage(activePlayer.getNickname() + " chose the Card number " + ((AssistantCardResult) receivedMessage).getCard());
                planTurnManager();
            }
        }else{
            Server.LOGGER.warning("Wrong message received from client.");
        }
    }

    public void planTurnManager(){
        for (int i = 0; i < game.getPlayers().size(); i++){
            if (game.getPlayers().get(i).getNickname().equals(activePlayer.getNickname())) {
                activePlayer = game.getPlayers().get((i + 1) % game.getPlayers().size());
                turnCont++;
                if (turnCont == game.getPlayers().size()){
                    state = GameState.ACTION;
                    checkController.initializeFirstPlayerInAction();
                    activePlayer = game.getPlayerByNickname(checkController.getFirstPlayerInAction());
                    VirtualView virtualView = virtualViewMap.get(activePlayer.getNickname());
                    virtualView.showGenericMessage("Do you want to move a student to your plank or island? [p/i]");
                    turnCont = 0;
                }if(state == GameState.PLAN) {
                    VirtualView virtualView = virtualViewMap.get(activePlayer.getNickname());
                    virtualView.onDemandAssistantCard(activePlayer.getDeck().getDeck());
                }break;
            }
        }
    }

    public void action(Message receivedMessage){
        if (receivedMessage.getMessageType() == MessageType.MOVE_STUDENT){
            MoveMessage moveMessage = (MoveMessage) receivedMessage;
            if(checkController.verifyReceivedData(receivedMessage)){
                if(moveMessage.getNumIsland() == 0) {
                    activePlayer.moveStudentFromEntranceToDiningRoom(new Student(moveMessage.getStudentColor()));
                    broadcastBoardMessage();
                    broadcastGenericMessage(activePlayer.getNickname() + " moved a " + moveMessage.getStudentColor() + " student to his plank!");
                }else{
                    activePlayer.moveStudentFromEntranceToIsland(new Student(moveMessage.getStudentColor()), game.getBoard().getIslands().get(moveMessage.getNumIsland()-1));
                    broadcastBoardMessage();
                    broadcastGenericMessage(activePlayer.getNickname() + " moved a " + moveMessage.getStudentColor() + " student to the island number " + moveMessage.getNumIsland() + "!");
                }actionTurnManager();
            }
        }else if (receivedMessage.getMessageType() == MessageType.MOTHERNATURE_RESULT){
            MotherNatureResult motherNatureResult = (MotherNatureResult) receivedMessage;
            if(checkController.verifyReceivedData(receivedMessage)){
                if(game instanceof GameExpert) {
                    GameExpert gameExpert = (GameExpert) game;
                    for (CharacterCard characterCard : gameExpert.getThreeChosenCards()) {
                        if (characterCard.getCharacterName() == CharacterName.Knight && characterCard.isEnabled()) {
                            activePlayer.setSupremacyCont(2);
                            break;
                        }
                    }
                }
                game.getBoard().moveMotherNature(motherNatureResult.getNumMoves());
                //disable charactercard
                noTowersWin();

            }
        }else if (receivedMessage.getMessageType() == MessageType.CLOUD){
            CloudMessage cloudMessage = (CloudMessage) receivedMessage;
            if(checkController.verifyReceivedData(receivedMessage)){
                activePlayer.moveStudentsFromCloudToEntrance(game.getBoard().getClouds().get(cloudMessage.getNumCloud()-1));
                broadcastBoardMessage();
                broadcastGenericMessage(activePlayer.getNickname() + " chose the cloud number " + cloudMessage.getNumCloud());
                cloudFlag = false;
                moveCont--;
                actionTurnManager();
            }
        }
    }

    public void noTowersWin(){
        for (Player player : game.getPlayers()) {
            if (player.getPlank().getTowerSpace().getTowersList().size() == 0) {
                state = GameState.ENDGAME;
                broadcastGenericMessage(player.getNickname() + " is the WINNER!!!");
                broadcastGenericMessage("Type \"quit\" to leave the game.");
                break;
            }if(game.getPlayers().get(game.getPlayers().size()-1) == player){
                broadcastBoardMessage();
                motherNatureFlag = false;
                cloudFlag = true;
                moveCont--;
                actionTurnManager();
            }
        }
    }

    public void threeIslandEnd(){
        if(game.getBoard().getIslands().size() == 3) {
            boolean pare = false;
            int contProfessorFirstPlayer = 0;
            int contProfessorSecondPlayer = 0;
            int contProfessorThirdPlayer = 0;
            int cont = 0;
            int firstPlayerCont = game.getPlayers().get(0).getPlank().getTowerSpace().getTowersList().size();
            int contTowersPrev;
            int contTowersNext = game.getPlayers().get(0).getPlank().getTowerSpace().getTowersList().size();
            game.getPlayers().get(0).setTowerCont(contTowersNext);
            Player winner = game.getPlayers().get(0);

            for (int i = 0; i < game.getBoard().getProfessorsControlledBy().length; i++) {
                if(game.getBoard().getProfessorsControlledBy()[i].equals(game.getPlayers().get(0).getNickname())){
                    contProfessorFirstPlayer++;
                }if(game.getBoard().getProfessorsControlledBy()[i].equals(game.getPlayers().get(1).getNickname())){
                    contProfessorSecondPlayer++;
                }if(game.getBoard().getProfessorsControlledBy()[i].equals(game.getPlayers().get(2).getNickname())) {
                    contProfessorThirdPlayer++;
                }
            }for (int i = 1; i < game.getPlayers().size(); i++) {
                contTowersPrev = contTowersNext;
                contTowersNext = game.getPlayers().get(i).getPlank().getTowerSpace().getTowersList().size();

                if (contTowersPrev > contTowersNext && firstPlayerCont > contTowersNext) {
                    game.getPlayers().get(i).setTowerCont(contTowersNext);
                    winner = game.getPlayers().get(i);
                    pare = false;

                } if (i == 1 && contTowersPrev == contTowersNext && contTowersNext == winner.getTowerCont()) {
                    game.getPlayers().get(1).setTowerCont(contTowersNext);
                    if (contProfessorFirstPlayer > contProfessorSecondPlayer) {
                        winner = game.getPlayers().get(0);
                    } else if (contProfessorFirstPlayer < contProfessorSecondPlayer) {
                        winner = game.getPlayers().get(1);
                    } else {
                        pare = true;
                        //metodo di patta
                        return;
                    }

                } if (i == 2 && contTowersPrev == contTowersNext && contTowersNext == winner.getTowerCont()) {
                    game.getPlayers().get(2).setTowerCont(contTowersNext);
                    if (contProfessorSecondPlayer > contProfessorThirdPlayer) {
                        winner = game.getPlayers().get(1);
                    } else if (contProfessorSecondPlayer < contProfessorThirdPlayer) {
                        winner = game.getPlayers().get(2);
                    } else {
                        pare = true;
                        //metodo di patta
                        return;
                    }
                }if (i == 2 && contTowersNext == firstPlayerCont && contTowersNext == winner.getTowerCont()) {
                    game.getPlayers().get(2).setTowerCont(contTowersNext);
                    if (contProfessorFirstPlayer > contProfessorThirdPlayer) {
                        winner = game.getPlayers().get(0);
                    } else if (contProfessorFirstPlayer < contProfessorThirdPlayer) {
                        winner = game.getPlayers().get(2);
                    } else {
                        pare = true;
                        //metodo di patta
                        return;
                    }
                }
            }
            //metodo di vincita


                    /*if (player.getPlank().getTowerSpace().getTowersList().size() == 0) {
                        state = GameState.ENDGAME;
                        broadcastGenericMessage(player.getNickname() + " is the WINNER!!!");
                        broadcastGenericMessage("Type \"quit\" to leave the game.");
                        break;
                    }
                    if (game.getPlayers().get(game.getPlayers().size() - 1) == player) {
                        broadcastBoardMessage();
                        motherNatureFlag = false;
                        cloudFlag = true;
                        moveCont--;
                        actionTurnManager();
                    }*/
        }
    }

    public void actionTurnManager(){
        moveCont++;
        if(moveCont < game.getNumPlayers()+1) {
            VirtualView virtualView = virtualViewMap.get(activePlayer.getNickname());
            virtualView.showGenericMessage("Do you want to move a student to your plank or island? [p/i]");
        }else if (moveCont == game.getNumPlayers()+1){
            if (!cloudFlag && motherNatureFlag){
                VirtualView virtualView = virtualViewMap.get(activePlayer.getNickname());
                if(game instanceof GameExpert) {
                    GameExpert gameExpert = (GameExpert) game;
                    for (CharacterCard characterCard : gameExpert.getThreeChosenCards()) {
                        if (characterCard.getCharacterName() == CharacterName.Postman && characterCard.isEnabled()) {
                            virtualView.onDemandMotherNatureMoves(activePlayer.getChosenAssistantCard().getMaxMoves() + 2);
                            break;
                        }if(gameExpert.getThreeChosenCards().get(gameExpert.getThreeChosenCards().size()-1) == characterCard){
                            virtualView.onDemandMotherNatureMoves(activePlayer.getChosenAssistantCard().getMaxMoves());
                        }
                    }
                }else {
                    virtualView.onDemandMotherNatureMoves(activePlayer.getChosenAssistantCard().getMaxMoves());
                }
            }else if(cloudFlag && !motherNatureFlag){
                VirtualView virtualView = virtualViewMap.get(activePlayer.getNickname());
                virtualView.showGenericMessage("Which cloud do you choose? Insert the cloud number.");
            }else if(!cloudFlag && !motherNatureFlag){
                for (int i = 0; i < checkController.getNicknamesInChooseOrder().size(); i++) {
                    if (checkController.getNicknamesInChooseOrder().get(i).equals(activePlayer.getNickname())) {
                        activePlayer = game.getPlayerByNickname(checkController.getNicknamesInChooseOrder().get((i + 1) % checkController.getNicknamesInChooseOrder().size()));
                        moveCont = 0;
                        turnCont++;
                        if (turnCont == game.getPlayers().size()) {
                            state = GameState.PLAN;
                            int getNumCardOtherPlayerSize = checkController.getNumCardOtherPlayers().size();
                            for (int j = 0; j < getNumCardOtherPlayerSize; j++) {
                                checkController.getNumCardOtherPlayers().remove(0);
                                checkController.getNicknamesInChooseOrder().remove(0);
                            }
                            game.getBoard().moveStudentsFromBagToClouds();
                            broadcastBoardMessage();
                            activePlayer = game.getPlayerByNickname(checkController.getFirstPlayerInAction());    //mezzo inutile
                            VirtualView virtualView = virtualViewMap.get(activePlayer.getNickname());
                            virtualView.onDemandAssistantCard(activePlayer.getDeck().getDeck());
                            turnCont = 0;
                        } else {
                            VirtualView virtualView = virtualViewMap.get(activePlayer.getNickname());
                            virtualView.showGenericMessage("Do you want to move a student to your plank or island? [p/i]");
                        }
                        motherNatureFlag = true;
                        cloudFlag = false;
                        break;
                    }
                }
            }
        }
    }


    @Override
    public void update(Message message) {

    }

}
