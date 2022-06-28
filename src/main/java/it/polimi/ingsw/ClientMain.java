package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.view.CLI.EriantysCLI;
import java.util.logging.Level;

/**
 * This class creates the client, so it creates the Cli of the game, the ClientController and adds the clientcontroller
 * as an observer to the view. Then it runs the Cli with the welcome method.
 */
public class ClientMain {

    public static void main(String[] args) {

        Client.LOGGER.setLevel(Level.WARNING);
        EriantysCLI view = new EriantysCLI();
        ClientController clientcontroller = new ClientController(view);
        view.addObserver(clientcontroller);
        view.welcome();
    }
}