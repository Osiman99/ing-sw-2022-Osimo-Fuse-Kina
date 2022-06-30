package it.polimi.ingsw.network;

/**
 * Message used to send to the server the Assistant Card chosen by the client.
 */
public class AssistantCardResult extends Message{

    private static final long serialVersionUID = 3072974604481308602L;
    private final int card;


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
