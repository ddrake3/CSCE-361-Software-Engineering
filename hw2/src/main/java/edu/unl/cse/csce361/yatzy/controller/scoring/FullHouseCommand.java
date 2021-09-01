package edu.unl.cse.csce361.yatzy.controller.scoring;

import edu.unl.cse.csce361.yatzy.model.scoring.OfTwoKindsCategory;

/**
 * Scoring command for the Full House category.
 */

public class FullHouseCommand extends AbstractDieBasedScoringCommand{
    public FullHouseCommand() {
        super(new OfTwoKindsCategory(3, 2));
    }

    @Override
    public String toString() {
        return "Full House";
    }
}
