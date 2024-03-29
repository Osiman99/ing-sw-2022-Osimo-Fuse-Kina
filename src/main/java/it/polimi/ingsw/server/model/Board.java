package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.BoardMessage;
import it.polimi.ingsw.observer.Observable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board extends Observable implements Serializable {

    private static final long serialVersionUID = -4480043050522641306L;
    private Game game;
    private List<Island> islands;
    private List<Cloud> clouds;
    private String professorsControlledBy[];
    private Bag bag;
    private Random random;
    private int randomInt;
    private final int NUM_ISLAND_INIT = 12;
    private ArrayList<Student> studentsIslandInit;
    private int greenCont;
    private int redCont;
    private int yellowCont;
    private int pinkCont;
    private int blueCont;
    private int contJoin;
    private boolean cardActivated;

    /**
     * initialize the game board
     */
    public void initBoard(){
        bag = new Bag();
        game = Game.getInstance();
        professorsControlledBy = new String[5];
        for (int i = 0; i < 5; i++) {
            professorsControlledBy[i] = "";
        }

        cardActivated = false;
        greenCont = 0;
        redCont = 0;
        yellowCont = 0;
        pinkCont = 0;
        blueCont = 0;
        contJoin = 0;

        /**
         * creation of the 12 islands
         */
        islands = new ArrayList<Island>();
        for (int i = 0; i < NUM_ISLAND_INIT; i++) {
            islands.add(new Island());
        }

        /**
         * in the beginning of the game we have 2 students of each color in the islands
         */
        studentsIslandInit = new ArrayList<Student>();
        for (int i = 0; i < 2; i++) {
            studentsIslandInit.add(new Student(StudentColor.GREEN));
        }
        for (int i = 0; i < 2; i++) {
            studentsIslandInit.add(new Student(StudentColor.RED));
        }
        for (int i = 0; i < 2; i++) {
            studentsIslandInit.add(new Student(StudentColor.YELLOW));
        }
        for (int i = 0; i < 2; i++) {
            studentsIslandInit.add(new Student(StudentColor.PINK));
        }
        for (int i = 0; i < 2; i++) {
            studentsIslandInit.add(new Student(StudentColor.BLUE));
        }

        /**
         * we put the 10 students in a random mode in the islands except the island in which is put motherNature
         * and the opposite island in which is motherNature
         */
        Collections.shuffle(studentsIslandInit);
        random = new Random();
        randomInt = random.nextInt(NUM_ISLAND_INIT-1);
        islands.get(randomInt).setMotherNature(true);
        for (int i = 0; i < NUM_ISLAND_INIT; i++){
            if (!(islands.get(i) == islands.get(randomInt) || islands.get(i) == islands.get((randomInt+6)%12))){
                islands.get(i).addStudent(studentsIslandInit.get(0));
                studentsIslandInit.remove(0);
            }
        }

        /**
         * we create the same number of clouds as there are players in the game(2 or 3)
         */
        clouds = new ArrayList<Cloud>();
        for (int i = 0; i < game.getNumPlayers(); i++) {
                clouds.add(new Cloud());
        }

    }

    public Bag getBag() {
        return bag;
    }

    public List<Cloud> getClouds() {
        return clouds;
    }

    public List<Island> getIslands() {
        return islands;
    }


    public void setProfessorsControlledBy(String[] nicknames) {
        System.arraycopy(nicknames, 0, professorsControlledBy, 0, 5);
    }

    public String[] getProfessorsControlledBy() {
        return professorsControlledBy;
    }

    public void setGameInstance(Game instance) {
        game = instance;
    }

    public Game getGameInstance() {
        return game;
    }


    /**
     * if there are 2 players Cloud gets 3 students from the Bag each round,
     * if there are 3 players it gets 4.
     */
    public void moveStudentsFromBagToClouds() {
        for (int i = 0; i < game.getNumPlayers(); i++) {
                for (int j = 0; j < game.getNumPlayers()+1; j++) {
                    if (!(getBag().getStudents().isEmpty())) {
                        clouds.get(i).addStudent(bag.getFirstStudent());
                        bag.removeStudent();
                    }else{
                        getBag().setBagEmpty(true);
                        return;
                    }
                }
        }
        notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));

    }


    /**
     * when we add the tower to the island, at the same time we remove the tower from the towerSpace.
     * when the towerSpace remains empty the player owning that plank wins the game.
     *
     * @param player the player who has the tower moving.
     * @param island the island where the tower is being placed
     */
    public void moveTowerFromPlankToIsland(Player player, Island island){
        if(!player.getPlank().getTowerSpace().getTowersList().isEmpty()) {
            island.addTower(player.getPlank().getTowerSpace().getFirstTower());
            player.getPlank().getTowerSpace().removeTower();
        }
    }

    /**
     * if one player conquers an island(group of islands) that before was conquered by another player,
     * the ex-conqueror of the island gets in its plank all the towers belonging to him.
     *
     * @param island
     */
    public void moveTowerFromIslandToPlank(Island island){      //la for sarà nel conquerIsland
        for (int i = 0; i < game.getNumPlayers(); i++){
            if (island.getFirstTower().getColor() == game.getPlayers().get(i).getPlayerColor()){
                game.getPlayers().get(i).getPlank().getTowerSpace().addTower(island.getFirstTower());
                island.removeTower();
                break;
            }
        }
    }

    /**
     * the method that allows the mother nature to move.
     * after the move is done the supremacy is calculated in the island in which mother nature is found.
     *
     * @param numMoves number of moves mother nature should do
     */
    public void moveMotherNature(int numMoves){
        for (int i = 0; i < islands.size(); i++){
            if (islands.get(i).isMotherNature()){
                islands.get(i).setMotherNature(false);
                islands.get((i+numMoves)%islands.size()).setMotherNature(true);
                if (!(islands.get((i+numMoves)%islands.size()).isBanCard())){
                    calculateSupremacy(islands.get((i+numMoves)%islands.size()));
                }else{
                    islands.get((i+numMoves)%islands.size()).setBanCard(false);
                    GameExpert gameExpert = (GameExpert) game;
                    for(CharacterCard characterCard : gameExpert.getThreeChosenCards()) {
                        if (characterCard.getCharacterName() == CharacterName.Herbalist && characterCard.isEnabled()) {
                            for (int j = 0; j < characterCard.getBanCards().length; j++) {
                                if (!characterCard.getBanCards()[j]) {
                                    characterCard.getBanCards()[j] = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
        notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));
    }

    /**
     * moving the professor in case of an expert game by using "the Chef" character card.
     */
    public void moveProfessor(Player player){
        if(game instanceof GameExpert){
            GameExpert gameExpert = (GameExpert) game;
            for(CharacterCard characterCard : gameExpert.getThreeChosenCards()) {
                if (characterCard.getCharacterName() == CharacterName.Chef && characterCard.isEnabled()){
                    System.out.println("ciao");
                    moveProfessorChef(player);
                    break;
                }if(gameExpert.getThreeChosenCards().get(gameExpert.getThreeChosenCards().size()-1) == characterCard){
                    moveProfessorNormal(player);
                }
            }
        }else {
            moveProfessorNormal(player);
        }
    }

    /**
     * moving the professor when a player moves one of his students into his dining room.
     *
     * @param player is the active player
     */
    private void moveProfessorNormal(Player player) {
        int numPlayer= game.getNumPlayers();
        for(int i=0; i<numPlayer; i++) {
            if(game.getPlayers().get(i)==player) {
                for(int j=0; j<5; j++) {
                    if(player.getPlank().getDiningRoom()[j].getStudents().size()>game.getPlayers().get((i+1)%numPlayer).getPlank().getDiningRoom()[j].getStudents().size()){
                        if(numPlayer==3){
                            if(player.getPlank().getDiningRoom()[j].getStudents().size()>game.getPlayers().get((i+2)%numPlayer).getPlank().getDiningRoom()[j].getStudents().size()){
                                professorsControlledBy[j]= player.getNickname();
                            }
                        }
                        else {
                            professorsControlledBy[j]= player.getNickname();
                        }
                    }
                }
                break;
            }
        }
    }

    /**
     * moving the professor in case Chef card effect is applied.
     *
     * @param player is the active player
     */
    public void moveProfessorChef(Player player){
        int numPlayer= game.getNumPlayers();
        for(int i=0; i<numPlayer; i++) {
            if(game.getPlayers().get(i)==player) {
                for(int j=0; j<5; j++) {
                    if(player.getPlank().getDiningRoom()[j].getStudents().size()>=game.getPlayers().get((i+1)%numPlayer).getPlank().getDiningRoom()[j].getStudents().size()){
                        if(numPlayer==3){
                            if(player.getPlank().getDiningRoom()[j].getStudents().size()>=game.getPlayers().get((i+2)%numPlayer).getPlank().getDiningRoom()[j].getStudents().size()){
                                professorsControlledBy[j]= player.getNickname();
                            }
                        }
                        else {
                            professorsControlledBy[j]= player.getNickname();
                        }
                    }
                }
                break;
            }
        }
    }

    /**
     * calculate the supremacy.
     *
     * @param island is the island with mother nature onto.
     */
    public void calculateSupremacy(Island island){
        if (island.getStudents().size() != 0){
            for (int i = 0; i < island.getStudents().size(); i++) {
                if (island.getStudents().get(i).getColor() == StudentColor.GREEN){
                    greenCont++;
                }else if (island.getStudents().get(i).getColor() == StudentColor.RED){
                    redCont++;
                }else if (island.getStudents().get(i).getColor() == StudentColor.YELLOW) {
                    yellowCont++;
                }else if (island.getStudents().get(i).getColor() == StudentColor.PINK) {
                    pinkCont++;
                }else if (island.getStudents().get(i).getColor() == StudentColor.BLUE) {
                    blueCont++;
                }
            }for (int i = 0; i < 5; i++){
                for (int j = 0; j < game.getNumPlayers(); j++){
                    if (professorsControlledBy[i].equals(game.getPlayers().get(j).getNickname())){
                        if (i == 0){
                            game.getPlayers().get(j).setSupremacyCont(game.getPlayers().get(j).getSupremacyCont() + greenCont);
                        }else if (i == 1){
                            game.getPlayers().get(j).setSupremacyCont(game.getPlayers().get(j).getSupremacyCont() + redCont);
                        }else if (i == 2){
                            game.getPlayers().get(j).setSupremacyCont(game.getPlayers().get(j).getSupremacyCont() + yellowCont);
                        }else if (i == 3) {
                            game.getPlayers().get(j).setSupremacyCont(game.getPlayers().get(j).getSupremacyCont() + pinkCont);
                        }else {
                            game.getPlayers().get(j).setSupremacyCont(game.getPlayers().get(j).getSupremacyCont() + blueCont);
                        }
                    }
                }
            } if (game instanceof GameExpert) {
                GameExpert gameExpert = (GameExpert) game;
                for (CharacterCard characterCard : gameExpert.getThreeChosenCards()) {
                    if (characterCard.getCharacterName() == CharacterName.Centaur && characterCard.isEnabled()) {
                        characterCard.setEnabled(false);
                        break;
                    }if(gameExpert.getThreeChosenCards().get(gameExpert.getThreeChosenCards().size()-1) == characterCard){
                        towerCont(island);
                    }
                }
            }else{
                towerCont(island);
            }conquerIsland(island);
        }
    }

    /**
     * count the tower in the supremacy calculation.
     *
     * @param island the island where the towers are in.
     */
    private void towerCont(Island island) {
        if (island.getTowers().size() != 0) {
            for (int i = 0; i < island.getTowers().size(); i++) {
                for (int j = 0; j < game.getNumPlayers(); j++) {
                    if (island.getFirstTower().getColor() == game.getPlayers().get(j).getPlayerColor()) {
                        game.getPlayers().get(j).setSupremacyCont(game.getPlayers().get(j).getSupremacyCont() + 1);
                    }
                }
            }
        }
    }

    /**
     * if one player has the highest supremacy in the island in which mother nature stops,
     * he conquers the island by putting his tower in it.
     *
     * @param island the island to be conquered
     */
    public void conquerIsland(Island island){
        if (island.getTowers().size() != 0){
            if (game.getNumPlayers() == 2){
                for (int i = 0; i < 2; i++){
                    if (game.getPlayers().get(i).getPlayerColor() == island.getFirstTower().getColor()){
                        if (game.getPlayers().get(i).getSupremacyCont() < game.getPlayers().get((i+1)%2).getSupremacyCont()){
                            int numTowers = island.getTowers().size();
                            for (int j = 0; j < numTowers; j++){
                                moveTowerFromIslandToPlank(island);
                                moveTowerFromPlankToIsland(game.getPlayers().get((i+1)%2), island);
                            }joinIslands(island);
                        }
                    }
                }
            }else if (game.getNumPlayers() == 3) {
                for (int i = 0; i < 3; i++){
                    if (game.getPlayers().get(i).getPlayerColor() == island.getFirstTower().getColor()){
                        if (game.getPlayers().get((i+1)%3).getSupremacyCont() > game.getPlayers().get(i).getSupremacyCont() && game.getPlayers().get((i+1)%3).getSupremacyCont() > game.getPlayers().get((i+2)%3).getSupremacyCont()){
                            int numTowers = island.getTowers().size();
                            for (int j = 0; j < numTowers; j++){
                                moveTowerFromIslandToPlank(island);
                                moveTowerFromPlankToIsland(game.getPlayers().get((i+1)%3), island);
                            }joinIslands(island);
                        }else if (game.getPlayers().get((i+2)%3).getSupremacyCont() > game.getPlayers().get(i).getSupremacyCont() && game.getPlayers().get((i+2)%3).getSupremacyCont() > game.getPlayers().get((i+1)%3).getSupremacyCont()){
                            int numTowers = island.getTowers().size();
                            for (int j = 0; j < numTowers; j++){
                                moveTowerFromIslandToPlank(island);
                                moveTowerFromPlankToIsland(game.getPlayers().get((i+2)%3), island);
                            }joinIslands(island);
                        }
                    }
                }
            }
        }else{
            if (game.getNumPlayers() == 2){
                if (game.getPlayers().get(0).getSupremacyCont() > game.getPlayers().get(1).getSupremacyCont()){
                    moveTowerFromPlankToIsland(game.getPlayers().get(0), island);
                    joinIslands(island);
                }else if (game.getPlayers().get(0).getSupremacyCont() < game.getPlayers().get(1).getSupremacyCont()){
                    moveTowerFromPlankToIsland(game.getPlayers().get(1), island);
                    joinIslands(island);
                }
            }else if (game.getNumPlayers() == 3) {
                if (game.getPlayers().get(0).getSupremacyCont() > game.getPlayers().get(1).getSupremacyCont() && game.getPlayers().get(0).getSupremacyCont() > game.getPlayers().get(2).getSupremacyCont()){
                    moveTowerFromPlankToIsland(game.getPlayers().get(0), island);
                    joinIslands(island);
                }else if (game.getPlayers().get(1).getSupremacyCont() > game.getPlayers().get(0).getSupremacyCont() && game.getPlayers().get(1).getSupremacyCont() > game.getPlayers().get(2).getSupremacyCont()){
                    moveTowerFromPlankToIsland(game.getPlayers().get(1), island);
                    joinIslands(island);
                }else if (game.getPlayers().get(2).getSupremacyCont() > game.getPlayers().get(0).getSupremacyCont() && game.getPlayers().get(2).getSupremacyCont() > game.getPlayers().get(1).getSupremacyCont()){
                    moveTowerFromPlankToIsland(game.getPlayers().get(2), island);
                    joinIslands(island);
                }
            }
        } greenCont = 0;
        redCont = 0;
        yellowCont = 0;
        pinkCont = 0;
        blueCont = 0;
        for (int i = 0; i < game.getNumPlayers(); i++) {
            game.getPlayers().get(i).setSupremacyCont(0);
        }
    }

    /**
     * if there are 2 or more islands near each other and those islands are conquered by the same player,
     * then the islands are unified.
     * @param island
     */
    public void joinIslands(Island island){
        Island i1 = null;
        Island i2 = null;
        int numIsland = islands.size();
        for (int i = 0; i < numIsland; i++){
            if (islands.get(i) == island) {
                if (!(islands.get((i+1)%islands.size()).getTowers().size() == 0 || islands.get((i+1)%islands.size()).getFirstTower().getColor() != island.getFirstTower().getColor())) {
                    int numTowersNextIsland = islands.get((i+1)%islands.size()).getTowers().size();
                    for (int j = 0; j < numTowersNextIsland; j++){
                        island.addTower(islands.get((i+1)%islands.size()).getFirstTower());
                        islands.get((i+1)%islands.size()).removeTower();
                    }int numStudentsNextIsland = islands.get((i+1)%islands.size()).getStudents().size();
                    for (int j = 0; j < numStudentsNextIsland; j++){
                        island.addStudent(islands.get((i+1)%islands.size()).getFirstStudent());
                        islands.get((i+1)%islands.size()).removeStudent();
                    }
                    if (game instanceof GameExpert) {
                        GameExpert gameExpert = (GameExpert) game;
                        for (CharacterCard characterCard : gameExpert.getThreeChosenCards()) {
                            if (characterCard.getCharacterName() == CharacterName.Messenger && characterCard.isEnabled()) {
                                contJoin++;
                            }
                        }
                    }
                    i1 = islands.get((i+1)%islands.size());
                }if ((i-1) == -1) {
                    if (!(islands.get(islands.size()-1).getTowers().size() == 0  || islands.get(islands.size()-1).getFirstTower().getColor() != island.getFirstTower().getColor())) {
                        int numTowersLastIsland = islands.get(islands.size()-1).getTowers().size();
                        for (int j = 0; j < numTowersLastIsland; j++) {
                            island.addTower(islands.get(islands.size()-1).getFirstTower());
                            islands.get(islands.size()-1).removeTower();
                        }int numStudentsLastIsland = islands.get(islands.size()-1).getStudents().size();
                        for (int j = 0; j < numStudentsLastIsland; j++) {
                            island.addStudent(islands.get(islands.size()-1).getFirstStudent());
                            islands.get(islands.size()-1).removeStudent();
                        }
                        if (game instanceof GameExpert) {
                            GameExpert gameExpert = (GameExpert) game;
                            for (CharacterCard characterCard : gameExpert.getThreeChosenCards()) {
                                if (characterCard.getCharacterName() == CharacterName.Messenger && characterCard.isEnabled()) {
                                    contJoin++;
                                }
                            }
                        }
                        i2 = islands.get(islands.size()-1);
                    }
                }else {
                    if (!(islands.get(i-1).getTowers().size() == 0  || islands.get(i-1).getFirstTower().getColor() != island.getFirstTower().getColor())) {
                        int numTowersPrevIsland = islands.get(i-1).getTowers().size();
                        for (int j = 0; j < numTowersPrevIsland; j++) {
                            island.addTower(islands.get(i-1).getFirstTower());
                            islands.get(i-1).removeTower();
                        }int numStudentsPrevIsland = islands.get(i-1).getStudents().size();
                        for (int j = 0; j < numStudentsPrevIsland; j++) {
                            island.addStudent(islands.get(i-1).getFirstStudent());
                            islands.get(i-1).removeStudent();
                        }
                        if (game instanceof GameExpert) {
                            GameExpert gameExpert = (GameExpert) game;
                            for (CharacterCard characterCard : gameExpert.getThreeChosenCards()) {
                                if (characterCard.getCharacterName() == CharacterName.Messenger && characterCard.isEnabled()) {
                                    contJoin++;
                                }
                            }
                        }
                        i2 = islands.get(i-1);
                    }
                }if (i1 != null){
                    islands.remove(i1);
                }if (i2 != null){
                    islands.remove(i2);
                }
                break;
            }
        }
    }

    /**
     * returns true if the character card is activated.
     */
    public boolean isCardActivated() {
        return cardActivated;
    }

    public void setCardActivated(boolean cardActivated) {
        this.cardActivated = cardActivated;
    }

    public boolean areIslandsLessThanThree(){
        if(islands.size() <= 3){
            notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));
            return true;
        }
        return false;
    }

    /**
     * returns true if the bag remains empty and notifies the observer.
     */
    public boolean isBagEmptyGC(){
        if(bag.isBagEmpty()){
            notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));
            return true;
        }
        return false;
    }

    /**
     * In setup, draw 4 students and place them on this card.
     * When this card effect is applied you can take 1 student from this card and place it on an island of your choice.
     * Then, draw a student from the bag and place it on this card. The price after this card is used is increased by 1.
     *
     * @param player who activates the card
     * @param characterCard Sommelier
     * @param studentColor
     * @param numIsland
     */
    public void applyEffectSommelier(Player player, CharacterCard characterCard, StudentColor studentColor, int numIsland){
        characterCard.setEnabled(true);
        cardActivated = true;
        for (int i = 0; i < 4; i++){
            if (characterCard.getStudents().get(i).getColor() == studentColor){
                islands.get(numIsland-1).addStudent(characterCard.getStudents().get(i));
                characterCard.getStudents().remove(characterCard.getStudents().get(i));
                if(!(getBag().isBagEmpty())) {
                    characterCard.getStudents().add(game.getBoard().getBag().getFirstStudent());
                    game.getBoard().getBag().removeStudent();
                }
                break;
            }
        }
        player.setNumCoins(player.getNumCoins() - characterCard.getPrice());
        characterCard.setPrice(characterCard.getPrice() + 1);
        characterCard.setEnabled(false);
        notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));
    }

    /**
     * after this card is used its price is increased by 1.
     *
     * @param player who activates this card
     * @param characterCard Chef
     */
    public void applyEffectChef(Player player, CharacterCard characterCard) {
        characterCard.setEnabled(true);
        cardActivated = true;
        player.setNumCoins(player.getNumCoins() - characterCard.getPrice());
        characterCard.setPrice(characterCard.getPrice() + 1);
        moveProfessorChef(player);
        notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));
    }

    /**
     * The effect of this card is that during the turn this card is activated the player chooses one island of his
     * choice and calculates the supremacy as if mother nature had ended her movement there.
     * Mother Nature will still move during that turn and the island where she ends her movement will still be resolved.
     * The card price is increased by 1 after its effect is used.
     *
     * @param player who activates this card
     * @param characterCard Messenger
     * @param numIsland is the number of island the player puts the mother nature
     */
    public void applyEffectMessenger(Player player, CharacterCard characterCard, int numIsland){
        characterCard.setEnabled(true);
        cardActivated = true;
        int prevIsland = 0;
        player.setNumCoins(player.getNumCoins() - characterCard.getPrice());
        characterCard.setPrice(characterCard.getPrice() + 1);
        if (!(islands.get(numIsland-1).isBanCard())){
            for (int i = 0; i < islands.size(); i++) {
                if (islands.get(i).isMotherNature()) {
                    prevIsland = i;
                    islands.get(i).setMotherNature(false);
                    islands.get(numIsland - 1).setMotherNature(true);
                    break;
                }
            }
            calculateSupremacy(islands.get(numIsland-1));
            if((prevIsland+1) > numIsland){
                for (int i = 0; i < islands.size(); i++) {
                    if (islands.get(i).isMotherNature()) {
                        islands.get(i).setMotherNature(false);
                    }
                }islands.get(prevIsland-contJoin).setMotherNature(true);
            }else{
                for (int i = 0; i < islands.size(); i++) {
                    if (islands.get(i).isMotherNature()) {
                        islands.get(i).setMotherNature(false);
                    }
                }islands.get(prevIsland).setMotherNature(true);
            }
        }else{
            islands.get(numIsland-1).setBanCard(false);
        }
        characterCard.setEnabled(false);
        notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));
    }

    /**
     * The card price is increased by 1 after its effect is used.
     *
     * @param player who activates this card effect
     * @param characterCard Postman
     */
    public void applyEffectPostman(Player player, CharacterCard characterCard){
        characterCard.setEnabled(true);
        cardActivated = true;
        player.setNumCoins(player.getNumCoins() - characterCard.getPrice());
        characterCard.setPrice(characterCard.getPrice() + 1);
        notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));
    }

    /**
     * This character card allows the use of the BanCards.
     * The card price is increased by 1 after its effect is used.
     *
     * @param player who activates this card effect
     * @param characterCard Herbalist
     * @param numIsland is the island number where the player puts the ban card
     */
    public void applyEffectHerbalist(Player player, CharacterCard characterCard, int numIsland){
        characterCard.setEnabled(true);
        cardActivated = true;
        player.setNumCoins(player.getNumCoins() - characterCard.getPrice());
        characterCard.setPrice(characterCard.getPrice() + 1);
        getIslands().get(numIsland-1).setBanCard(true);

        for (int i = characterCard.getBanCards().length-1; i >= 0; i--) {
            if (characterCard.getBanCards()[i]) {
                characterCard.getBanCards()[i] = false;
                break;
            }
        }
        characterCard.setEnabled(false);
        notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));
    }

    /**
     * The card price is increased by 1 after its effect is used.
     *
     * @param player who activates this card effect
     * @param characterCard Centaur
     */
    public void applyEffectCentaur(Player player, CharacterCard characterCard){
        characterCard.setEnabled(true);
        cardActivated = true;
        player.setNumCoins(player.getNumCoins() - characterCard.getPrice());
        characterCard.setPrice(characterCard.getPrice() + 1);
        notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));
    }

    /**
     * The card price is increased by 1 after its effect is used.
     *
     * @param player who activates this card effect
     * @param characterCard Knight
     */
    public void applyEffectKnight(Player player, CharacterCard characterCard){
        characterCard.setEnabled(true);
        cardActivated = true;
        player.setNumCoins(player.getNumCoins() - characterCard.getPrice());
        characterCard.setPrice(characterCard.getPrice() + 1);
        notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));
    }

    /**
     * In setup, draw 4 students and place them on this card.
     * This card's effect permits the player who activated it to take one student from this card and place it on his
     * dining room. Then, draw a student from the bag and place it on this card.
     * The card price is increased by 1 after its effect is used.
     *
     * @param player who activates this card effect
     * @param characterCard Lady
     * @param studentColor the student color
     */
    public void applyEffectLady(Player player, CharacterCard characterCard, StudentColor studentColor){
        characterCard.setEnabled(true);
        cardActivated = true;
        for (int i = 0; i < 4; i++){
            if (characterCard.getStudents().get(i).getColor() == studentColor){
                player.getPlank().getDiningRoom()[studentColor.getCode()].addStudent(characterCard.getStudents().get(i));
                characterCard.getStudents().remove(characterCard.getStudents().get(i));
                if(!(getBag().isBagEmpty())) {
                    characterCard.getStudents().add(game.getBoard().getBag().getFirstStudent());
                    game.getBoard().getBag().removeStudent();
                }
                break;
            }
        }
        player.setNumCoins(player.getNumCoins() - characterCard.getPrice());
        characterCard.setPrice(characterCard.getPrice() + 1);
        characterCard.setEnabled(false);
        notifyObserver(new BoardMessage(Game.SERVER_NICKNAME, game));
    }
}