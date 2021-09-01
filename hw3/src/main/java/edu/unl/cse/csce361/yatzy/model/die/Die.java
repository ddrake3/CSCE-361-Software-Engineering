package edu.unl.cse.csce361.yatzy.model.die;

import edu.unl.cse.csce361.yatzy.AbstractSubject;
import edu.unl.cse.csce361.yatzy.model.DieModel;

import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

/**
 * The concrete representation of a die.
 */
public class Die extends AbstractSubject implements DieModel {
    private static int numberOfSides = 0;
    /**
     * Source of in-range random numbers
     */
    private static Iterator<Integer> futureValues;

    private int value;

    public static int getNumberOfSides() {
        return Die.numberOfSides;
    }

    /**
     * Sets the number of sides the dice in this game have
     *
     * @param numberOfSides the number of sides that dice in this game have
     */
    public static void setNumberOfSides(int numberOfSides) {
        if (Die.getNumberOfSides() == 0) {
            Die.numberOfSides = numberOfSides;
            setFutureValues(new Random().ints(1, numberOfSides + 1).iterator());
        } else if (numberOfSides != Die.getNumberOfSides()) {
            throw new IllegalStateException("Attempted to set dice to have " + numberOfSides +
                    " sides when they already have " + Die.numberOfSides + " sides.");
        }
    }

    static void setFutureValues(Iterator<Integer> futureValues) {
        Die.futureValues = futureValues;
    }

    public Die() {
        this(0);
    }

    public Die(int initialValue) {
        if (0 <= initialValue && initialValue <= numberOfSides && numberOfSides != 0) {
            setValue(initialValue);
        } else if (numberOfSides == 0) {
            throw new IllegalStateException("Attempted to create 0-sided die. " +
                    "Tell the developer to call Die.setNumberOfSides() first.");
        } else {
            throw new IllegalArgumentException("Attempted to set initial value of " + initialValue + " on a " +
                    numberOfSides + "-sided die.");
        }
    }

    @Override
    public int getValue() {
        return value;
    }

    void setValue(int nextValue) {
        value = nextValue;
    }

    @Override
    public void roll() {
        if (futureValues.hasNext()) {
            int nextValue = futureValues.next();
            if (1 <= nextValue && nextValue <= numberOfSides) {
                setValue(nextValue);
                notifyObservers();
            } else {
                throw new IllegalStateException("A " + numberOfSides +
                        "-sided die attempted to take on an illegal value: " + nextValue);
            }
        } else {
            throw new IllegalStateException("The infinite supply of random values has been exhausted.");
        }
    }

    @Override
    public void reset() {
        setValue(0);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Die die = (Die) other;
        return value == die.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(DieModel other) {
        return Integer.compare(this.getValue(), other.getValue());
    }
}
