package it.polimi.ingsw.server;

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
    public int turnCont;


    public GameController(){
        this.game = Game.getInstance();
        virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        checkController = new CheckController(virtualViewMap, this);
        setGameState(GameState.PREGAME);
        turnCont = 0;
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
    }

    public List<String> getNicknames() {
        return nicknames;
    }


    public void broadcastDisconnectionMessage(String nicknameDisconnected, String text) {
        for (VirtualView virtualView : virtualViewMap.values()) {
            virtualView.showDisconnectionMessage(nicknameDisconnected, text);
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
                    virtualView.showGenericMessage("Choose your student to move. [e.g. b p/b 3]");
                    turnCont = 0;
                }if(state == GameState.PLAN) {
                    VirtualView virtualView = virtualViewMap.get(activePlayer.getNickname());
                    virtualView.onDemandAssistantCard(activePlayer.getDeck().getDeck());
                }break;
            }
        }
    }

    public void action(Message receivedMessage){
        MoveMessage moveMessage = (MoveMessage) receivedMessage;
        if (receivedMessage.getMessageType() == MessageType.MOVE_STUDENT){
            if(checkController.verifyReceivedData(receivedMessage)){
                if(moveMessage.getNumIsland() == 0) {
                    activePlayer.moveStudentFromEntranceToDiningRoom(new Student(moveMessage.getStudentColor()));
                    System.out.println(activePlayer.getPlank().getDiningRoom()[0].getStudents().size());
                    System.out.println(activePlayer.getPlank().getDiningRoom()[0].getStudents().get(0).getColor());
                    broadcastBoardMessage();
                    broadcastGenericMessage(activePlayer.getNickname() + " moved a " + moveMessage.getStudentColor() + " student to his plank!");
                }else{
                    activePlayer.moveStudentFromEntranceToIsland(new Student(moveMessage.getStudentColor()), game.getBoard().getIslands().get(moveMessage.getNumIsland()));
                    broadcastBoardMessage();
                    broadcastGenericMessage(activePlayer.getNickname() + " moved a " + moveMessage.getStudentColor() + " student to the island number " + moveMessage.getNumIsland() + "!");
                }
            }
        }
    }


    @Override
    public void update(Message message) {

    }

}
