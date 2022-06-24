package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.messages.BoardMessage;
import it.polimi.ingsw.network.messages.LobbyMessage;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.GameState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game extends Observable implements Serializable {

    private static final long serialVersionUID = 4004020661063976635L;

    public static final int MAX_PLAYERS = 3;
    private Board board;              //forse va fatto final (singleton o no?)
    private static Game instance;   //classe Game, per non farla singleton basta non mettere static in instance (e chiamare il setInstance per ogni game creato nel main)
    private List<Player> players;
    private int chosenPlayersNumber;       //servirebbe nel caso un player si scollega dal gioco (FA)
    private int contPlayer;
    public static final String SERVER_NICKNAME = "server";
    public List<String> nicknames;
    public GameState state;

    /**
     * Game constructor
     */
    public Game(){
        instance = this;
        this.chosenPlayersNumber = chosenPlayersNumber;
        contPlayer = 0;
        players = new ArrayList<Player>();
        nicknames = new ArrayList<String>();
        board = new Board();
    }


    /**
     * initializes the game(the board & the players)
     */
    public void initGame(){
        board.initBoard();
        for (int i = 0; i < chosenPlayersNumber; i++) {
            players.get(i).initPlayer();
            if (chosenPlayersNumber == 2) {
                if (i == 0){
                    for (int j = 0; j < 8; j++){
                        players.get(i).getPlank().getTowerSpace().addTower(new Tower(TowerColor.BLACK));
                    }
                }else if (i == 1){
                    for (int j = 0; j < 8; j++){
                        players.get(i).getPlank().getTowerSpace().addTower(new Tower(TowerColor.WHITE));
                    }
                }
            }else if (chosenPlayersNumber == 3){
                if (i == 0) {
                    for (int j = 0; j < 6; j++){
                        players.get(i).getPlank().getTowerSpace().addTower(new Tower(TowerColor.BLACK));
                    }
                }else if (i == 1){
                    for (int j = 0; j < 6; j++){
                        players.get(i).getPlank().getTowerSpace().addTower(new Tower(TowerColor.WHITE));
                    }
                }else if (i == 2){
                    for (int j = 0; j < 6; j++){
                        players.get(i).getPlank().getTowerSpace().addTower(new Tower(TowerColor.GREY));
                    }
                }
            }
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


    /**
     * controls if the nickname is occupied
     * @param nickname
     * @return
     */
    public boolean isNicknameTaken(String nickname) {
        return players.stream()
                .anyMatch(p -> nickname.equals(p.getNickname()));
    }

    /**
     * in case the player disconnects
     * @param nickname
     * @param notifyEnabled
     * @return
     */
    public boolean removePlayerByNickname(String nickname, boolean notifyEnabled) {
        boolean result = players.remove(getPlayerByNickname(nickname));

        if (notifyEnabled) {
            notifyObserver(new LobbyMessage(getNicknames(), this.chosenPlayersNumber));
        }
        return result;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
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

    public int getChosenPlayerNumber() {return chosenPlayersNumber;}

    public void notifyBoard(){
        notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, this));
    }
}