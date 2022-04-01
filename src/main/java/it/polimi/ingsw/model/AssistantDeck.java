package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class AssistantDeck {

    /**
     * list of cards
     */
    private List<AssistantCard> deck;

    /**
     * deck constructor
     */
    public AssistantDeck() {
        deck = new ArrayList<AssistantCard>();

        /**
         * add Assistant cards to deck
         */
        for(int i = 0; i < 10; i++) {
            deck.add(new AssistantCard(i + 1, i / 2 + 1));
        }

    }

    /**
     * Getter
     *
     * @return deck of assistant cards
     */
    public List<AssistantCard> getDeck() {
        return deck;
    }

}
