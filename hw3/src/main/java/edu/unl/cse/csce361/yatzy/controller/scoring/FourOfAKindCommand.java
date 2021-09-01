package edu.unl.cse.csce361.yatzy.controller.scoring;

import edu.unl.cse.csce361.yatzy.model.scoring.ChanceCategory;
import edu.unl.cse.csce361.yatzy.model.scoring.OfAKindCategory;

/**
 * Scoring command for the FourOfAKind category.
 */

public class FourOfAKindCommand extends AbstractDieBasedScoringCommand {
    public FourOfAKindCommand() {
        super(new OfAKindCategory(4));
    }

    @Override
    public String toString() {
        return "Four of a Kind";
    }
}
