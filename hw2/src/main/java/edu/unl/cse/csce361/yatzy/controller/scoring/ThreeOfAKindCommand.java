package edu.unl.cse.csce361.yatzy.controller.scoring;

import edu.unl.cse.csce361.yatzy.model.scoring.OfAKindCategory;


/**
 * Scoring command for the three of a kind category.
 */
public class ThreeOfAKindCommand extends AbstractDieBasedScoringCommand {
    public ThreeOfAKindCommand() {
        super(new OfAKindCategory(3));

    }

    @Override
    public String toString() {
        return "Three of a kind";
    }
    
}