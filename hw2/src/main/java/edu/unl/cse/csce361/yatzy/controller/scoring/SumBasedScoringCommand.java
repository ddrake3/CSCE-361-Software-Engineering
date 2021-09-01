package edu.unl.cse.csce361.yatzy.controller.scoring;

import edu.unl.cse.csce361.yatzy.controller.ScoringCommand;
import edu.unl.cse.csce361.yatzy.model.CategoryModel;

/**
 * Scoring command for the Bonus, Subtotal, and Grand Total categories.
 */
public class SumBasedScoringCommand implements ScoringCommand {
    private final CategoryModel categoryModel;
    private final String label;

    public SumBasedScoringCommand(CategoryModel categoryModel, String label) {
        this.categoryModel = categoryModel;
        this.label = label;
    }

    @Override
    public void execute() {
        // do nothing
    }

    @Override
    public int getCategoryScore() {
        return categoryModel.getScore();
    }

    @Override
    public String toString() {
        return label;
    }
}
