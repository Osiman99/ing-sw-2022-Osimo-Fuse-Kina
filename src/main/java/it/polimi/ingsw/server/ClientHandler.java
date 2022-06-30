package it.polimi.ingsw.server;

import it.polimi.ingsw.network.messages.Message;

/**
 * Interface to handle clients. Every type of connection must implement this interface.
 */
public interface ClientHandler {

    /**
     *  Returns the connection status
     * @return true if the client is still connected and reachable.
     */
    boolean isConnected();

    /**
     * Disconnects from the client.
     */
    void disconnect();

    /**
     * Sends a message to the client.
     *
     * @param message the message to be sent.
     */
    void sendMessage(Message message);
}
