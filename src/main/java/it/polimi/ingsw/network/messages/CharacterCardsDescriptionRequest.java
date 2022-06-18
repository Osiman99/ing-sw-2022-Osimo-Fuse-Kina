package it.polimi.ingsw.network.messages;

public class CharacterCardsDescriptionRequest extends Message{
    private static final long serialVersionUID = -4600228149696912393L;
    private final String cc;
    //
    //

    public CharacterCardsDescriptionRequest(String nickname, String cc) {
        super(nickname, MessageType.CHARACTERCARDS_DESCRIPTION_REQUEST);
        this.cc = cc;
    }

    public String getCc() {
        return cc;
    }

    public String toString(){
        return "CharacterCards description request";
    }
}
