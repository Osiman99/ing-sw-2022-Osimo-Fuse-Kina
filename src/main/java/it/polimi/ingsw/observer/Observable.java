package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;
import java.util.List;

public class Observable {

    private  final List<Observer> observerList = new ArrayList<>();

    public void addObserver (Observer observer) {
        observerList.add(observer);
    }

    public void removeObserver (Observer observer) {
        observerList.remove(observer);
    }

    protected void notifyObserver (Message message) {
        for (Observer observer : observerList)
            observer.update(message);
    }
}
