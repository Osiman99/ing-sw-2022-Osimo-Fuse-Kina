package it.polimi.ingsw.network;

import it.polimi.ingsw.server.model.AssistantCard;
import java.util.ArrayList;
import java.util.List;

/**
 * Message used to ask the client to play the Assistant Card.
 */
public class AssistantCardRequest extends Message{

    private static final long serialVersionUID = 3050646401315283892L;
    private final List<AssistantCard> deck;
    private List<String> cardValueList;



    public AssistantCardRequest(String nickname, List<AssistantCard> deck) {
        super(nickname, MessageType.ASSISTANTCARD_REQUEST);
        this.deck = deck;
        cardValueList = new ArrayList<String>();
        for (int i = 0; i < deck.size(); i++)
            cardValueList.add(Integer.toString(deck.get(i).getValue()));
    }

    public List<AssistantCard> getDeck() {
        return deck;
    }

    public String toString(){
        return "Assistant Card List: " + cardValueList;
    }

}
