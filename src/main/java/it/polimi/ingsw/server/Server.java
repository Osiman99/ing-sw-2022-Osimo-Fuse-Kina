package it.polimi.ingsw.server;

import it.polimi.ingsw.server.model.Game;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Server {

    ServerSocket serverSocket;
    private Random random;
    private int randomInt;
    private static Server instance;
    private static Game game;
    private static Thread start;
    private static int numberOfSockets;
    private static boolean closeServer = false;
    ArrayList<ClientHandler> connections = new ArrayList<>();
    private List<Lobby> lobbies;

    public static void main(String[] args) {
        new Server();

    }

    public Server(){
        try{
            System.out.println("Internal ip: " + InetAddress.getLocalHost());
        }catch (IOException e){

        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Server port?");
        int socketPort = Integer.parseInt(scanner.nextLine());
        ServerSocket socket;
        try {
            socket = new ServerSocket(socketPort);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }
        while (!closeServer) {
            try {
                Socket client = socket.accept();
                //numberOfSockets++;
                ClientHandler clientHandler = new ClientHandler(client, this);  //oppure al posto di this numberOfSockets
                connections.add(clientHandler);
                Thread thread1 = new Thread(clientHandler, "client_" + client.getInetAddress());
                thread1.start();
                connections.add(clientHandler);
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
        lobbies = new ArrayList<Lobby>();
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