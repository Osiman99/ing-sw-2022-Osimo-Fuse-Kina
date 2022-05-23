package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.server.model.AssistantCard;
import it.polimi.ingsw.server.model.Game;

import java.util.List;

public class AssistantCardResult extends Message{
    private static final long serialVersionUID = 3072974604481308602L;
    private final int card;
    //
    //

    public AssistantCardResult(String nickname, int card) {
        super(nickname, MessageType.ASSISTANTCARD_RESULT);
        this.card = card;
    }

    public int getCard() {
        return card;
    }

    public String toString(){
        return "Chosen Assistant Card: " + card;
    }
}
