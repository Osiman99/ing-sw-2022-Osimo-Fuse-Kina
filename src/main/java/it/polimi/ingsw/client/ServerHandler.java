package it.polimi.ingsw.client;

import java.io.*;
import java.net.Socket;


public class ServerHandler implements Runnable{

    Client client;
    Socket server;
    ObjectInputStream input;
    ObjectOutputStream output;
    boolean shouldRun = true;

    public ServerHandler(Socket server, Client client){
        this.client = client;
        this.server = server;
    }

    /*public void sendStringToServer(String text){
        try {
            dataOutputStream.writeUTF(text);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            close();
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
        } catch (IOException e) {
            e.printStackTrace();
            closeSocket();
        }
    }

    public void closeSocket() throws IOException{
        try{
            input.close();
            output.close();
            client.close();
        }catch (IOException e){}
    }
}