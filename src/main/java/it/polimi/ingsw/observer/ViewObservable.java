package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Custom observable class that can be observed by implementing the {@link ViewObserver} interface and registering as listener.
 */
public abstract class ViewObservable {

    protected final List<ViewObserver> observerList = new ArrayList<>();

    /**
     * Adds an observer.
     *
     * @param observer the observer to be added.
     */
    public void addObserver (ViewObserver observer) {
        observerList.add(observer);
    }

    public void removeObserver (ViewObserver observer) {
        observerList.remove(observer);
    }

    /**
     * Notifies all the current observers through the lambda argument.
     *
     * @param lambda the lambda to be called on the observers.
     */
    protected void notifyObserver(Consumer<ViewObserver> lambda) {
        for (ViewObserver observer : observerList)
            lambda.accept(observer);
    }

}
