package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    ServerSocket serverSocket;
    ArrayList<ClientHandler> connections = new ArrayList<ClientHandler>();
    boolean shouldRun = true;

    public static void main(String[] args) {
        new Server();
    }

    public Server(){
        try {
            serverSocket = new ServerSocket(5000);
            while (shouldRun) {
                Socket socket = serverSocket.accept();
                ClientHandler serverConnection = new ClientHandler(socket, this);
                serverConnection.start();
                connections.add(serverConnection);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}