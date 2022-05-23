package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.messages.LobbyMessage;
import it.polimi.ingsw.observer.Observable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game extends Observable implements Serializable {
    public static final int MAX_PLAYERS = 3;
    private Board board;              //forse va fatto final (singleton o no?)
    private static Game instance;   //classe Game, per non farla singleton basta non mettere static in instance (e chiamare il setInstance per ogni game creato nel main)
    private List<Player> players;
    private int chosenPlayersNumber;       //servirebbe nel caso un player si scollega dal gioco (FA)
    private int contPlayer;
    public static final String SERVER_NICKNAME = "server";
    public List<String> nicknames;


    public Game(){
        this.chosenPlayersNumber = chosenPlayersNumber;
        contPlayer = 0;
        players = new ArrayList<Player>();
        nicknames = new ArrayList<String>();
        board = Board.getInstance();
    }

    public void initGame(){
        board.initBoard();
        for (int i = 0; i < chosenPlayersNumber; i++) {
            players.get(i).initPlayer();
        }
    }



    public static void setInstance(Game game){  //nel main devo evocare questo metodo
        instance = game;
    }


    public static Game getInstance(){
        if (instance == null){
            instance = new Game();
        }
        return instance;
    }

    public int getContPlayer() {
        return contPlayer;
    }

    /**
     * getter
     * @return
     */


    public void addPlayer(Player player){
        players.add(player);
        contPlayer++;
        nicknames.add(player.getNickname());
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public int getNumPlayers() {
        return chosenPlayersNumber;   //oppure return chosenPlayersNumber (in base a se vogliamo
                                 // fare la FA se uno si scollega dal gioco)
    }

    /**
     * it searches the player with this nickname
     * @param nickname
     * @return Player
     */
    public Player getPlayerByNickname(String nickname) {
        return players.stream()
                .filter(player -> nickname.equals(player.getNickname()))
                .findFirst()
                .orElse(null);
    }


    public boolean isNicknameTaken(String nickname) {
        return players.stream()
                .anyMatch(p -> nickname.equals(p.getNickname()));
    }

    public boolean removePlayerByNickname(String nickname, boolean notifyEnabled) {
        boolean result = players.remove(getPlayerByNickname(nickname));

        if (notifyEnabled) {
            notifyObserver(new LobbyMessage(getNicknames(), this.chosenPlayersNumber));
        }
        return result;
    }


    public static void resetInstance(){
        instance = null;
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setChosenPlayersNumber(int chosenPlayersNumber) {
        this.chosenPlayersNumber = chosenPlayersNumber;
    }
}
