package it.polimi.ingsw.client.view.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class ModalityController {

    ObservableList<String> modeChoiceList = FXCollections.observableArrayList("Normal","Expert");
    ObservableList<String> playersChoiceList = FXCollections.observableArrayList("2","3");

    @FXML
    private ChoiceBox modeChoice;
    @FXML
    private ChoiceBox playersChoice;
    @FXML
    private Button playButton;

    @FXML
    public void initialize(){
        modeChoice.setValue("");
        playersChoice.setValue("");

    }
}
