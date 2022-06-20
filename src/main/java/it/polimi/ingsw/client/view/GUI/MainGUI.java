package it.polimi.ingsw.client.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class MainGUI extends Application {
    private Scene scene;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = new File("src/main/java/it/polimi/ingsw/client/view/GUI/files/entry.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        root.setId("entry");

        scene = new Scene(root);

        scene.getStylesheets().add(new File("src/main/java/it/polimi/ingsw/client/view/GUI/files/style.css").toURI().toURL().toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
