package edu.unl.cse.csce361.yatzy.controller.scoring;

import com.ibm.icu.text.RuleBasedNumberFormat;

import edu.unl.cse.csce361.yatzy.model.scoring.SequenceCategory;

/**
 * Scoring command for the Small Straight category.
 */

public class StraightCommand extends AbstractDieBasedScoringCommand{

    private int StraightStartNumber;
	
	public StraightCommand(int StraightStartNumber) {
		super(new SequenceCategory(StraightStartNumber));
		this.StraightStartNumber = StraightStartNumber;
    }

    @Override
    public String toString() {
    	RuleBasedNumberFormat ordinalFormat = new RuleBasedNumberFormat(RuleBasedNumberFormat.ORDINAL);
    	return ordinalFormat.format(StraightStartNumber) + " Straight";
    }
}
