package it.polimi.ingsw.client.view.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ConnectionController {

    @FXML
    private Scene scene;

    @FXML
    private Stage stage;

    @FXML
    private Button connectButton;

    @FXML
    private Parent root;

    public void modalityScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(new File("src/main/java/it/polimi/ingsw/client/view/GUI/files/modality.fxml").toURI().toURL());
        root = loader.load();

        stage = (Stage) (connectButton).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
