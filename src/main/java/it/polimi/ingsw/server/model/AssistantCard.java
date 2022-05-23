package it.polimi.ingsw.server.model;

import java.io.Serializable;

public class AssistantCard implements Serializable {

    private static final long serialVersionUID = 6471988689871075538L;
    private final int value;
    private final int maxMoves;



    /**
     * default constructor
     * @param value
     * @param maxMoves
     */
    public AssistantCard(int value, int maxMoves) {
        this.value = value;
        this.maxMoves = maxMoves;
    }

    /**
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * @return maxMoves
     */
    public int getMaxMoves() {
        return maxMoves;
    }
}
