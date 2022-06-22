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
                randomInt = random.nextInt(7);
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
                    threeChosenCards.add(new CharacterCard(CharacterName.Knight));
                    break;
                case 7:
                    threeChosenCards.add(new CharacterCard(CharacterName.Lady));
                    break;
            }
            randomInt = random.nextInt(7);
            while (tempRandomInt1 == randomInt || tempRandomInt2 == randomInt) {
                randomInt = random.nextInt(7);
            }
        }fillCcDescription();
    }

    public void fillCcDescription(){
        for (CharacterCard cc : threeChosenCards) {
            switch (cc.getCharacterName()) {
                case Sommelier:
                    cc.setDescription("SOMMELIER\n" +
                            "Take 1 Student from this card and place it on\n" +
                            "an lsland of your choice. Then, draw a new Student\n" +
                            "from the Bag and place it on this card.");
                    break;
                case Chef:
                    cc.setDescription("CHEF\n" +
                            "During this turn, you take control of any\n" +
                            "number of Professors even if you have the same\n" +
                            "number of Students as the player who currently\n" +
                            "controls them.");
                    break;
                case Messenger:
                    cc.setDescription("MESSENGER\n" +
                            "Choose an lsland and resolve the lsland as if\n" +
                            "Mother Nature had ended her movement there. Mother\n" +
                            "Nature will still move and the lsland where she ends\n" +
                            "her movement will also be resolved.");
                    break;
                case Postman:
                    cc.setDescription("POSTMAN\n" +
                            "You may move Mother Nature up to 2\n" +
                            "additional lslands than is indicated by the Assistant\n" +
                            "card you've played.");
                    break;
                case Herbalist:
                    cc.setDescription("HERBALIST\n" +
                            "Place a No Entry tile on an lsland of your choice.\n" +
                            "The first time Mother Nature ends her movement\n" +
                            "there, put the No Entry tile back onto this card DO NOT\n" +
                            "calculate influence on thai lsland, or place any Towers.");
                    break;
                case Centaur:
                    cc.setDescription("CENTAUR\n" +
                            "When resolving a Conquering on an lsland,\n" +
                            "Towers do not count towards influence.");
                    break;
                case Joker:
                    cc.setDescription("JOKER\n" +
                            "You may take up to 3 Students from this card\n" +
                            "and replace them with the same number of Students\n" +
                            "from your Entrance.");
                    break;
                case Knight:
                    cc.setDescription("KNIGHT\n" +
                            "During the influence calculation this turn,\n" +
                            "you count as having 2 more influence.");
                    break;
                case Merchant:
                    cc.setDescription("MERCHANT\n" +
                            "Choose a color of Student: during the influence\n" +
                            "calculation this turn, that color adds no influence.");
                    break;
                case Musician:
                    cc.setDescription("MUSICIAN\n" +
                            "You may exchange up to 2 Students between\n" +
                            "your Entrance and your Dining Room.");
                    break;
                case Lady:
                    cc.setDescription("LADY\n" +
                            "Take 1 Student from this card and place it\n" +
                            "in your Dining Room. Then, draw a new Student\n" +
                            "from the Bag and place it on this card .");
                    break;
                case Sinister:
                    cc.setDescription("SINISTER\n" +
                            "Choose a type of Student: every player\n" +
                            "(including yourself) must return 3 Students\n" +
                            "of that type from their Dining Room to the bag.\n" +
                            "If any player has fewer than 3 Students of that type,\n" +
                            "return as many Students as they have.");
                    break;
            }
        }
    }

    public List<CharacterCard> getThreeChosenCards() {
        return threeChosenCards;
    }

}
