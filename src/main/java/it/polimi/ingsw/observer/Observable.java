package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.Message;
import java.util.ArrayList;
import java.util.List;

/**
 * Observable class that can be observed by implementing the {@link Observer} interface and registering as listener.
 */
public class Observable {

    private  final List<Observer> observerList = new ArrayList<>();

    /**
     * Adds an observer.
     *
     * @param observer the observer to be added.
     */
    public void addObserver (Observer observer) {
        observerList.add(observer);
    }

    /**
     * Removes an observer from the observer list.
     *
     * @param observer the observer to be removed.
     */
    public void removeObserver (Observer observer) {
        observerList.remove(observer);
    }

    /**
     * Notifies all the current observers through the update method and passes to them a {@link Message}.
     *
     * @param message the message to be passed to the observers.
     */
    protected void notifyObserver (Message message) {
        for (Observer observer : observerList)
            observer.update(message);
    }
}
