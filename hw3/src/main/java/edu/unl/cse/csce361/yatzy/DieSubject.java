package edu.unl.cse.csce361.yatzy;

/**
 * A specialized subject in the Observer Pattern. DieSubject provides a mechanism for observers to determine the
 * subject's face value. We expect the subject to represent a die (in the problem domain sense), though it need not
 * be an object of any particular class.
 */
public interface DieSubject extends Subject {
    /**
     * Provides the die's face value.
     *
     * @return a value between 1 and {@link Game#NUMBER_OF_DIE_SIDES}, inclusive
     */
    int getValue();
}
