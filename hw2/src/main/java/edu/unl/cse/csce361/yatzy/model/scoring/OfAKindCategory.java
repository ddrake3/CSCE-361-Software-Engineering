package edu.unl.cse.csce361.yatzy.model.scoring;

import edu.unl.cse.csce361.yatzy.Game;
import edu.unl.cse.csce361.yatzy.model.CategoryModel;
import edu.unl.cse.csce361.yatzy.model.DieModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Scoring category for dice whose face value is the same as other dice. The constructor's argument specifies how many
 * dice must match each other. Typical usage would be one pair, three of a kind, four of a kind, and Yatzy.
 */
public class OfAKindCategory extends AbstractMatchingCategory {
    private final int targetCount;

    public OfAKindCategory(int targetCount) {
        if ((1 < targetCount) && (targetCount <= Game.NUMBER_OF_DICE)) {
            this.targetCount = targetCount;
        } else if (targetCount > Game.NUMBER_OF_DICE) {
            throw new IllegalArgumentException("Cannot have " + targetCount + " of a kind with only " +
                    Game.NUMBER_OF_DICE + " dice.");
        } else if (targetCount == 1) {
            throw new IllegalArgumentException("Trivial one of a kind has no defined score.");
        } else {
            throw new IllegalArgumentException("Vacuous " + targetCount + " of a kind has no defined score.");
        }
    }

    /**
     * Reports whether at least a specified number of dice (<code>minmumSizeOfGroup</code>) all have the same face
     * value.
     *
     * @param dice               the collection of dice to be evaluated
     * @param minimumSizeOfGroup the minimum number of dice that must have the same value
     * @return <code>true</code> if there are at least the specified number of dice with the same value,
     * <code>false</code> otherwise
     */
    boolean isSimpleMatch(List<DieModel> dice, int minimumSizeOfGroup) {

        List<Integer> value = new ArrayList<>();
        for (DieModel i : dice){
            value.add(i.getValue());
        }
        for (int s: value) {
            int frequency = Collections.frequency(value, s);
            if (frequency >= minimumSizeOfGroup){
                return true;
            }
		}
        return false;
    }

    @Override
    public boolean isSatisfiedBy(List<DieModel> dice) {
        return isSimpleMatch(dice, targetCount);
    }

    @Override
    public List<DieModel> getSatisfyingDice(List<DieModel> dice) {
        if(isSatisfiedBy(dice)){
        List<Integer> value = new ArrayList<>();
        List<DieModel> satisfyingDice = new ArrayList<>();
        for (DieModel i : dice){
			value.add(i.getValue());
        }
        for (DieModel i : dice){
            int frequency = Collections.frequency(value, i.getValue());
            if(frequency >= targetCount){
                satisfyingDice.add(i);
            }
        }
        return satisfyingDice;
    }
    return null;
    }

    @Override
    public int getHypotheticalScore(List<DieModel> dice) {
        dice = getSatisfyingDice(dice);

        if (dice == null){
            return 0;
        }
        if(targetCount == 5 &&  getSatisfyingDice(dice).size() == 5){
            return CategoryModel.FIVE_OF_A_KIND_VALUE;
        }
        int total = 0; 
        for (DieModel i : dice){
            total += i.getValue();
        }
        return total;
    }
}
