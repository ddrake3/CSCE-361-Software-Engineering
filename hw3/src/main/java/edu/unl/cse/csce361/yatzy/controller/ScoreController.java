package edu.unl.cse.csce361.yatzy.controller;

import edu.unl.cse.csce361.yatzy.Game;
import edu.unl.cse.csce361.yatzy.controller.scoring.*;
import edu.unl.cse.csce361.yatzy.model.CategoryModel;
import edu.unl.cse.csce361.yatzy.model.DieModel;
import edu.unl.cse.csce361.yatzy.model.scoring.BonusCategory;
import edu.unl.cse.csce361.yatzy.model.scoring.TotalCategory;
import edu.unl.cse.csce361.yatzy.view.textview.TextGameBoard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Controller for coordinating the scoring of dice rolls.
 */
public class ScoreController extends Controller {
    private static ScoreController instance = null;

    public static ScoreController getController() {
        if (board == null) {
            throw new IllegalStateException("Need to set Controller.board before creating the instance.");
        }
        if (instance == null) {
            instance = new ScoreController();
        }
        return instance;
    }

    private ScoreController() {
        super();
        List<CategoryModel> leftCategoryModels = addLeftCategories();
        List<CategoryModel> rightCategoryModels = addRightCategories();
        addSumCategories(leftCategoryModels, rightCategoryModels);
    }

    /**
     * Applies dice to a scoring category. The dice are used to assign a score to the category's model, and the view
     * is changed to show the new score and to reflect that the category has been scored.
     *
     * @param scoringCommand the scoring category's command
     * @param categoryModel  the scoring category's model
     */
    public void assignScoreToCategory(ScoringCommand scoringCommand, CategoryModel categoryModel) {
        categoryModel.assignScore(dice);
        board.deactivateCommand(scoringCommand);
        board.setMessage("Scored " + categoryModel.getScore() + " points on " + scoringCommand + ".");
        dice.forEach(DieModel::reset);
    }

    private List<CategoryModel> addLeftCategories() {
        List<CategoryModel> categoryModels = new LinkedList<>();
        for (int i = 1; i <= Game.NUMBER_OF_DIE_SIDES; i++) {
            NumberScoringCommand categoryCommand = new NumberScoringCommand(i);
            categoryModels.add(categoryCommand.getCategoryModel());
            board.addCommand(categoryCommand, TextGameBoard.CategorySide.NUMBER_SIDE);
        }
        return categoryModels;
    }

    private void addRightCategory(AbstractDieBasedScoringCommand categoryCommand, List<CategoryModel> categoryModels) {
        categoryModels.add(categoryCommand.getCategoryModel());
        board.addCommand(categoryCommand, TextGameBoard.CategorySide.COMBO_SIDE);
    }

    private List<CategoryModel> addRightCategories() {
        List<CategoryModel> categoryModels = new LinkedList<>();
        addRightCategory(new ChanceCommand(), categoryModels);
        addRightCategory(new OnePairCommand(), categoryModels);
        addRightCategory(new TwoPairCommand(), categoryModels);
        addRightCategory(new ThreeOfAKindCommand(), categoryModels);
        addRightCategory(new FourOfAKindCommand(), categoryModels);
        for(int i = 1; i <= (Game.NUMBER_OF_DIE_SIDES - Game.NUMBER_OF_DICE +1 ); i++) {
        	addRightCategory(new StraightCommand(i), categoryModels);
        }
        addRightCategory(new FullHouseCommand(), categoryModels);
        addRightCategory(new YatzyCommand(), categoryModels);

        return categoryModels;
    }

    private void addSumCategories(List<CategoryModel> leftCategoryModels,
                                  List<CategoryModel> rightCategoryModels) {
        List<CategoryModel> sums = new ArrayList<>(3);
        CategoryModel categoryModel = new TotalCategory(leftCategoryModels);
        sums.add(categoryModel);
        board.addSubtotalCommand(new SumBasedScoringCommand(categoryModel, "Subtotal"),
                TextGameBoard.CategorySide.NUMBER_SIDE);
        categoryModel = new BonusCategory(leftCategoryModels);
        sums.add(categoryModel);
        board.addSubtotalCommand(new SumBasedScoringCommand(categoryModel, "Bonus"),
                TextGameBoard.CategorySide.NUMBER_SIDE);
        categoryModel = new TotalCategory(rightCategoryModels);
        sums.add(categoryModel);
        board.addSubtotalCommand(new SumBasedScoringCommand(categoryModel, "Subtotal"),
                TextGameBoard.CategorySide.COMBO_SIDE);
        board.addGrandTotalCommand(new SumBasedScoringCommand(new TotalCategory(sums), "Grand Total"));
    }
}
