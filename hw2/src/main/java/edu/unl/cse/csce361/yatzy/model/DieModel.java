package edu.unl.cse.csce361.yatzy.model;

import edu.unl.cse.csce361.yatzy.DieSubject;

/**
 * Representation of a die.
 */
public interface DieModel extends DieSubject, Comparable<DieModel> {

    /**
     * Obtains the face value of this die.
     *
     * @return this die's value
     */
    int getValue();

    /**
     * Assigns an in-range random integer to the die's value.
     */
    void roll();

    /**
     * Sets the die's value to 0, indicating it has no value until rolled again.
     */
    void reset();
}
