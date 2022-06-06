package it.polimi.ingsw.client.view.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;



public class PlayController {

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
    public void initialize(){

        /* 2 players

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
        plankBox.setManaged(false);    */

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



    public void play(ActionEvent actionEvent){


    }
}
