package it.polimi.ingsw.client.view.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

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

    public void play(ActionEvent actionEvent) throws IOException {
        if(modeChoice.getSelectionModel().getSelectedItem()!=null && playersChoice.getSelectionModel().getSelectedItem()!=null ) {
            String mode = modeChoice.getSelectionModel().getSelectedItem().toString();
            int playerNumber = Integer.parseInt(playersChoice.getSelectionModel().getSelectedItem().toString());
            System.out.println("MODE : "+ mode);
            System.out.println("PLAYERS NUMBER : "+ playerNumber);

        }
        else{
            System.out.println("Insert again!");
        }


        FXMLLoader loader = new FXMLLoader(new File("src/main/java/it/polimi/ingsw/client/view/GUI/files/play.fxml").toURI().toURL());
        root = loader.load();

        stage = (Stage) (playButton).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
