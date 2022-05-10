package it.polimi.ingsw.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class ServerHandler implements Runnable{

    Socket server;
    Client client;
    ObjectInputStream input;
    ObjectOutputStream output;
    boolean shouldRun = true;

    public ServerHandler(Socket server, Client client){
        this.client = client;
        this.server = server;
    }

    /*public void sendStringToServer(String text){
        try {
            output.writeObject(text);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
            //this.close();
        }
    }*/

    @Override
    public void run() {
        try {
            input = new ObjectInputStream(server.getInputStream());
            output = new ObjectOutputStream(server.getOutputStream());

            /*while (shouldRun) {
                try {
                    while (dataInputStream.available() == 0) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    String reply = dataInputStream.readUTF();
                    System.out.println(reply);
                } catch (IOException e) {
                    e.printStackTrace();
                    close();
                }
            }*/
            this.handleServerConnection();

        } catch (IOException e) {
            System.out.println("could not open connection to " + server.getInetAddress());
            return;
        }
    }

    public void closeSocket() throws IOException{
        try{
            input.close();
            output.close();
        }catch (IOException e){}
    }

    public void sendMessage(Object msg) throws IOException{
        try{
            output.writeObject(msg);
            output.flush();
            output.reset();
        }catch (IOException e){
            System.out.println("Error while writing " + msg.getClass() + " type message to " + this.server.getInetAddress() + " so the game ends.");
            //KeepAlive.run(false);
        }
    }

    public String receiveMessage() throws IOException, ClassNotFoundException{
        try{
            String next = (String) input.readObject();
            return next;
        }catch (IOException e){
            System.out.println("Error while reading from " + this.server.getInetAddress() + " so the game ends.");
            this.closeSocket();
            //KeepAlive.run(false);
        }
        return "";
    }

    public void handleServerConnection() throws IOException {
        try {
            String next;
            String next2;
            do {
                next = this.receiveMessage();
                Scanner scanner = new Scanner(System.in);
                String nickname = scanner.nextLine();
                this.sendMessage(nickname);
            } while (next.equals(""));
        } catch (ClassCastException | ClassNotFoundException e) {
            System.out.println("invalid stream from server");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}