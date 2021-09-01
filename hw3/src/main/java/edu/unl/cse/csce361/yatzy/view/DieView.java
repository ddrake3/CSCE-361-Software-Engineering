package edu.unl.cse.csce361.yatzy.view;

import edu.unl.cse.csce361.yatzy.Observer;

/**
 * The subview for a specific die
 */
public interface DieView extends Observer {
    /**
     * Obtains the die view's understanding of the die's value.
     *
     * @return the die's face value
     */
    int getValue();

    /**
     * Sets the die view's understanding of the die's value.
     *
     * @param value the die's face value
     * @throws IllegalArgumentException if the value is out of range (note: 0 is a legal value for an unrolled die)
     */
    void setValue(int value) throws IllegalArgumentException;

    /**
     * Reports whether a die has been highlighted (generally happens when the die is held).
     *
     * @return <code>true</code> if the die is highlighted, <code>false</code> otherwise
     */
    boolean isHighlighted();

    /**
     * Highlights a die (generally indicates that the die is held).
     */
    void highlight();

    /**
     * Unhighlights a die (generally indicates that the die is no longer to be held).
     */
    void unHighlight();

    /**
     * If the die is not highlighted, highlight it. If the die is highlighted, unhighlight it.
     */
    void toggleHighlight();

    /**
     * If the die is displayed right-side-up, display it upside-down. If the die is displayed upside-down, display it
     * right-side-up.
     */
    void toggleInversion();
}
