package it.polimi.ingsw.client.view.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private TextField ipAddressText;
    @FXML
    private TextField portText;
    @FXML
    private TextField nicknameText;
    @FXML
    private Parent root;

    private String ipAddress = "";
    private String port = "";
    private String nickname = "";


    @FXML
    public void initialize(){

    }

    public void modalityScene(ActionEvent actionEvent) throws IOException {

        ipAddress = ipAddressText.getText();
        port = portText.getText();
        nickname = nicknameText.getText();
        System.out.println("IP : "+ipAddress);
        System.out.println("PORT : "+port);
        System.out.println("NICKNAME : "+nickname);
        //fare controllo lato server

        //if dati inseriti validi carica nuova schermata
        FXMLLoader loader = new FXMLLoader(new File("src/main/java/it/polimi/ingsw/client/view/GUI/files/modality.fxml").toURI().toURL());
        root = loader.load();

        stage = (Stage) (connectButton).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
