package edu.unl.cse.csce361.yatzy.controller.scoring;

import edu.unl.cse.csce361.yatzy.model.scoring.SequenceCategory;

/**
 * Scoring command for the Large Straight category.
 */

public class LargeStraightCommand extends AbstractDieBasedScoringCommand{
    public LargeStraightCommand() {
        super(new SequenceCategory(2));
    }

    @Override
    public String toString() {
        return "Large Straight";
    }
}
