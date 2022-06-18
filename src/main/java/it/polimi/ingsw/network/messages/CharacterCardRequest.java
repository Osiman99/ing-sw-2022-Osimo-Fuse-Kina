package it.polimi.ingsw.network.messages;

public class CharacterCardRequest extends Message{
    private static final long serialVersionUID = 6374071475323421718L;
    private final String card;
    //
    //

    public CharacterCardRequest(String nickname, String card) {
        super(nickname, MessageType.CHARACTERCARDS_REQUEST);
        this.card = card;
    }

    public String getCard() {
        return card;
    }

    public String toString(){
        return card + " card request";
    }
}
