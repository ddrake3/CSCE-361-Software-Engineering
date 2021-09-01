package edu.unl.cse.csce361.yatzy.model.scoring;

import edu.unl.cse.csce361.yatzy.Game;
import edu.unl.cse.csce361.yatzy.model.die.Die;
import edu.unl.cse.csce361.yatzy.model.DieModel;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Scoring category for a sequence of dice. Where Yahtzee® sequences are defined by length, Yatzy sequences must use
 * all of the dice and are defined by <i>which</i> sequence of all the dice is used. The argument to the constructor is
 * the least value in the sequence: for example, if the argument is 2 then the sequence is 2-3-4-5-6.
 */
public class SequenceCategory extends AbstractDieBasedCategory {
    private int targetSequenceStart;

    public SequenceCategory(int targetSequenceStart) {
        if ((1 <= targetSequenceStart) && (targetSequenceStart <= (Die.getNumberOfSides() - Game.NUMBER_OF_DICE + 1))) {
            this.targetSequenceStart = targetSequenceStart;
        } else {
            throw new IllegalArgumentException("Target sequence must be a face on the " + Die.getNumberOfSides() +
                    "-sided dice, and low enough to allow a sequence of " + Game.NUMBER_OF_DICE + " dice.");
        }
    }

    @Override
    public boolean isSatisfiedBy(List<DieModel> dice) {

        List<Integer> value = new ArrayList<>();
        for (DieModel di : dice){
            value.add(di.getValue());
        }
        if(value.get(0) != targetSequenceStart){
            return false;
        }
        for (int i = 0; i < value.size() - 1; i++) {
            if (value.get(i) != value.get(i + 1) - 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected List<DieModel> getSatisfyingDice(List<DieModel> dice) {
        if(!isSatisfiedBy(dice)){
            return null;
        }
        return dice;
    }
    
}
