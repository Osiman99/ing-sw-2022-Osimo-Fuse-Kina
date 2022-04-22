package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * in this class we have to use the character cards
 */
public class GameExpert extends Game{

    private List<CharacterCards> threeChosenCards;
    private CharacterCards sommelier;
    private CharacterCards chef;
    private CharacterCards messenger;
    private CharacterCards postman;
    private CharacterCards herbalist;
    private CharacterCards centaur;
    private CharacterCards joker;
    private CharacterCards knight;
    private CharacterCards merchant;
    private CharacterCards musician;
    private CharacterCards lady;
    private CharacterCards sinister;
    private Random random;
    private int randomInt;


    @Override
    public void initGame(Game game, List<String> nicknames, int chosenPlayersNumber) {
        super.initGame(game, nicknames, chosenPlayersNumber);
        threeChosenCards = new ArrayList<CharacterCards>();
        random = new Random();
        for (int i = 0; i < 3; i++) {
            randomInt = random.nextInt(11);
            switch (randomInt) {
                case 0:
                    sommelier = Sommelier.getInstance();
                    threeChosenCards.add(sommelier);
                    break;
                case 1:
                    chef = new Chef();
                    threeChosenCards.add(chef);
                    break;
                case 2:
                    messenger = new Messenger();
                    threeChosenCards.add(messenger);
                    break;
                case 3:
                    postman = new Postman();
                    threeChosenCards.add(postman);
                    break;
                case 4:
                    herbalist = new Herbalist();
                    threeChosenCards.add(herbalist);
                    break;
                case 5:
                    centaur = new Centaur();
                    threeChosenCards.add(centaur);
                    break;
                case 6:
                    joker = new Joker();
                    threeChosenCards.add(joker);
                    break;
                case 7:
                    knight = new Knight();
                    threeChosenCards.add(knight);
                    break;
                case 8:
                    merchant = new Merchant();
                    threeChosenCards.add(merchant);
                    break;
                case 9:
                    musician = new Musician();
                    threeChosenCards.add(musician);
                    break;
                case 10:
                    lady = new Lady();
                    threeChosenCards.add(lady);
                    break;
                case 11:
                    sinister = new Sinister();
                    threeChosenCards.add(sinister);
                    break;
            }
        }
    }
}
