package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.messages.Message;

/**
 * Observer interface. It supports a generic method of update.
 */
public interface Observer {
    void update (Message message);
}
