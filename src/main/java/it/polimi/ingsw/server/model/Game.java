package it.polimi.ingsw.server.model;

import it.polimi.ingsw.observer.Observable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game extends Observable {
    public static final int MAX_PLAYERS = 3;
    private Board board;              //forse va fatto final (singleton o no?)
    private static Game instance;   //classe Game, per non farla singleton basta non mettere static in instance (e chiamare il setInstance per ogni game creato nel main)
    private List<Player> players;
    private int chosenPlayersNumber;       //servirebbe nel caso un player si scollega dal gioco (FA)
    private int contPlayer;
    public static final String SERVER_NICKNAME = "server";



    /**
     * initialization of the game, choosing the number of players and getting their nicknames
     * @param game
     * @param nicknames
     * @param chosenPlayersNumber
     */
    public void initGame(Game game, List<String> nicknames, int chosenPlayersNumber){
        setInstance(game);
        this.chosenPlayersNumber = chosenPlayersNumber;
        contPlayer = 0;
        board = Board.getInstance();
        players = new ArrayList<Player>();
        for (int i = 0; i < this.chosenPlayersNumber; i++){
            contPlayer = contPlayer + 1;
            players.add(new Player(nicknames.get(i)));
            if (i == 0) {
                players.get(i).setPlayerColor(TowerColor.BLACK);
            }else if (i == 1){
                players.get(i).setPlayerColor(TowerColor.WHITE);
            }else if (i == 2){
                players.get(i).setPlayerColor(TowerColor.GREY);
            }
        }
    }

    public static void setInstance(Game game){  //nel main devo evocare questo metodo
        instance = game;
    }

    public static Game getInstance(){
        return instance;
    }

    public int getContPlayer() {
        return contPlayer;
    }

    /**
     * getter
     * @return
     */



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


    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
