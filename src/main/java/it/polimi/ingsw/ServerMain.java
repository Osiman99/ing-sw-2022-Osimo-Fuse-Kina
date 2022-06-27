package it.polimi.ingsw;

import it.polimi.ingsw.server.GameController;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.SocketServer;

/**
 * This class creates a GameController, a Server, a SocketServer and a thread which runs the socketServer class
 */
public class ServerMain {
    public static void main(String[] args) {
        int serverPort = 12500; // default value

        for (int i = 0; i < args.length; i++) {
            if (args.length >= 2 && (args[i].equals("--port") || args[i].equals("-p"))) {
                try {
                    serverPort = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    Server.LOGGER.warning("Invalid port specified. Using default port.");
                }
            }
        }

        GameController gameController = new GameController();
        Server server = new Server(gameController);

        SocketServer socketServer = new SocketServer(server, serverPort);
        Thread thread = new Thread(socketServer, "socketserver_");
        thread.start();
    }
}
