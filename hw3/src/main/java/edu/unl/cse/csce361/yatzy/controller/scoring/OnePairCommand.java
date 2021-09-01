package edu.unl.cse.csce361.yatzy.controller.scoring;

import edu.unl.cse.csce361.yatzy.model.scoring.OfAKindCategory;

/**
 * Scoring command for the one pair category.
 */
public class OnePairCommand extends AbstractDieBasedScoringCommand{
    public OnePairCommand() {
        super(new OfAKindCategory(2));

    }

    @Override
    public String toString() {
        return "One Pair";
    }

}
