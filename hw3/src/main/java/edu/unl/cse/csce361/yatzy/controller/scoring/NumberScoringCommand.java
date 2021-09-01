package edu.unl.cse.csce361.yatzy.controller.scoring;

import edu.unl.cse.csce361.yatzy.model.scoring.NumberCategory;

/**
 * Scoring command for the Number categories (e.g., Ones, Twos, ...).
 */
public class NumberScoringCommand extends AbstractDieBasedScoringCommand {
    private final int targetNumber;

    public NumberScoringCommand(int targetNumber) {
        super(new NumberCategory(targetNumber));
        this.targetNumber = targetNumber;
    }

    @Override
    public String toString() {
        // Until we think of a clean way to allow for pluralization in arbitrary locales, we'll hardcode it for English
        String numberSpellOut = numberFormat.format(targetNumber);
        char lastCharacterInSpellOut = numberSpellOut.charAt(numberSpellOut.length() - 1);
        String pluralization = (lastCharacterInSpellOut == 's' || lastCharacterInSpellOut == 'x') ? "es" : "s";
        return numberSpellOut + pluralization;
    }
}
