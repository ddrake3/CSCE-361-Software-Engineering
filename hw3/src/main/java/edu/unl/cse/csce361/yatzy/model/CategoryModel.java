package edu.unl.cse.csce361.yatzy.model;

import java.util.List;

/**
 * Representation of a scoring category.
 */
public interface CategoryModel {
    int UPPER_OR_LEFT_BONUS = 50;
    int FIVE_OF_A_KIND_VALUE = 50;

    /**
     * Report whether a collection of dice satisfy the criteria to be scored in this category.
     *
     * @param dice the dice to be checked
     * @return <code>true</code> if the dice satisfy the scoring criteria, <code>false</code> otherwise
     */
    boolean isSatisfiedBy(List<DieModel> dice);

    /**
     * Reports the value that would be assigned to this category if the collection of dice is scored according to this
     * category's scoring rule.
     *
     * @param dice the dice to be checked
     * @return the score that would be assigned if the dice were scored against this category
     */
    int getHypotheticalScore(List<DieModel> dice);

    /**
     * Assigns a value to this category based on the collection of dice and the category's scoring rule (optional
     * operation).
     *
     * @param dice the dice to be scored against this category
     */
    void assignScore(List<DieModel> dice);

    /**
     * Retrieves this category's current score. If no score has been assigned, getScore() will return 0; otherwise it
     * will return the score assigned to it.
     *
     * @return the current score for this category.
     */
    int getScore();

    /**
     * Reports whether a score has been assigned to this category.
     *
     * @return <code>true</code> if {@link #assignScore(List)}} was previously called, <code>false</code> otherwise
     */
    boolean hasBeenScored();

    /**
     * Resets the score to 0 and clears the {@link #hasBeenScored()} flag.
     */
    void reset();
}
