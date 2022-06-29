package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.messages.Message;

public interface Observer {

    /**
     * updates the observable object depending on the received type of message
     * @param message
     */
    void update (Message message);

}
