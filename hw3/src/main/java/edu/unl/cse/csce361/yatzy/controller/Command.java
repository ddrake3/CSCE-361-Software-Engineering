package edu.unl.cse.csce361.yatzy.controller;

/**
 * Base interface for the Command Pattern.
 */
public interface Command {
    /**
     * <p>The method to be called whenever the user selects a command.</p>
     * <p>In general, implementations should delegate the behavior to another object.</p>
     */
    default void execute() {
        System.err.println(this.getClass().getName() + " not yet implemented.");
    }

    /**
     * Implementations should override {@link Object#toString()} so a meaningful description of the command can be
     * shown to the user.
     *
     * @return the text to be displayed on the user interface
     */
    String toString();
}
