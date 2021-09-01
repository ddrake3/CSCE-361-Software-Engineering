package edu.unl.cse.csce361.yatzy.controller.scoring;

import edu.unl.cse.csce361.yatzy.model.scoring.OfAKindCategory;
import edu.unl.cse.csce361.yatzy.model.scoring.OfTwoKindsCategory;

/**
 * Scoring command for yatzy category.
 */
public class YatzyCommand extends AbstractDieBasedScoringCommand {
    public YatzyCommand() {
        super(new OfAKindCategory(5));
    }

    @Override
    public String toString() {
        return "Yatzy";
    }

}
