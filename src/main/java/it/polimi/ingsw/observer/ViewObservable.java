package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ViewObservable {

    protected final List<ViewObserver> observerList = new ArrayList<>();

    //add a new Observer object
    public void addObserver (ViewObserver observer) {
        observerList.add(observer);
    }

    public void removeObserver (ViewObserver observer) {
        observerList.remove(observer);
    }

    protected void notifyObserver(Consumer<ViewObserver> lambda) {
        for (ViewObserver observer : observerList)
            lambda.accept(observer);
    }

}
