package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssistantDeck {

    /**
     * list of cards
     */
    private List<AssistantCard> deck = new ArrayList<AssistantCard>();

    /**
     * deck constructor
     */
    public AssistantDeck() {

        /**
         * add Assistant cards to deck
         */
       for(int i = 0; i < 10; i++) {
           deck.add(new AssistantCard(i + 1, i / 2 + 1));
       }
    }

    //public AssistantCard playCard(){...}
}
