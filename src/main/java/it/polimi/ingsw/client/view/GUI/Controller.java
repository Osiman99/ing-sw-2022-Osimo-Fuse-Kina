package it.polimi.ingsw.client.view.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;


public class Controller {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button loginButton;

    public void exit(ActionEvent actionEvent) {
        /*
        exitButton.setOnAction((actionEvent1 -> {
            Platform.exit();
        }));

         */
    }

    public void login(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(new File("src/main/java/it/polimi/ingsw/client/view/GUI/connection.fxml").toURI().toURL());
        root = loader.load();

        stage = (Stage) ((Node) loginButton).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
