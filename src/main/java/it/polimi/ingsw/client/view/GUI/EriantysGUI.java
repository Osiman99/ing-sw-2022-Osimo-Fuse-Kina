package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.server.model.AssistantCard;
import it.polimi.ingsw.server.model.Game;
 import javafx.application.Platform;

import java.util.List;

public class EriantysGUI extends ViewObservable implements View {
    @Override
    public void onDemandNickname() {
        //Platform.runLater();
    }

    @Override
    public void onDemandPlayersNumber() {

    }

    @Override
    public void onDemandAssistantCard(List<AssistantCard> deck) {

    }

    @Override
    public void showLoginResult(boolean nicknameAccepted, boolean connection, String nickname) {

    }

    @Override
    public void showGenericMessage(String genericMessage) {

    }

    @Override
    public void showDisconnectionMessage(String nicknameDisconnected, String value) {

    }

    @Override
    public void drawBoard(Game game) {

    }

    @Override
    public void onDemandMotherNatureMoves(int maxMoves) {

    }

    @Override
    public void onDemandCharacterCard(String[] text) {

    }

    @Override
    public void showErrorAndQuit(String error) {

    }
}
