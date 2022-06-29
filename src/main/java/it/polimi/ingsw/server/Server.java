package it.polimi.ingsw.server;

import it.polimi.ingsw.network.messages.Message;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class Server {

    private static Server instance;
    private final GameController gameController;
    private boolean flag;

    private final Map<String, ClientHandler> clientHandlerMap;

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private final Object lock;

    public Server(GameController gameController) {
        Server.instance = this;
        this.gameController = gameController;
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.lock = new Object();
        flag = false;
    }



    public void addClient(String nickname, ClientHandler clientHandler) {
        VirtualView vv = new VirtualView(clientHandler);

        if (gameController.checkLoginNickname(nickname, vv)) {
            clientHandlerMap.put(nickname, clientHandler);
            gameController.loginHandler(nickname, vv);
        }
    }

    public static Server getInstance() {
        return instance;
    }

    /**
     * Removes a client given his nickname.
     *
     * @param nickname      the VirtualView to be removed.
     */
    public void removeClient(String nickname) {

        clientHandlerMap.remove(nickname);
        gameController.removeVirtualView(nickname);
        /*if (!flag) {
            for (int i = 0; i < gameController.getLobby().getPlayers().size(); i++) {
                if (gameController.getLobby().getPlayers().get(i).getNickname().equals(nickname)) {
                    gameController.getLobby().getPlayers().remove(i);
                    flag = true;
                    break;
                }
            }
        }
        if (flag) {
            gameController.initGameController();
        }*/

        if(gameController.getState() != GameState.ENDGAME) {
            gameController.quitFromServer(nickname);
        }

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

                removeClient(nickname);

                if(gameController.getNicknames() != null &&
                        !gameController.getNicknames().contains(nickname)) {
                    return;
                }
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