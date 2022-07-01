package it.polimi.ingsw.network;

import it.polimi.ingsw.server.model.StudentColor;

/**
 * The client sends the request to activate the effect of a character card.
 */
public class CharacterCardMessage extends Message{

    private static final long serialVersionUID = 6374071475323421718L;
    private final String card;
    private final StudentColor studentColor;
    private final int numIsland;

    public CharacterCardMessage(String nickname, String card, StudentColor studentColor, int numIsland) {
        super(nickname, MessageType.CHARACTERCARDS_REQUEST);
        this.card = card;
        this.studentColor = studentColor;
        this.numIsland = numIsland;
    }

    public String getCard() {
        return card;
    }

    public StudentColor getStudentColor() {
        return studentColor;
    }

    public int getNumIsland() {
        return numIsland;
    }

    public String toString(){
        return card + " card request";
    }
}
