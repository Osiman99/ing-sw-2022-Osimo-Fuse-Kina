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
            Socket server = new Socket("127.0.0.1", 10000);
            serverHandler = new ServerHandler(server, this);
            serverHandler.run();
            listenForInput();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void listenForInput(){

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
        serverHandler.close();
    }
}