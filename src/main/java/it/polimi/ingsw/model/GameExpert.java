package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * in this class we have to use the character cards
 */
public class GameExpert extends Game{

    private List<CharacterCard> threeChosenCards;
    private Random random;
    private int randomInt;


    @Override
    public void initGame(Game game, List<String> nicknames, int chosenPlayersNumber) {
        super.initGame(game, nicknames, chosenPlayersNumber);
        threeChosenCards = new ArrayList<CharacterCard>();
        random = new Random();
        for (int i = 0; i < 3; i++) {
            randomInt = random.nextInt(11);
            switch (randomInt) {
                case 0:
                    threeChosenCards.add(new CharacterCard(CharacterName.Sommelier));
                    break;
                case 1:
                    threeChosenCards.add(new CharacterCard(CharacterName.Chef));
                    break;
                case 2:
                    threeChosenCards.add(new CharacterCard(CharacterName.Messenger));
                    break;
                case 3:
                    threeChosenCards.add(new CharacterCard(CharacterName.Postman));
                    break;
                case 4:
                    threeChosenCards.add(new CharacterCard(CharacterName.Herbalist));
                    break;
                case 5:
                    threeChosenCards.add(new CharacterCard(CharacterName.Centaur));
                    break;
                case 6:
                    threeChosenCards.add(new CharacterCard(CharacterName.Joker));
                    break;
                case 7:
                    threeChosenCards.add(new CharacterCard(CharacterName.Knight));
                    break;
                case 8:
                    threeChosenCards.add(new CharacterCard(CharacterName.Merchant));
                    break;
                case 9:
                    threeChosenCards.add(new CharacterCard(CharacterName.Musician));
                    break;
                case 10:
                    threeChosenCards.add(new CharacterCard(CharacterName.Lady));
                    break;
                case 11:
                    threeChosenCards.add(new CharacterCard(CharacterName.Sinister));
                    break;
            }
        }
    }
}
