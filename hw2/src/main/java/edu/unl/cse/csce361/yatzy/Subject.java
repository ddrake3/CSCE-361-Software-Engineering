package edu.unl.cse.csce361.yatzy;

/**
 * The subject, or observable, in the Observer Pattern.
 */
public interface Subject {
    /**
     * Adds an observer to the collection of observers to be notified when the subject has an update. If the observer
     * is already registered, there is no change. While the Observer Pattern is usually described as having observers
     * register themselves, there is no reason that another object cannot register the observer.
     *
     * @param observer The observer to be added
     */
    void registerObserver(Observer observer);

    /**
     * Remove's an observer from the collection of observers that are notified when the subject has an update. If the
     * observer is not registered, there is no change.
     *
     * @param observer The observer to be removed.
     */
    void removeObserver(Observer observer);
}
