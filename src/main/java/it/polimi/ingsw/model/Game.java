package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int MAX_PLAYERS = 3;
    private Board board;              //forse va fatto final (singleton o no?)
    private static Game instance;
    private List<Player> players;
    private int chosenPlayersNumber;

    /**
     * Game constructor
     */
    public Game(){
        players = new ArrayList<Player>();
        this.board = Board.getInstance();
    }

    /**
     * singleton class, create object only if it doesn't exist
     * @return
     */
    public static Game getInstance(){
        if (instance == null){
            instance = new Game();
        }
        return instance;
    }

    /**
     * getter
     * @return
     */
    public int getNumPlayers() {
        return players.size();   //oppure return chosenPlayersNumber (in base a se vogliamo
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

    public void addPlayer(Player player){
        players.add(player);
    }

    public Board getBoard() {
        return board;
    }
}
