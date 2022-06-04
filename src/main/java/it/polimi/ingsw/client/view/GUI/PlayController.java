package it.polimi.ingsw.client.view.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class PlayController {

    @FXML
    private HBox charactersBox;
    @FXML
    private ImageView card1;
    @FXML
    private ImageView card2;
    @FXML
    private ImageView card3;

    @FXML
    public void initialize(){
        /*
        card1.setVisible(false);
        card2.setVisible(false);
        card3.setVisible(false);
        card1.setDisable(true);
        card2.setDisable(true);
        card3.setDisable(true);
        charactersBox.setManaged(false);

        modalità normal
         */


        System.out.println("init");
    }



    public void play(ActionEvent actionEvent){


    }
}
