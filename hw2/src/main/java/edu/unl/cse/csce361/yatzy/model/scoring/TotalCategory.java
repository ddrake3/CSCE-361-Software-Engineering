package edu.unl.cse.csce361.yatzy.model.scoring;

import edu.unl.cse.csce361.yatzy.model.CategoryModel;

import java.util.List;

/**
 * A scoring category whose value is the sums of the scores of other scoring categories.
 */
public class TotalCategory extends AbstractSumBasedCategory {
    public TotalCategory(List<CategoryModel> contributingCategories) {
        super(contributingCategories);
    }

    @Override
    public int getScore() {
        return contributingCategories.stream().mapToInt(CategoryModel::getScore).sum();
    }

    @Override
    public boolean hasBeenScored() {
        return false;
    }
}
