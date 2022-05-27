package it.polimi.ingsw.client.view.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

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
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private Parent root;

    @FXML
    public void initialize(){
        modeChoice.setItems(modeChoiceList);
        playersChoice.setItems(playersChoiceList);

    }

    public void play(ActionEvent actionEvent) {

    }
}
