package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AssistantDeck implements Serializable {

    private static final long serialVersionUID = 6475314869046793590L;
    /**
     * list of cards
     */
    private List<AssistantCard> deck;

    /**
     * deck constructor
     */
    public AssistantDeck() {
        deck = new ArrayList<AssistantCard>();
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
