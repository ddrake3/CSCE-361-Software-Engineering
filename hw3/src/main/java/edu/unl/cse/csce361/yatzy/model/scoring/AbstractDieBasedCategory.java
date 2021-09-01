package edu.unl.cse.csce361.yatzy.model.scoring;

import edu.unl.cse.csce361.yatzy.model.CategoryModel;
import edu.unl.cse.csce361.yatzy.model.DieModel;

import java.util.List;

/**
 * Base class for scoring categories that make use of dice values to obtain their score.
 */
public abstract class AbstractDieBasedCategory implements CategoryModel {
    private int value = 0;
    private boolean valueIsFinal = false;

    protected abstract List<DieModel> getSatisfyingDice(List<DieModel> dice);
    
    @Override
    public int getHypotheticalScore(List<DieModel> dice) {
        dice = getSatisfyingDice(dice);
        if (dice == null){
            return 0;
        }
        int total = 0;
        for (DieModel i : dice){
            total += i.getValue();
        }
        return total;
    }

    @Override
    public void assignScore(List<DieModel> dice) {
        value = getHypotheticalScore(dice);
        valueIsFinal = true;
    }

    @Override
    public int getScore() {
        return value;
    }

    @Override
    public boolean hasBeenScored() {
        return valueIsFinal;
    }

    @Override
    public void reset() {
        value = 0;
        valueIsFinal = false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + value;
    }
    
}
