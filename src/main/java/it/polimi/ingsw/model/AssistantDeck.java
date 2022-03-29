package it.polimi.ingsw.model;

import java.util.Arrays;
import java.util.List;

public class AssistantDeck {

    /**
     * list of cards
     */
    private List<AssistantCard> list;

    /**
     * deck constructor
     */
    public AssistantDeck() {

        AssistantCard[] deck = new AssistantCard[10];

        /**
         * add Assistant cards to deck
         */
       for(int i = 0; i < deck.length; i++){
           deck[i] = new AssistantCard(i + 1, i/2 + 1);
       }

        /**
         * fills the list from array
         */
        list =Arrays.asList(deck);
    }

    //public AssistantCard playCard(){...}
}
