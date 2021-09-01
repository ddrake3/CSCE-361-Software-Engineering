package edu.unl.cse.csce361.yatzy.model.scoring;

import edu.unl.cse.csce361.yatzy.Game;
import edu.unl.cse.csce361.yatzy.model.CategoryModel;

import java.util.List;

/**
 * A scoring category whose value is formulaic based on the sums of other scoring categories.
 */
public class BonusCategory extends AbstractSumBasedCategory {
    public static final int SUBTOTAL_NEEDED_FOR_BONUS =
            3 * (Game.NUMBER_OF_DIE_SIDES * (Game.NUMBER_OF_DIE_SIDES + 1) / 2);    // 3 * Triangular Number formula

    public BonusCategory(List<CategoryModel> contributingCategories) {
        super(contributingCategories);
        for (CategoryModel category : contributingCategories) {
            if (!(category instanceof NumberCategory)) {
                throw new IllegalArgumentException("Bonus Category must be based on number categories 1-" +
                        Game.NUMBER_OF_DIE_SIDES + "; received " + category.getClass().getSimpleName() + ".");
            }
        }
        if (contributingCategories.size() < 1 || contributingCategories.size() > Game.NUMBER_OF_DIE_SIDES) {
            throw new IllegalArgumentException("Bonus Category must be based on number categories 1-" +
                    Game.NUMBER_OF_DIE_SIDES + "; received " + contributingCategories.size() + " number categories.");
        }
    }

    @Override
    public int getScore() {
        int subtotal = contributingCategories.stream().mapToInt(CategoryModel::getScore).sum();
        return subtotal >= SUBTOTAL_NEEDED_FOR_BONUS ? UPPER_OR_LEFT_BONUS : 0;
    }

    @Override
    public boolean hasBeenScored() {
        return getScore() == UPPER_OR_LEFT_BONUS;
    }

}
