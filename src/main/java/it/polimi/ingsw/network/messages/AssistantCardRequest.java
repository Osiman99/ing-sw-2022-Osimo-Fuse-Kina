package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.server.model.AssistantCard;
import it.polimi.ingsw.server.model.Game;

import java.util.ArrayList;
import java.util.List;

public class AssistantCardRequest extends Message{
    private static final long serialVersionUID = 3050646401315283892L;
    private final List<AssistantCard> deck;
    //
    //

    public AssistantCardRequest(List<AssistantCard> deck) {
        super(Game.SERVER_NICKNAME, MessageType.ASSISTANT_CARD);
        this.deck = deck;
    }

    public List<AssistantCard> getDeck() {
        return deck;
    }

    public String toString(){
        return "Assistant Card List: " + deck;
    }

}
