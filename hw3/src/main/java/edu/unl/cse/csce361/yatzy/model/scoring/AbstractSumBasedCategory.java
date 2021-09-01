package edu.unl.cse.csce361.yatzy.model.scoring;

import edu.unl.cse.csce361.yatzy.model.CategoryModel;
import edu.unl.cse.csce361.yatzy.model.DieModel;

import java.util.List;

/**
 * Base class for scoring categories that obtain their scores from the sums of other scoring categories.
 */
public abstract class AbstractSumBasedCategory implements CategoryModel {
    protected final List<CategoryModel> contributingCategories;

    public AbstractSumBasedCategory(List<CategoryModel> contributingCategories) {
        this.contributingCategories = contributingCategories;
    }

    @Override
    public boolean isSatisfiedBy(List<DieModel> dice) {
        return false;
    }

    @Override
    public int getHypotheticalScore(List<DieModel> dice) {
        return 0;
    }

    @Override
    public void assignScore(List<DieModel> dice) {
        throw new UnsupportedOperationException("Sum-based categories cannot take on values from dice.");
    }

    @Override
    public void reset() {
        // nothing to be done
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
