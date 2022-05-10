package it.polimi.ingsw.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client{

    ServerHandler serverHandler;

    public static void main(String[] args) {
        new Client();
    }

    public Client(){
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("IP address of server?");
            String ip = scanner.nextLine();
            System.out.println("Server port?");
            int socketPort = Integer.parseInt(scanner.nextLine());
            Socket server = new Socket(ip, socketPort);
            serverHandler = new ServerHandler(server, this);
            Thread thread2 = new Thread(serverHandler, "server_" + server.getInetAddress());
            thread2.start();

            //listenForInput();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /*public void listenForInput(){

        Scanner console = new Scanner(System.in);

        while (true){
            while(!console.hasNextLine()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            String input = console.nextLine();

            if (Objects.equals(input.toLowerCase(), "quit")){
                break;
            }
            serverHandler.sendStringToServer(input);
        }
        serverHandler.closeSocket();
    }*/
}