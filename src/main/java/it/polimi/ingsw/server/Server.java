package it.polimi.ingsw.server;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.network.messages.Message;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class Server {

    private static Server instance;
    private final GameController gameController;

    private final Map<String, ClientHandler> clientHandlerMap;

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private final Object lock;

    public Server(GameController gameController) {
        Server.instance = this;
        this.gameController = gameController;
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.lock = new Object();
    }



    public void addClient(String nickname, ClientHandler clientHandler) {
        VirtualView vv = new VirtualView(clientHandler);

        if (!gameController.isGameStarted()) {
            if (gameController.checkLoginNickname(nickname, vv)) {
                clientHandlerMap.put(nickname, clientHandler);
                gameController.loginHandler(nickname, vv);
            }
        } else {
            //vv.showLoginResult(true, false, null);
            clientHandler.disconnect();
        }

    }

    public static Server getInstance() {
        return instance;
    }

    /**
     * Removes a client given his nickname.
     *
     * @param nickname      the VirtualView to be removed.
     * @param notifyEnabled set to {@code true} to enable a lobby disconnection message, {@code false} otherwise.
     */
    public void removeClient(String nickname, boolean notifyEnabled) {
        clientHandlerMap.remove(nickname);
        gameController.removeVirtualView(nickname, notifyEnabled);
        LOGGER.info(() -> "Removed " + nickname + " from the client list.");
    }

    /**
     * Forwards a received message from the client to the GameController.
     *
     * @param message the message to be forwarded.
     */
    public void onMessageReceived(Message message) {
        gameController.switchState(message);
    }

    /**
     * Handles the disconnection of a client.
     *
     * @param clientHandler the client disconnecting.
     */
    public void onDisconnect(ClientHandler clientHandler) {
        synchronized (lock) {
            String nickname = getNicknameFromClientHandler(clientHandler);

            if (nickname != null) {

                boolean gameStarted = gameController.isGameStarted();
                removeClient(nickname, !gameStarted); // enable lobby notifications only if the game didn't start yet.

                if(gameController.getNicknames() != null &&
                        !gameController.getNicknames().contains(nickname)) {
                    return;
                }

                // Resets server status only if the game was already started.
                // Otherwise the server will wait for a new player to connect.
                /*if (gameStarted) {
                    gameController.broadcastDisconnectionMessage(nickname, " disconnected from the server. GAME ENDED.");
                    gameController.endGame();
                    clientHandlerMap.clear();
                }*/
            }
        }
    }


    public Map<String, ClientHandler> getClientHandlerMap() {
        return clientHandlerMap;
    }

    /**
     * Returns the corresponding nickname of a ClientHandler.
     *
     * @param clientHandler the client handler.
     * @return the corresponding nickname of a ClientHandler.
     */
    private String getNicknameFromClientHandler(ClientHandler clientHandler) {
        return clientHandlerMap.entrySet()
                .stream()
                .filter(entry -> clientHandler.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}