package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.view.CLI.ANSIColor;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.model.*;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
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
    private boolean endgame;
    private Lobby lobby;
    private String askInterrupted;
    private String[] text;
    private boolean serverEndFlag;
    private boolean acFlag;


    public GameController(){
        lobby = new Lobby(this);
        virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        checkController = new CheckController(virtualViewMap, this);
        setGameState(GameState.PREGAME);
        turnCont = 0;
        moveCont = 0;
        cloudFlag = false;
        motherNatureFlag = true;
        endgame = false;
        askInterrupted = "";
        text = new String[3];
        serverEndFlag = false;
        acFlag = false;
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
        if (virtualViewMap.isEmpty()) { // First player logged. Ask the game mode.
            addVirtualView(nickname, virtualView);
            lobby.addPlayer(nickname);
            lobby.getPlayers().get(0).setPlayerColor(TowerColor.BLACK);
            virtualView.showLoginResult(true, true, Game.SERVER_NICKNAME);
            virtualView.showGenericMessage("Do you want to play in " + ANSIColor.UNDERLINE +"Normal" +ANSIColor.RESET+  " or " +ANSIColor.UNDERLINE+  "Expert" +ANSIColor.RESET+ " mode? [n/e]");

        } else if (!lobby.isFull()) {
            addVirtualView(nickname, virtualView);
            lobby.addPlayer(nickname);
            if (virtualViewMap.size() == 2) {
                lobby.getPlayers().get(1).setPlayerColor(TowerColor.WHITE);
            }else if (virtualViewMap.size() == 3){
                lobby.getPlayers().get(2).setPlayerColor(TowerColor.GREY);
            }
            virtualView.showLoginResult(true, true, Game.SERVER_NICKNAME);


            if (lobby.isFull()) { // If all players logged
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
            server = Server.getInstance();
            server.getClientHandlerMap().remove(nickname);
            //LOGGER.info(() -> "Removed " + nickname + " from the client list.");
            virtualView.showGenericMessage(ANSIColor.RED + "Max players reached. Connection refused." + ANSIColor.RESET);
            virtualView.showDisconnectionMessage(nickname, " disconnected from the Server.");
        }
    }

    private void login(Message receivedMessage){
        if(receivedMessage.getMessageType() == MessageType.MODE_MESSAGE){
            ModeMessage modeMessage = (ModeMessage) receivedMessage;
            if(checkController.verifyReceivedData(modeMessage)){  //check Controller
                lobby.setMode(modeMessage.getMode());
                VirtualView virtualView = virtualViewMap.get(modeMessage.getNickname());
                virtualView.onDemandPlayersNumber();
            }
        }

        if (receivedMessage.getMessageType() == MessageType.PLAYERNUMBER_REPLY) {
            PlayerNumberReply playerNumberReply = (PlayerNumberReply) receivedMessage;
            if (checkController.verifyReceivedData(receivedMessage)) {
                lobby.setNumPlayers(playerNumberReply.getPlayerNumber());
                //game.setChosenPlayersNumber(((PlayerNumberReply) receivedMessage).getPlayerNumber());
                if (lobby.checkStart()){
                    server = Server.getInstance();
                    lobby.deleteExtraPlayers();
                    lobby.setFull(true);
                    initGame();
                }else {
                    broadcastGenericMessage("Waiting for other Players . . .");
                }
            }
        } else {
            Server.LOGGER.warning("Wrong message received from client.");
        }
    }



    public void initGame(){
        if (lobby.getMode().equals("n")){
            game = new Game();
        }else{
            game = new GameExpert();
        }

        for(int i = 0; i < lobby.getNumPlayers(); i++){
            game.addPlayer(lobby.getPlayers().get(i));
        }

        for (Map.Entry<String, VirtualView> entry : virtualViewMap.entrySet()) {
            game.addObserver(entry.getValue());
            game.getBoard().addObserver(entry.getValue());
            for(int j = 0; j < game.getPlayers().size(); j++) {
                game.getPlayers().get(j).addObserver(entry.getValue());
            }
        }

        game.setChosenPlayersNumber(lobby.getNumPlayers());

        nicknames = new ArrayList<>(game.getNicknames());
        for (int i = 0; i < game.getNumPlayers(); i++)
            if (game.getPlayers().get(i).getNickname().equals(nicknames.get(0))) {
                activePlayer = game.getPlayers().get(i);
            }
        game.initGame();
        if (game instanceof GameExpert){
            GameExpert gameExpert = (GameExpert) game;
            gameExpert.initGameExpert();
            ccDescription();
        }

        setGameState(GameState.PLAN);
        game.setState(GameState.PLAN);
        game.getBoard().moveStudentsFromBagToClouds();
        broadcastGenericMessage("All Players are connected. " + ANSIColor.PURPLE_BOLD_BRIGHT+ activePlayer.getNickname().toUpperCase() +ANSIColor.RESET + " is choosing the Assistant Card...");


        VirtualView virtualView = virtualViewMap.get(activePlayer.getNickname());
        virtualView.onDemandAssistantCard(activePlayer.getDeck().getDeck());
    }

    public void broadcastGenericMessage(String message) {
        for (VirtualView virtualView : virtualViewMap.values()) {
            virtualView.showGenericMessage(message);
        }
    }


    public void setGameState(GameState state) {
        this.state = state;
    }

    public void addVirtualView(String nickname, VirtualView virtualView) {
        virtualViewMap.put(nickname, virtualView);
    }

    public boolean isGameStarted() {
        return state != GameState.PREGAME;
    }

    public boolean checkLoginNickname(String nickname, View view) {
        return checkController.checkLoginNickname(nickname, view);
    }


    public void removeVirtualView(String nickname) {
        VirtualView vv = virtualViewMap.remove(nickname);

        game.removeObserver(vv);
        game.getBoard().removeObserver(vv);
        game.removePlayerByNickname(nickname);
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


    public void quitFromServer(String nickname){
        state = GameState.ENDGAME;
        for (Map.Entry<String, VirtualView> entry : virtualViewMap.entrySet()) {
            if(!entry.getKey().equals(nickname))
                entry.getValue().showGenericMessage(ANSIColor.RED + "One player exit the game. The game is over." + ANSIColor.RESET);
                entry.getValue().showGenericMessage("Type \"quit\" to leave the game.");
        }
    }

    public void end(Message receivedMessage){
        server = Server.getInstance();
        if (receivedMessage.getMessageType() == MessageType.END_MESSAGE) {
            virtualViewMap.get(receivedMessage.getNickname()).showDisconnectionMessage(receivedMessage.getNickname(), "disconnected from the Server.");
            ClientHandler clientHandler = server.getClientHandlerMap().get(receivedMessage.getNickname());
            clientHandler.disconnect();
        }if (virtualViewMap.isEmpty()){
            endGame();
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
        lobby = new Lobby(this);
        virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        checkController = new CheckController(virtualViewMap, this);
        setGameState(GameState.PREGAME);
        turnCont = 0;
        moveCont = 0;
        cloudFlag = false;
        motherNatureFlag = true;
        endgame = false;
        askInterrupted = "";
        text = new String[3];
        serverEndFlag = false;
        acFlag = false;
    }

    public void plan(Message receivedMessage){
        if (receivedMessage.getMessageType() == MessageType.ASSISTANTCARD_RESULT){
            if (checkController.verifyReceivedData(receivedMessage)) {
                if(activePlayer.getDeck().getDeck().size() == 1){
                    acFlag = true;
                }
                activePlayer.chooseAssistantCard(((AssistantCardResult) receivedMessage).getCard());
                broadcastGenericMessage(ANSIColor.PURPLE_BOLD_BRIGHT+ activePlayer.getNickname().toUpperCase() +ANSIColor.CYAN_BOLD +  " chose the Card number " + ((AssistantCardResult) receivedMessage).getCard() +ANSIColor.RESET );
                planTurnManager();
            }
        }else{
            Server.LOGGER.warning("Wrong message received from client.");
        }
    }

    public void planTurnManager(){
        if(!endgame) {
            for (int i = 0; i < game.getPlayers().size(); i++) {
                if (game.getPlayers().get(i).getNickname().equals(activePlayer.getNickname())) {
                    activePlayer = game.getPlayers().get((i + 1) % game.getPlayers().size());
                    turnCont++;
                    if (turnCont == game.getPlayers().size()) {
                        state = GameState.ACTION;
                        game.setState(GameState.ACTION);
                        game.notifyBoard();
                        checkController.initializeFirstPlayerInAction();
                        activePlayer = game.getPlayerByNickname(checkController.getFirstPlayerInAction());
                        broadcastWaitingMessage(activePlayer);
                        VirtualView virtualView = virtualViewMap.get(activePlayer.getNickname());
                        virtualView.showGenericMessage("Do you want to move a student to your plank or island? [p/i]");
                        turnCont = 0;
                    }
                    if (state == GameState.PLAN) {
                        broadcastWaitingMessage(activePlayer);
                        VirtualView virtualView = virtualViewMap.get(activePlayer.getNickname());
                        virtualView.onDemandAssistantCard(activePlayer.getDeck().getDeck());
                    }
                    break;
                }
            }
        }
    }

    public void assistantCardEnd(){
        if(activePlayer.isDeckEmpty()){
            establishWin();
        }
    }

    public void ccDescription(){
        GameExpert gameExpert = (GameExpert) game;
        for(int i = 0; i < 3; i++) {
            text[i] = gameExpert.getThreeChosenCards().get(i).getDescription();
        }
    }

    public void characterCardsDescription(Message receivedMessage){
        if (receivedMessage.getMessageType() == MessageType.CHARACTERCARDS_DESCRIPTION_REQUEST){
            CharacterCardsDescriptionRequest characterCardsDescriptionRequest = (CharacterCardsDescriptionRequest) receivedMessage;
            VirtualView virtualView = virtualViewMap.get(activePlayer.getNickname());
            if(game instanceof GameExpert) {
                askInterrupted = characterCardsDescriptionRequest.getAskInterrupted();
                virtualView.onDemandCharacterCard(text);
            }else{
                if(characterCardsDescriptionRequest.getAskInterrupted().equals("s")) {
                    virtualView.showGenericMessage(ANSIColor.RED+"Invalid input! Please try again."+ANSIColor.RESET);
                    virtualView.showGenericMessage("Do you want to move a student to your plank or island? [p/i]");
                }else if(characterCardsDescriptionRequest.getAskInterrupted().equals("m")){
                    virtualView.showGenericMessage(ANSIColor.RED+"Invalid input! Please try again."+ANSIColor.RESET);
                    virtualView.onDemandMotherNatureMoves(activePlayer.getChosenAssistantCard().getMaxMoves());
                }else if(characterCardsDescriptionRequest.getAskInterrupted().equals("c")){
                    virtualView.showGenericMessage(ANSIColor.RED+"Invalid input! Please try again."+ANSIColor.RESET);
                    virtualView.showGenericMessage("Which cloud do you choose? Insert the cloud number.");
                }
            }
        }
    }

    public void characterCardManager(Message receivedMessage){
        if (receivedMessage.getMessageType() == MessageType.CHARACTERCARDS_REQUEST) {
            VirtualView virtualView = virtualViewMap.get(activePlayer.getNickname());
            GameExpert gameExpert = (GameExpert) game;
            CharacterCardMessage characterCardMessage = (CharacterCardMessage) receivedMessage;
            if(checkController.verifyReceivedData(receivedMessage)) {            //DA IMPLEMENTARE IL CONTROLLO
                switch (characterCardMessage.getCard()) {
                    case "sommelier":
                        for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                            if (cc.getCharacterName() == CharacterName.Sommelier){
                                gameExpert.getBoard().applyEffectSommelier(activePlayer, cc, characterCardMessage.getStudentColor(), characterCardMessage.getNumIsland());
                            }
                        }
                        break;
                    case "chef":
                        for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                            if (cc.getCharacterName() == CharacterName.Chef){
                                gameExpert.getBoard().applyEffectChef(activePlayer, cc);
                            }
                        }
                        break;
                    case "messenger":
                        for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                            if (cc.getCharacterName() == CharacterName.Messenger){
                                gameExpert.getBoard().applyEffectMessenger(activePlayer, cc, characterCardMessage.getNumIsland());
                            }
                        }
                        break;
                    case "postman":
                        for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                            if (cc.getCharacterName() == CharacterName.Postman){
                                gameExpert.getBoard().applyEffectPostman(activePlayer, cc);
                            }
                        }
                        break;
                    case "herbalist":
                        for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                            if (cc.getCharacterName() == CharacterName.Herbalist){
                                gameExpert.getBoard().applyEffectHerbalist(activePlayer, cc, characterCardMessage.getNumIsland());
                            }
                        }
                        break;
                    case "centaur":
                        for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                            if (cc.getCharacterName() == CharacterName.Centaur){
                                gameExpert.getBoard().applyEffectCentaur(activePlayer, cc);
                            }
                        }
                        break;
                    //case "joker":
                        //break;
                    case "knight":
                        for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                            if (cc.getCharacterName() == CharacterName.Knight){
                                gameExpert.getBoard().applyEffectKnight(activePlayer, cc);
                            }
                        }
                        break;
                    //case "merchant":
                        //break;
                    //case "musician":
                        //break;
                    case "lady":
                        for(CharacterCard cc : gameExpert.getThreeChosenCards()) {
                            if (cc.getCharacterName() == CharacterName.Lady){
                                gameExpert.getBoard().applyEffectLady(activePlayer, cc, characterCardMessage.getStudentColor());
                            }
                        }
                        break;
                    //case "sinister":
                        //break;
                }
                if (askInterrupted.equals("s")){
                    virtualView.showGenericMessage("Do you want to move a student to your plank or island? [p/i]");
                }else if(askInterrupted.equals("m")){
                    for (CharacterCard characterCard : gameExpert.getThreeChosenCards()) {
                        if (characterCard.getCharacterName() == CharacterName.Postman && characterCard.isEnabled()) {
                            virtualView.onDemandMotherNatureMoves(activePlayer.getChosenAssistantCard().getMaxMoves() + 2);
                            return;
                        }
                    }virtualView.onDemandMotherNatureMoves(activePlayer.getChosenAssistantCard().getMaxMoves());
                }else if(askInterrupted.equals("c")){
                    virtualView.showGenericMessage("Which cloud do you choose? Insert the cloud number.");
                }
            }
        }
    }

    public void action(Message receivedMessage){
        characterCardsDescription(receivedMessage);
        characterCardManager(receivedMessage);

        if (receivedMessage.getMessageType() == MessageType.MOVE_STUDENT){
            MoveMessage moveMessage = (MoveMessage) receivedMessage;
            if(checkController.verifyReceivedData(receivedMessage)){
                if(moveMessage.getNumIsland() == 0) {
                    activePlayer.moveStudentFromEntranceToDiningRoom(new Student(moveMessage.getStudentColor()));
                    broadcastGenericMessage( ANSIColor.PURPLE_BOLD_BRIGHT+ activePlayer.getNickname().toUpperCase() +ANSIColor.CYAN_BOLD + " moved a " + moveMessage.getStudentColor() + " student to his plank!"+ANSIColor.RESET);
                    broadcastWaitingMessage(activePlayer);
                }else{
                    activePlayer.moveStudentFromEntranceToIsland(new Student(moveMessage.getStudentColor()), game.getBoard().getIslands().get(moveMessage.getNumIsland()-1));
                    broadcastGenericMessage(ANSIColor.PURPLE_BOLD_BRIGHT+ activePlayer.getNickname().toUpperCase() +ANSIColor.CYAN_BOLD +" moved a " + moveMessage.getStudentColor() + " student to the island number " + moveMessage.getNumIsland() + "!"+ANSIColor.RESET);
                    broadcastWaitingMessage(activePlayer);
                }actionTurnManager();
            }
        }else if (receivedMessage.getMessageType() == MessageType.MOTHERNATURE_RESULT){
            MotherNatureResult motherNatureResult = (MotherNatureResult) receivedMessage;
            if(checkController.verifyReceivedData(receivedMessage)){
                if(game instanceof GameExpert) {
                    GameExpert gameExpert = (GameExpert) game;
                    for (CharacterCard characterCard : gameExpert.getThreeChosenCards()) {
                        if (characterCard.getCharacterName() == CharacterName.Postman && characterCard.isEnabled()) {
                            characterCard.setEnabled(false);
                        }
                        if (characterCard.getCharacterName() == CharacterName.Knight && characterCard.isEnabled()) {
                            activePlayer.setSupremacyCont(2);
                            break;
                        }
                    }
                }
                game.getBoard().moveMotherNature(motherNatureResult.getNumMoves());
                if(game instanceof GameExpert) {
                    GameExpert gameExpert = (GameExpert) game;
                    for (CharacterCard characterCard : gameExpert.getThreeChosenCards()) {
                        if (characterCard.getCharacterName() == CharacterName.Knight && characterCard.isEnabled()) {
                            characterCard.setEnabled(false);
                        }
                    }
                }
                broadcastGenericMessage(ANSIColor.PURPLE_BOLD_BRIGHT + activePlayer.getNickname() + ANSIColor.RESET + ANSIColor.CYAN_BOLD + " moved MotherNature by " + motherNatureResult.getNumMoves() + " steps" + ANSIColor.RESET);
                broadcastWaitingMessage(activePlayer);
                noTowersWin();
                if (!endgame)
                    threeIslandEnd();
                if(!endgame) {
                    motherNatureFlag = false;
                    cloudFlag = true;
                    moveCont--;
                    actionTurnManager();
                }

            }
        }else if (receivedMessage.getMessageType() == MessageType.CLOUD){
            CloudMessage cloudMessage = (CloudMessage) receivedMessage;
            if(checkController.verifyReceivedData(receivedMessage)){
                activePlayer.moveStudentsFromCloudToEntrance(game.getBoard().getClouds().get(cloudMessage.getNumCloud()-1));
                broadcastGenericMessage(ANSIColor.PURPLE_BOLD_BRIGHT+ activePlayer.getNickname().toUpperCase() +ANSIColor.CYAN_BOLD + " chose the cloud number " + cloudMessage.getNumCloud()+ANSIColor.RESET);
                cloudFlag = false;
                moveCont--;
                actionTurnManager();
            }
        }
    }

    public void noTowersWin(){
        for (Player player : game.getPlayers()) {
            if (player.isTowerSpaceEmpty()) {
                state = GameState.ENDGAME;
                game.setState(GameState.ENDGAME);
                broadcastGenericMessage(ANSIColor.PURPLE_BOLD_BRIGHT+ player.getNickname().toUpperCase() +ANSIColor.CYAN_BOLD+ " is the WINNER!!!"+ANSIColor.RESET);
                broadcastGenericMessage("Type \"quit\" to leave the game.");
                endgame = true;
                return;
            }
        }
    }

    public void threeIslandEnd(){
        if(game.getBoard().areIslandsLessThanThree()) {
            establishWin();
        }
    }

    public void establishWin(){
        boolean tie = false;
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
            if(game.getNumPlayers() == 2){
                if (game.getBoard().getProfessorsControlledBy()[i].equals(game.getPlayers().get(0).getNickname())) {
                    contProfessorFirstPlayer++;
                }
                if (game.getBoard().getProfessorsControlledBy()[i].equals(game.getPlayers().get(1).getNickname())) {
                    contProfessorSecondPlayer++;
                }
            }else if(game.getNumPlayers() == 3) {
                if (game.getBoard().getProfessorsControlledBy()[i].equals(game.getPlayers().get(0).getNickname())) {
                    contProfessorFirstPlayer++;
                }
                if (game.getBoard().getProfessorsControlledBy()[i].equals(game.getPlayers().get(1).getNickname())) {
                    contProfessorSecondPlayer++;
                }
                if (game.getBoard().getProfessorsControlledBy()[i].equals(game.getPlayers().get(2).getNickname())) {
                    contProfessorThirdPlayer++;
                }
            }
        }for (int i = 1; i < game.getPlayers().size(); i++) {
            contTowersPrev = contTowersNext;
            contTowersNext = game.getPlayers().get(i).getPlank().getTowerSpace().getTowersList().size();

            if (contTowersPrev > contTowersNext && firstPlayerCont > contTowersNext) {
                game.getPlayers().get(i).setTowerCont(contTowersNext);
                winner = game.getPlayers().get(i);
                tie = false;

            } if (i == 1 && contTowersPrev == contTowersNext && contTowersNext == winner.getTowerCont()) {
                game.getPlayers().get(1).setTowerCont(contTowersNext);
                if (contProfessorFirstPlayer > contProfessorSecondPlayer) {
                    winner = game.getPlayers().get(0);
                } else if (contProfessorFirstPlayer < contProfessorSecondPlayer) {
                    winner = game.getPlayers().get(1);
                } else {
                    tie = true;
                    state = GameState.ENDGAME;
                    game.setState(GameState.ENDGAME);
                    broadcastGenericMessage(ANSIColor.CYAN_BOLD +"It's a DRAW!"+ANSIColor.RESET);
                    broadcastGenericMessage("Type \"quit\" to leave the game.");
                    endgame = true;
                    return;
                }

            } if (i == 2 && contTowersPrev == contTowersNext && contTowersNext == winner.getTowerCont()) {
                game.getPlayers().get(2).setTowerCont(contTowersNext);
                if (contProfessorSecondPlayer > contProfessorThirdPlayer) {
                    winner = game.getPlayers().get(1);
                } else if (contProfessorSecondPlayer < contProfessorThirdPlayer) {
                    winner = game.getPlayers().get(2);
                } else {
                    tie = true;
                    state = GameState.ENDGAME;
                    game.setState(GameState.ENDGAME);
                    broadcastGenericMessage(ANSIColor.CYAN_BOLD +"It's a DRAW!"+ANSIColor.RESET);
                    broadcastGenericMessage("Type \"quit\" to leave the game.");
                    endgame = true;
                    return;
                }
            }if (i == 2 && contTowersNext == firstPlayerCont && contTowersNext == winner.getTowerCont()) {
                game.getPlayers().get(2).setTowerCont(contTowersNext);
                if (contProfessorFirstPlayer > contProfessorThirdPlayer) {
                    winner = game.getPlayers().get(0);
                } else if (contProfessorFirstPlayer < contProfessorThirdPlayer) {
                    winner = game.getPlayers().get(2);
                } else {
                    tie = true;
                    state = GameState.ENDGAME;
                    game.setState(GameState.ENDGAME);
                    broadcastGenericMessage(ANSIColor.CYAN_BOLD +"It's a DRAW!"+ANSIColor.RESET);
                    broadcastGenericMessage("Type \"quit\" to leave the game.");
                    endgame = true;
                    return;
                }
            }
        }
        state = GameState.ENDGAME;
        game.setState(GameState.ENDGAME);
        broadcastGenericMessage(ANSIColor.PURPLE_BOLD_BRIGHT+ winner.getNickname().toUpperCase()+ANSIColor.CYAN_BOLD + " is the WINNER!!!"+ANSIColor.RESET);
        broadcastGenericMessage("Type \"quit\" to leave the game.");
        endgame = true;
    }

    public void bagEmptyEnd(){
        if (game.getBoard().isBagEmptyGC()) {
            establishWin();
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
                if (game instanceof GameExpert){
                    GameExpert gameExpert = (GameExpert) game;
                    for (CharacterCard characterCard : gameExpert.getThreeChosenCards()) {
                        characterCard.setEnabled(false);
                    }
                }
                for (int i = 0; i < checkController.getNicknamesInChooseOrder().size(); i++) {
                    if (checkController.getNicknamesInChooseOrder().get(i).equals(activePlayer.getNickname())) {
                        game.getBoard().setCardActivated(false);
                        activePlayer = game.getPlayerByNickname(checkController.getNicknamesInChooseOrder().get((i + 1) % checkController.getNicknamesInChooseOrder().size()));
                        moveCont = 0;
                        turnCont++;
                        if (turnCont == game.getPlayers().size()) {
                            bagEmptyEnd();
                            if(!endgame) {
                                if(!acFlag) {
                                    state = GameState.PLAN;
                                    game.setState(GameState.PLAN);
                                    int getNumCardOtherPlayerSize = checkController.getNumCardOtherPlayers().size();
                                    for (int j = 0; j < getNumCardOtherPlayerSize; j++) {
                                        checkController.getNumCardOtherPlayers().remove(0);
                                        checkController.getNicknamesInChooseOrder().remove(0);
                                    }
                                    game.getBoard().moveStudentsFromBagToClouds();
                                    activePlayer = game.getPlayerByNickname(checkController.getFirstPlayerInAction());    //mezzo inutile
                                    broadcastWaitingMessage(activePlayer);
                                    VirtualView virtualView = virtualViewMap.get(activePlayer.getNickname());
                                    virtualView.onDemandAssistantCard(activePlayer.getDeck().getDeck());
                                    turnCont = 0;
                                }else{
                                    assistantCardEnd();
                                }
                            }
                        } else {
                            broadcastWaitingMessage(activePlayer);
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

    public void broadcastWaitingMessage(Player activePlayer){
        for (VirtualView vv : virtualViewMap.values()){
            if (vv != virtualViewMap.get(activePlayer.getNickname())){
                vv.showGenericMessage("Waiting for " + ANSIColor.PURPLE_BOLD_BRIGHT + activePlayer.getNickname().toUpperCase() + ANSIColor.RESET + "...");
            }
        }
    }

    public Server getServer() {
        return server;
    }

    public GameState getState() {
        return state;
    }

    public void setServerEndFlag(boolean serverEndFlag) {
        this.serverEndFlag = serverEndFlag;
    }

    public String[] getText() {
        return text;
    }

    public String getAskInterrupted() {
        return askInterrupted;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public Map<String, VirtualView> getVirtualViewMap() {
        return virtualViewMap;
    }

    @Override
    public void update(Message message) {

    }

}
