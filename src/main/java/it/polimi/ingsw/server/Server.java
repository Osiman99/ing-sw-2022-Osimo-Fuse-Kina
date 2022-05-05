package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    ServerSocket serverSocket;
    private static int numberOfSockets;
    ArrayList<ClientHandler> connections = new ArrayList<>();
    boolean shouldRun = true;

    public static void main(String[] args) {
        new Server();
    }

    public Server(){
        try {
            serverSocket = new ServerSocket(10000);
            while (shouldRun) {
                Socket client = serverSocket.accept();
                //numberOfSockets++;
                ClientHandler serverConnection = new ClientHandler(client, this);  //oppure al posto di this numberOfSockets
                serverConnection.run();
                connections.add(serverConnection);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void runGame(){

    }

}