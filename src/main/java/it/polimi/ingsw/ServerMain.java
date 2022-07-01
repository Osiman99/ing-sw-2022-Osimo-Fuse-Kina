package it.polimi.ingsw;

import it.polimi.ingsw.server.GameController;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.SocketServer;

/**
 * This class creates a GameController, a Server, a SocketServer and a thread which runs the socketServer class
 */
public class ServerMain {
    public static void main(String[] args) {
        int serverPort = 12500;

        GameController gameController = new GameController();
        Server server = new Server(gameController);

        SocketServer socketServer = new SocketServer(server, serverPort);
        Thread thread = new Thread(socketServer, "socketserver_");
        thread.start();
    }
}
