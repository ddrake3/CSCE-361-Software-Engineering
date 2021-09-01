package edu.unl.cse.csce361.yatzy.controller.scoring;

import edu.unl.cse.csce361.yatzy.model.scoring.ChanceCategory;

/**
 * Scoring command for the Chance category.
 */
public class ChanceCommand extends AbstractDieBasedScoringCommand {
    public ChanceCommand() {
        super(new ChanceCategory());
    }

    @Override
    public String toString() {
        return "Chance";
    }
}
