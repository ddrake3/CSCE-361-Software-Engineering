package edu.unl.cse.csce361.yatzy.controller.scoring;

import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.text.RuleBasedNumberFormat;
import edu.unl.cse.csce361.yatzy.controller.DiceController;
import edu.unl.cse.csce361.yatzy.controller.NavigationController;
import edu.unl.cse.csce361.yatzy.controller.ScoringCommand;
import edu.unl.cse.csce361.yatzy.controller.ScoreController;
import edu.unl.cse.csce361.yatzy.model.CategoryModel;

/**
 * Base class for scoring commands.
 */
public abstract class AbstractDieBasedScoringCommand implements ScoringCommand {
    protected final static RuleBasedNumberFormat numberFormat;

    static {
        numberFormat = new RuleBasedNumberFormat(RuleBasedNumberFormat.SPELLOUT);
        numberFormat.setContext(DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU);
    }

    protected final CategoryModel categoryModel;

    protected AbstractDieBasedScoringCommand(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    @Override
    public void execute() {
        ScoreController.getController().assignScoreToCategory(this, categoryModel);
    }

    @Override
    public int getCategoryScore() {
        return categoryModel.getScore();
    }
}
