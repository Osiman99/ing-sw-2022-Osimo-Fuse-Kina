package it.polimi.ingsw.network;

/**
 * The client requests che available character card effect descriptions.
 */
public class CharacterCardsDescriptionRequest extends Message{

    private static final long serialVersionUID = 4492879405668852154L;
    private final String askInterrupted;

    public CharacterCardsDescriptionRequest(String nickname, String askInterrupted) {
        super(nickname, MessageType.CHARACTERCARDS_DESCRIPTION_REQUEST);
        this.askInterrupted = askInterrupted;
    }

    public String getAskInterrupted() {
        return askInterrupted;
    }

    public String toString(){
        return "CharacterCards description request";
    }
}
