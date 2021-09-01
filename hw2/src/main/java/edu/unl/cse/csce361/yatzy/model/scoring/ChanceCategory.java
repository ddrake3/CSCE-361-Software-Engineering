package edu.unl.cse.csce361.yatzy.model.scoring;

import edu.unl.cse.csce361.yatzy.model.DieModel;

import java.util.List;

/**
 * A scoring category whose value is the sum of the die faces, without regard to the particular combination.
 */
public class ChanceCategory extends AbstractDieBasedCategory {
    @Override
    public boolean isSatisfiedBy(List<DieModel> dice) {
        return true;
    }

    @Override
    protected List<DieModel> getSatisfyingDice(List<DieModel> dice) {
        return dice;
    }
}
