package edu.unl.cse.csce361.yatzy;

/**
 * The observer in the Observer Pattern.
 */
public interface Observer {
    /**
     * The method by which the observer is notified of updates to the subject. Typically called by the subject itself,
     * with <code>this</code> as the argument.
     *
     * @param subject The subject, or observable, that has been updated.
     */
    void update(Subject subject);
}
