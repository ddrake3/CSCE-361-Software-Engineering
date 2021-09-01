package edu.unl.cse.csce361.yatzy.model.scoring;

import edu.unl.cse.csce361.yatzy.Game;
import edu.unl.cse.csce361.yatzy.model.DieModel;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * Intermediate class for scoring categories based on dice having the same values as other dice.
 */
public abstract class AbstractMatchingCategory extends AbstractDieBasedCategory {
    /**
     * Reports the face value of the dice that occurs at least as often as any other face value. In case of a tie, one
     * of the values will be nondeterministically selected.
     *
     * @param dice the dice to be considered
     * @return the most-common face value
     */
    protected static int mostCommonFace(List<DieModel> dice) {
        int temp, count = 1, common = dice.get(0).getValue(), countTemp;
        for (int i = 0; i < (dice.size() - 1); i++) {
            temp = dice.get(i).getValue();
            countTemp = 0;
            for (int j = 0; j < dice.size(); j++) {
                if (temp == dice.get(j).getValue()) {
                    countTemp++;
                }
            }
            if (countTemp > count) {
                common = temp;
                count = countTemp;
            }
        }
        return common;
    }

    protected int checkPair(int value, List<DieModel> dice) {
        int sum = 0;
        for(DieModel die : dice) {
            if(die.getValue() == value) {
                sum++;
            }
        }
        return sum;
    }

    protected ArrayList<Integer> createRepresenativeArrayList(List<DieModel> dice){
        ArrayList<Integer> diceNum = new ArrayList<>();
        for(int i = 0; i < Game.NUMBER_OF_DIE_SIDES; i++) {
            diceNum.add(0);
        }
        for(int i = 0; i < dice.size(); i++) {
            int indexOfDiceNum = diceNum.get( (dice.get(i).getValue()-1) );
            diceNum.set(indexOfDiceNum, diceNum.get(indexOfDiceNum)+1);
        }
        return diceNum;
    }

}