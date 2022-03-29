package it.polimi.ingsw.model;

public class AssistantCard {

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
