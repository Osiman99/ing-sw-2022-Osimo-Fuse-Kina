package it.polimi.ingsw.server;

import it.polimi.ingsw.client.view.CLI.ANSIColor;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * it's a room where the players are put before a match
 */
public class Lobby {
    private int ID;
    private int numPlayers;
    private int realTimeNumPlayer;
    private List<Player> players;
    private boolean full;
    private String mode;
    private GameController gameController;
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public Lobby(GameController gameController){
        players = new ArrayList<Player>();
        numPlayers = 0;
        realTimeNumPlayer = 0;
        this.gameController = gameController;
    }

    public void addPlayer(String nickname){
        players.add(new Player(nickname));
        realTimeNumPlayer++;
        checkFull();
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void checkFull(){
        if(realTimeNumPlayer == numPlayers){
            setFull(true);
        }
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isFull() {
        return full;
    }


    public void setFull(boolean full) {
        this.full = full;
    }

    public boolean checkStart(){
        return numPlayers <= realTimeNumPlayer;
    }

    public boolean isNicknameTaken(String nickname) {
        return players.stream()
                .anyMatch(p -> nickname.equals(p.getNickname().toUpperCase(Locale.ROOT)));
    }

    public void deleteExtraPlayers(){
        if(realTimeNumPlayer > numPlayers) {
            for (int i = 0; i < realTimeNumPlayer - numPlayers; i++) {
                gameController.getVirtualViewMap().get(players.get(numPlayers).getNickname()).showGenericMessage(ANSIColor.RED + "Max players reached. Connection refused." + ANSIColor.RESET);
                gameController.getVirtualViewMap().get(players.get(numPlayers).getNickname()).showDisconnectionMessage(players.get(numPlayers).getNickname(), " disconnected from the Server.");
                gameController.getServer().getClientHandlerMap().remove(players.get(numPlayers).getNickname());
                gameController.getVirtualViewMap().remove(players.get(numPlayers).getNickname());
                LOGGER.info(() -> "Removed " + players.get(numPlayers).getNickname() + " from the client list.");
                players.remove(numPlayers);
            }
        }
    }

}
