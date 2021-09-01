package edu.unl.cse.csce361.yatzy.model.scoring;

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

        Map<DieModel, Integer> map = new HashMap<>();

        for(DieModel die : dice){
            Integer frequency = map.get(dice);

            if(frequency == null){
                map.put(die, 1);
            }
            else{
                map.put(die, frequency + 1);
            }
        }

        Map.Entry<DieModel, Integer> maxOccur = null;

        for(Map.Entry<DieModel, Integer> entry : map.entrySet()){
            if(maxOccur == null){
                maxOccur = entry;
            }

            if(entry.getValue() > maxOccur.getValue()){
                maxOccur = entry;
            }
        }


        return maxOccur.getValue();
    }
}
