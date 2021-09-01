package edu.unl.cse.csce361.yatzy;

import java.util.HashSet;
import java.util.Set;

/**
 * Provides the subject (or observable) behavior for the Observer Pattern. Concrete subject classes may inherit from
 * AbstractSubject or may instantiate an inner-class extension to act as a delegate.
 */
public abstract class AbstractSubject implements Subject {
    /**
     * The collection of observers to be notified of updates to the subject.
     */
    private final Set<Observer> observers;

    protected AbstractSubject() {
        observers = new HashSet<>();
    }

    /**
     * Calls the {@link Observer#update(Subject)} method on each observer in {@link #observers}, providing a link to
     * <code>this</code>, the object that has updated. If the observer needs the subject's state or a description of
     * what changed, it is the observer's responsibility to do so.
     */
    protected void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}
