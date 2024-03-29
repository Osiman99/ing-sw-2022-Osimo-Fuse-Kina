package it.polimi.ingsw.network;

import it.polimi.ingsw.server.model.Game;

/**
 * Message to notify generic information to the user.
 */
public class GenericMessage extends Message {

    private static final long serialVersionUID = 934399396584368694L;
    private final String message;


    public GenericMessage(String message) {
        super(Game.SERVER_NICKNAME, MessageType.GENERIC_MESSAGE);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}