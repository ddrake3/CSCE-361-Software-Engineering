package edu.unl.cse.csce361.yatzy.controller.scoring;

import edu.unl.cse.csce361.yatzy.model.scoring.OfTwoKindsCategory;

/**
 * Scoring command for the two pair category.
 */
public class TwoPairCommand extends AbstractDieBasedScoringCommand {
    public TwoPairCommand() {
        super(new OfTwoKindsCategory(2,2));
    }

    @Override
    public String toString() {
        return "Two Pair";
    }

}
