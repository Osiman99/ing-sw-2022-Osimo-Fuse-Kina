package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * in this class we have to use the character cards
 */
public class GameExpert extends Game implements Serializable {

    private static final long serialVersionUID = 3589190030673960796L;
    private List<CharacterCard> threeChosenCards;
    private Random random;
    private int randomInt;
    private int tempRandomInt1;
    private int tempRandomInt2;


    public GameExpert() {
        super();
        tempRandomInt1 = -1;
        tempRandomInt2 = -1;
        threeChosenCards = new ArrayList<CharacterCard>();
        random = new Random();
    }

    public void initGameExpert(){
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                randomInt = random.nextInt(11);
                tempRandomInt2 = randomInt;
            } else if (i == 1) {
                tempRandomInt1 = randomInt;
            }

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
            randomInt = random.nextInt(11);
            while (tempRandomInt1 == randomInt || tempRandomInt2 == randomInt) {
                randomInt = random.nextInt(11);
            }
        }
    }

    public List<CharacterCard> getThreeChosenCards() {
        return threeChosenCards;
    }

}
