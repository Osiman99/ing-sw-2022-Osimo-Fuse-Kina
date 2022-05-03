package it.polimi.ingsw.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client{

    ServerHandler serverHandler;

    public static void main(String[] args) {
        new Client();
    }

    public Client(){
        try {
            Socket socket = new Socket("127.0.0.1", 10000);
            serverHandler = new ServerHandler(socket, this);
            serverHandler.start();
            listenForInput();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
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

            if (input.toLowerCase().equals("quit")){
                break;
            }
            serverHandler.sendStringToServer(input);
        }
        serverHandler.close();
    }
}