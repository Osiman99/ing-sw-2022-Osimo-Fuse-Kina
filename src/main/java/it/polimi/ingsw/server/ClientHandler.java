package it.polimi.ingsw.server;

//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import it.polimi.ingsw.server.KeepAlive;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{

    Socket client;
    Server server;
    ObjectInputStream input;
    ObjectOutputStream output;
    //boolean shouldRun = true;

    public ClientHandler(Socket client, Server server){
        //super("ServerConnectionThread");
        this.client = client;
        this.server = server;
    }

    /*public void sendStringToClient(String text){
        try {
            dataOutputStream.writeUTF(text);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /*public void sendStringToAllClients(String text){
        for (int i = 0; i < server.connections.size(); i++){
            ClientHandler clientHandler = server.connections.get(i);
            clientHandler.sendStringToClient(text);
        }
    }*/

    @Override
    public void run() {
        try {
            input = new ObjectInputStream(client.getInputStream());
            output = new ObjectOutputStream(client.getOutputStream());

            /*while(shouldRun){
                while(dataInputStream.available() == 0){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String textIn = dataInputStream.readUTF();
                sendStringToAllClients(textIn);

            }*/

        } catch (IOException e) {
            System.out.println("could not open connection to " + client.getInetAddress());;
            return;
            //e.printStackTrace();
        }
        System.out.println("Connected to: " + client.getInetAddress() + " at port #" + client.getLocalPort());
        try{
            handleClientConnection();
        }catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        }


    }

    public void handleClientConnection() throws IOException{
        try {
            String next;
            do {
                this.sendMessage("What's your name?");
                next = this.receiveMessage();
            }while(next.equals(""));





        }catch (ClassCastException | ClassNotFoundException e) {
            System.out.println("invalid stream from client");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isClose(){
        if(client.isClosed()) return true;
        return false;
    }


    public void closeSocket() throws IOException{
        try{
            input.close();
            output.close();
            client.close();
        }catch (IOException e){}
    }

    public void sendMessage(Object msg) throws IOException{
        try{
            output.writeObject(msg);
            output.flush();
            output.reset();
        }catch (IOException e){
            System.out.println("Error while writing " + msg.getClass() + " type message to " + this.client.getInetAddress() + " so the game ends.");
            //KeepAlive.run(false);
        }
    }

    public String receiveMessage() throws IOException, ClassNotFoundException{
        try{
            String next = (String) input.readObject();
            return next;
        }catch (IOException e){
            System.out.println("Error while reading from " + this.client.getInetAddress() + " so the game ends.");
            this.closeSocket();
            //KeepAlive.run(false);
        }
        return "";
    }


}