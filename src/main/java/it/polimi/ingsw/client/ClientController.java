package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.server.model.*;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ClientController implements ViewObserver, Observer {
    private final View view;
    private final ExecutorService taskQueue;
    private Client client;
    private String nickname;


    /**
     * ClientController constructor
     *
     * @param view the view to be controlled.
     */
    public ClientController(View view) {
        this.view = view;
        taskQueue = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onUpdateServerInfo(Map<String, String> serverInfo) {

        try {
            client = new ServerHandler(serverInfo.get("address"), Integer.parseInt(serverInfo.get("port")));
            client.addObserver(this);
            client.readMessage();
            client.enablePinger(true);
            taskQueue.execute(view::onDemandNickname);
        } catch (IOException e) {
            taskQueue.execute(() -> view.showLoginResult(false, false, this.nickname));
        }
    }

    @Override
    public void onUpdatePlayersNumber(int playersNumber) {
        client.sendMessage(new PlayerNumberResult(this.nickname, playersNumber));
    }

    @Override
    public void onUpdateNickname(String nickname) {
        this.nickname = nickname;
        client.sendMessage(new LoginRequest(this.nickname));
    }

    @Override
    public void onUpdateAssistantCard(int card) {
        client.sendMessage(new AssistantCardResult(this.nickname, card));
    }

    @Override
    public void onUpdateMoveStudentToDiningRoom(StudentColor studentColor) {
        client.sendMessage(new MoveMessage(this.nickname, studentColor, 0));
    }

    @Override
    public void onUpdateMoveStudentToIsland(StudentColor studentColor, int numIsland) {
        client.sendMessage(new MoveMessage(this.nickname, studentColor, numIsland));
    }

    @Override
    public void onUpdateCloud(int numCloud) {
        client.sendMessage(new CloudMessage(this.nickname, numCloud));
    }

    @Override
    public void onUpdateMotherNatureMoves(int numMoves){
        client.sendMessage(new MotherNatureResult(this.nickname, numMoves));
    }

    @Override
    public void onUpdateCharacterCardsDescription(String askInterrupted) {
        client.sendMessage(new CharacterCardsDescriptionRequest(this.nickname, askInterrupted));
    }

    @Override
    public void onUpdateQuit(String quit){
        client.sendMessage(new EndMessage(this.nickname, quit));
    }

    @Override
    public void onUpdateMode(String mode){
        client.sendMessage(new ModeMessage(this.nickname, mode));
    }

    @Override
    public void onUpdateCharacterCard(String card, StudentColor studentColor, int numIsland){
        client.sendMessage(new CharacterCardMessage(this.nickname, card, studentColor, numIsland));
    }

    @Override
    public void update(Message message) {
        switch(message.getMessageType()) {
            case PLAYERNUMBER_REQUEST:
                taskQueue.execute(view::onDemandPlayersNumber);
                break;
            case GENERIC_MESSAGE:
                taskQueue.execute(() -> view.showGenericMessage(((GenericMessage) message).getMessage()));
                break;
            case LOGIN_RESULT:
                LoginResult loginResult = (LoginResult) message;
                taskQueue.execute(() -> view.showLoginResult(loginResult.isNicknameAccepted(), loginResult.isConnectionSuccessful(), this.nickname));
                break;
            case ASSISTANTCARD_REQUEST:
                AssistantCardRequest assistantCardRequest = (AssistantCardRequest) message;
                taskQueue.execute(()-> view.onDemandAssistantCard(assistantCardRequest.getDeck()));
                break;
            case BOARD_MESSAGE:
                BoardMessage boardMessage = (BoardMessage) message;
                taskQueue.execute(()-> view.drawBoard(boardMessage.getGame()));
                break;
            case MOTHERNATURE_REQUEST:
                MotherNatureRequest motherNatureRequest = (MotherNatureRequest) message;
                taskQueue.execute(()-> view.onDemandMotherNatureMoves(motherNatureRequest.getMaxMoves()));
                break;
            case DISCONNECTION:
                DisconnectionMessage dm = (DisconnectionMessage) message;
                client.disconnect();
                view.showDisconnectionMessage(dm.getNicknameDisconnected(), dm.getMessageStr());
                break;
            case CHARACTERCARDS_DESCRIPTION_RESULT:
                CharacterCardsDescriptionResult characterCardsDescriptionResult = (CharacterCardsDescriptionResult) message;
                taskQueue.execute(()-> view.onDemandCharacterCard(characterCardsDescriptionResult.getText()));
                break;
                case ERROR:
                ErrorMessage errorMessage = (ErrorMessage) message;
                view.showErrorAndQuit(errorMessage.getError());
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + message.getMessageType());
        }
    }
}
