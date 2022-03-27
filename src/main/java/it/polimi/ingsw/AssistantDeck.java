package it.polimi.ingsw;

import java.util.ArrayList;

public class AssistantDeck {

    private AssistantCard[] deck;

    /**
     * deck constructor
     */
    public AssistantDeck() {
       int[] value = {1,2,3,4,5,6,7,8,9,10};
       int[] maxValue = {1,2,3,4,5};

       deck = new AssistantCard[10];

        /**
         * add Assistant cards to deck
         */
       for(int i = 0; i < deck.length; i++){
           deck[i] = new AssistantCard(value[i + 1], maxValue[i/2 + 1]);
       }
    }
}
