package it.polimi.ingsw.network.messages;

public class CharacterCardsDescriptionReply extends Message{
    private static final long serialVersionUID = -9072253637635095715L;
    private final String[] text;
    //
    //

    public CharacterCardsDescriptionReply(String nickname, String[] text) {
        super(nickname, MessageType.CHARACTERCARDS_DESCRIPTION_REPLY);
        this.text = text;
    }

    public String[] getText() {
        return text;
    }

    public String toString(){
        return "CharacterCards description sent to the player";
    }
}
