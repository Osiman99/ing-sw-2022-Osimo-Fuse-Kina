package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;
import java.util.List;


public class Observable {

    private  final List<Observer> observerList = new ArrayList<>();

    /**
     * adds an object as an observer
     * @param observer
     */
    public void addObserver (Observer observer) {
        observerList.add(observer);
    }

    /**
     * removes an observer from the observer list
     * @param observer
     */
    public void removeObserver (Observer observer) {
        observerList.remove(observer);
    }

    /**
     * the observable object sends a message to each observer object belonging to his observer list
     * @param message
     */
    protected void notifyObserver (Message message) {
        for (Observer observer : observerList)
            observer.update(message);
    }
}
