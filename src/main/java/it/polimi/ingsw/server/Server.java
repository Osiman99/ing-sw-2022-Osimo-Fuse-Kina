package it.polimi.ingsw.server;

import it.polimi.ingsw.server.model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server {

    ServerSocket serverSocket;
    private Random random;
    private int randomInt;
    private static Server instance;
    private static Game game;
    private static Thread start;
    private static int numberOfSockets;
    ArrayList<ClientHandler> connections = new ArrayList<>();
    boolean shouldRun = true;
    private List<Lobby> lobbies;

    public static void main(String[] args) {
        new Server();

    }

    public Server(){
        try {
            serverSocket = new ServerSocket(10000);
            random = new Random();
            setInstance(this);
            while (shouldRun) {
                Socket client = serverSocket.accept();
                //numberOfSockets++;
                ClientHandler serverConnection = new ClientHandler(client, this);  //oppure al posto di this numberOfSockets
                serverConnection.run();
                connections.add(serverConnection);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }lobbies = new ArrayList<Lobby>();
        random = new Random();
    }

    /*public static void runGame()
    {
        start = new Thread(game);
        start.start();
    }

    public static void runGameExpert(){
        start = new Thread(game);
        start.start();
    }*/

    public static void setInstance(Server server){
        instance = server;
    }

    public static Server getInstance(){
        return instance;
    }

    public List<Lobby> getLobbies() {
        return lobbies;
    }

    /*public Lobby getRandomLobby(int numPlayers){
        randomInt = random.nextInt(lobbies.size());
        while(lobbies.get(randomInt).getNumPlayers()
    }*/
}