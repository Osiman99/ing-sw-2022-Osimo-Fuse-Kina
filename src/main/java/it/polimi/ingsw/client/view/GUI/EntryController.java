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


public class EntryController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button loginButton;


    public void login(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(new File("src/main/java/it/polimi/ingsw/client/view/GUI/files/connection.fxml").toURI().toURL());
        root = loader.load();
        root.setId("connection");


        stage = (Stage) ((Node) loginButton).getScene().getWindow();
        scene = new Scene(root);

        scene.getStylesheets().add(new File("src/main/java/it/polimi/ingsw/client/view/GUI/files/style.css").toURI().toURL().toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
