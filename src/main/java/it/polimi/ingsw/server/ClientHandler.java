package it.polimi.ingsw.server;

import it.polimi.ingsw.network.messages.Message;

public interface ClientHandler {


    boolean isConnected();


    void disconnect();


    void sendMessage(Message message);
}
