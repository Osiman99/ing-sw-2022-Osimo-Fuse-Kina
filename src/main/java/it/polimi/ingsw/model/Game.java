package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int MAX_PLAYERS = 3;
    private Board board;              //forse va fatto final (singleton o no?)
    private static Game instance;   //classe Game, per non farla singleton basta non mettere static in instance (e chiamare il setInstance per ogni game creato nel main)
    private List<Player> players;
    private int chosenPlayersNumber; //servirebbe nel caso un player si scollega dal gioco (FA)

    /**
     * Game constructor
     */
    public Game(List<String> nicknames){
        players = new ArrayList<Player>();
        for (int i = 0; i < chosenPlayersNumber; i++){
            players.add(new Player(nicknames.get(i)));
        }
        board = Board.getInstance();
    }

    /**
     * singleton class, create object only if it doesn't exist
     * @return
     */

    public static void setInstance(Game game){  //nel main devo evocare questo metodo
        instance = game;
    }

    public static Game getInstance(){
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
