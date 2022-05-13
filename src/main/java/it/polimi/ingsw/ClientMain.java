package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.view.CLI.EriantysCLI;
import it.polimi.ingsw.client.view.GUI.TestGui;
import javafx.application.Application;

import java.util.logging.Level;

public class ClientMain {

    public static void main(String[] args) {

        boolean cliParam = false; // default value

        for (String arg : args) {
            if (arg.equals("--cli") || arg.equals("-c")) {
                cliParam = true;
                break;
            }
        }

        if (cliParam) {
            Client.LOGGER.setLevel(Level.WARNING);
            EriantysCLI view = new EriantysCLI();
            ClientController clientcontroller = new ClientController(view);
            view.addObserver(clientcontroller);
            view.welcome();
        } else {
            Application.launch(TestGui.class);
        }
    }
}