package edu.unl.cse.csce361.yatzy.view;

import edu.unl.cse.csce361.yatzy.DieSubject;
import edu.unl.cse.csce361.yatzy.Game;
import edu.unl.cse.csce361.yatzy.Subject;
import edu.unl.cse.csce361.yatzy.model.die.Die;

/**
 * Contains the behavior common to all DieViews, regardless of display framework.
 */
public class AbstractDieView implements DieView {
    /** The face value of the die */
    private int value;
    private boolean highlighted;
    private boolean inverted;

    protected AbstractDieView() {
        value = 0;
        highlighted = false;
        inverted = false;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) throws IllegalArgumentException {
        if (value < 0) {
            throw new IllegalArgumentException("Die cannot take on a negative value: " + value + ".");
        }
        if (value > Game.NUMBER_OF_DIE_SIDES) {
            throw new IllegalArgumentException("Value " + value +
                    " is greater than the maximum value of " + value + ".");
        }
        this.value = value;
    }

    @Override
    public boolean isHighlighted() {
        return highlighted;
    }

    @Override
    public void highlight() {
        highlighted = true;
    }

    @Override
    public void unHighlight() {
        highlighted = false;
    }

    @Override
    public void toggleHighlight() {
        highlighted = !highlighted;
    }

    @Override
    public void toggleInversion() {
        inverted = !inverted;
    }

    @Override
    public void update(Subject subject) {
        DieSubject dieRoll;
        dieRoll = (DieSubject) subject;

        this.value = dieRoll.getValue();
    }
}
