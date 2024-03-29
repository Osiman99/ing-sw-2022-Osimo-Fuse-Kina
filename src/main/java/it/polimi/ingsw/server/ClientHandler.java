package it.polimi.ingsw.server;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.MessageType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket client;
    private final SocketServer socketServer;

    private boolean connected;

    private final Object inputLock;
    private final Object outputLock;

    private ObjectOutputStream output;
    private ObjectInputStream input;


    public ClientHandler(SocketServer socketServer, Socket client) {
        this.socketServer = socketServer;
        this.client = client;
        connected = true;

        inputLock = new Object();
        outputLock = new Object();

        try {
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            Server.LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            messageManager();
        } catch (IOException e) {
            Server.LOGGER.severe("Client " + client.getInetAddress() + " connection dropped.");
            disconnect();
        }
    }


    private void messageManager() throws IOException {
        Server.LOGGER.info("Client connected from " + client.getInetAddress());
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (inputLock) {
                    Message message = (Message) input.readObject();

                    if (message != null && message.getMessageType() != MessageType.PING) {
                        if (message.getMessageType() == MessageType.LOGIN_REQUEST) {
                            socketServer.addClient(message.getNickname(), this);
                        } else {
                            Server.LOGGER.info(() -> "Received: " + message);
                            socketServer.onMessageReceived(message);
                        }
                    }
                }
            }
        } catch (ClassCastException | ClassNotFoundException e) {
            Server.LOGGER.severe("Invalid stream from client");
        }
        client.close();
    }


    public void disconnect() {
        if (connected) {
            try {
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                Server.LOGGER.severe(e.getMessage());
            }
            connected = false;
            Thread.currentThread().interrupt();

            socketServer.onDisconnect(this);
        }
    }


    public void sendMessage(Message message) {
        try {
            synchronized (outputLock) {
                output.writeObject(message);
                output.reset();
                Server.LOGGER.info(() -> "Sent: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Server.LOGGER.severe(e.getMessage());
            disconnect();
        }
    }
}