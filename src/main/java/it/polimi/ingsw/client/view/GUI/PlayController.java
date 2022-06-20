package it.polimi.ingsw.client.view.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class PlayController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private HBox charactersBox;
    @FXML
    private HBox plankBox;
    @FXML
    private ImageView card1;
    @FXML
    private ImageView card2;
    @FXML
    private ImageView card3;

    @FXML
    private Label nomeTerzo;
    @FXML
    private Label numeroMoneteTerzo;
    @FXML
    private ImageView coinTerzo;
    @FXML
    private ImageView assistantTerzo;
    @FXML
    private ImageView retroAssistantTerzo;
    @FXML
    private ImageView plankTerzo;
    @FXML
    private ImageView assistantOne;

    @FXML
    public void initialize() {



        coinTerzo.setVisible(false);
        assistantTerzo.setVisible(false);
        retroAssistantTerzo.setVisible(false);
        plankTerzo.setVisible(false);
        numeroMoneteTerzo.setVisible(false);
        nomeTerzo.setVisible(false);
        coinTerzo.setDisable(true);
        assistantTerzo.setDisable(true);
        retroAssistantTerzo.setDisable(true);
        plankTerzo.setDisable(true);
        numeroMoneteTerzo.setDisable(true);
        nomeTerzo.setDisable(true);
        plankBox.setManaged(false);

        /* normal mode

        card1.setVisible(false);
        card2.setVisible(false);
        card3.setVisible(false);
        card1.setDisable(true);
        card2.setDisable(true);
        card3.setDisable(true);
        charactersBox.setManaged(false);   */


        //coins1.setText("X");


        System.out.println("init");
    }


    public void play(ActionEvent actionEvent) {


    }

    public void deck(MouseEvent mouseEvent) throws IOException {
/*
        FXMLLoader loader = new FXMLLoader(new File("src/main/java/it/polimi/ingsw/client/view/GUI/files/deck.fxml").toURI().toURL());
        root = loader.load();
        root.setId("connection");

        stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        scene.getStylesheets().add(new File("src/main/java/it/polimi/ingsw/client/view/GUI/files/style.css").toURI().toURL().toExternalForm());
        stage.setScene(scene);
        stage.show();

*/
    }

}
