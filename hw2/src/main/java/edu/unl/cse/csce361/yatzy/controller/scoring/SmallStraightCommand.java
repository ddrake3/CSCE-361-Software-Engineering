package edu.unl.cse.csce361.yatzy.controller.scoring;

import edu.unl.cse.csce361.yatzy.model.scoring.SequenceCategory;

/**
 * Scoring command for the Small Straight category.
 */

public class SmallStraightCommand extends AbstractDieBasedScoringCommand{

    public SmallStraightCommand() {
        super(new SequenceCategory(1));
    }

    @Override
    public String toString() {
        return "Small Straight";
    }
}
