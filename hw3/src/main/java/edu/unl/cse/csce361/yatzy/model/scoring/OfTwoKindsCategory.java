package edu.unl.cse.csce361.yatzy.model.scoring;

import edu.unl.cse.csce361.yatzy.Game;
import edu.unl.cse.csce361.yatzy.model.DieModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Scoring category for dice, divided into two groups, such that each group consists of dice whose face value is the
 * same as the other dice in that group. The face value of the dice in one group <i>must</i> be distinct from the face
 * value of the dice in the other group. The constructor's arguments specify how many dice must match each other in
 * the two groups. Typical usage would be two pair and full house.
 */
public class OfTwoKindsCategory extends AbstractMatchingCategory {
    private final int firstTargetCount;
    private final int secondTargetCount;

    public OfTwoKindsCategory(int firstTargetCount, int secondTargetCount) {
        int targetCountSum = firstTargetCount + secondTargetCount;
        if ((firstTargetCount > 1) && (secondTargetCount > 1) && (targetCountSum <= Game.NUMBER_OF_DICE)) {
            this.firstTargetCount = Math.max(firstTargetCount, secondTargetCount);
            this.secondTargetCount = Math.min(firstTargetCount, secondTargetCount);
        } else if (targetCountSum > Game.NUMBER_OF_DICE) {
            throw new IllegalArgumentException("Cannot have " + firstTargetCount + " of one kind and " +
                    secondTargetCount + " of another kind with only " + Game.NUMBER_OF_DICE + " dice.");
        } else if ((firstTargetCount == 0) || (secondTargetCount == 0)) {
            throw new IllegalArgumentException("With one of the target counts being zero of a kind, use " +
                    "OfAKindCategory instead of OfTwoKindsCategory.");
        } else if ((firstTargetCount == 1) || (secondTargetCount == 1)) {
            throw new IllegalArgumentException("With one of the target counts being one of a kind, use " +
                    "OfAKindCategory instead of OfTwoKindsCategory.");
        } else {
            throw new IllegalArgumentException("Unexpected argument combination.");
        }
    }

    /**
     * Reports whether there are (at least) two groups of the specified numbers of dice, each of which consists of dice
     * that all have the same face value. While the first group is understood to be the larger of the two groups, the
     * two groups may be of equal size.
     *
     * @param dice                     the collection of dice to be evaluated
     * @param minimumSizeOfFirstGroup  the minimum number of dice in the larger group of dice with the same value
     * @param minimumSizeOfSecondGroup the minimum number of dice in the smaller group of dice with the same value
     * @return <code>true</code> if there are two requisite-sized groups of common-valued dice, <code>false</code>
     * otherwise
     */
    boolean isCompoundMatch(List<DieModel> dice, int minimumSizeOfFirstGroup, int minimumSizeOfSecondGroup) {
        List<Integer> value = new ArrayList<>();
        for (DieModel i : dice){
            value.add(i.getValue());
        }
        List<DieModel> diceCopy = new ArrayList<DieModel>();
        Boolean firstGroupBool = false;
        Boolean secondGroupBool = false;
        for (DieModel i : dice){
            int frequency = Collections.frequency(value, i.getValue());
            if (frequency >= minimumSizeOfFirstGroup){
                for (DieModel j : dice){
                    if (j.getValue() != i.getValue()){
                        diceCopy.add(j);
                    }
                }
                firstGroupBool = true;
            }
        }
        for (DieModel i : diceCopy){
            int frequency = Collections.frequency(value, i.getValue());
            if (frequency >= minimumSizeOfSecondGroup){
                secondGroupBool = true;
            }
        }


        return firstGroupBool && secondGroupBool;
    }

    @Override
    public boolean isSatisfiedBy(List<DieModel> dice) {
        return isCompoundMatch(dice, firstTargetCount, secondTargetCount);
    }

    @Override
    protected List<DieModel> getSatisfyingDice(List<DieModel> dice) {
        if (!isSatisfiedBy(dice)){
            return null;
        }
        List<Integer> value = new ArrayList<>();
        for (DieModel i : dice){
            value.add(i.getValue());
        }
        List<DieModel> diceReturn = new ArrayList<>();
        for (DieModel dieModel : dice) {
            int frequency = Collections.frequency(value, dieModel.getValue());
            if(frequency >= firstTargetCount || frequency >= secondTargetCount){
                diceReturn.add(dieModel);
            }
        }
        return diceReturn;
    }

}
