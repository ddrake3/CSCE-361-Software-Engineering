package edu.unl.cse.csce361.yatzy.model.scoring;

import edu.unl.cse.csce361.yatzy.model.die.Die;
import edu.unl.cse.csce361.yatzy.model.DieModel;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MatchingCategoryTest {
    private List<DieModel> noMatches;
    private List<DieModel> onePair;
    private List<DieModel> twoPair;
    private List<DieModel> threeOfAKind;
    private List<DieModel> fourOfAKind;
    private List<DieModel> fullHouse;
    private List<DieModel> fiveOfAKind;
    private OfAKindCategory oneKindCategory;
    private OfTwoKindsCategory twoKindsCategory;

    @Before
    public void setUp() {
        Die[] dice = new Die[7];
        Die.setNumberOfSides(6);
        for (int i = 0; i < 7; i++) {
            dice[i] = new Die(i);
        }
        oneKindCategory = new OfAKindCategory(2);
        twoKindsCategory = new OfTwoKindsCategory(2, 2);
        noMatches = List.of(dice[1], dice[2], dice[3], dice[4], dice[5]);
        onePair = List.of(dice[3], dice[2], dice[3], dice[4], dice[1]);
        twoPair = List.of(dice[3], dice[2], dice[3], dice[4], dice[2]);
        threeOfAKind = List.of(dice[3], dice[2], dice[3], dice[3], dice[1]);
        fourOfAKind = List.of(dice[3], dice[3], dice[3], dice[4], dice[3]);
        fullHouse = List.of(dice[3], dice[2], dice[3], dice[2], dice[2]);
        fiveOfAKind = List.of(dice[3], dice[3], dice[3], dice[3], dice[3]);
    }

    @Test
    public void testIsOnePair() {
        assertFalse(oneKindCategory.isSimpleMatch(noMatches, 2));
        assertTrue(oneKindCategory.isSimpleMatch(onePair, 2));
        assertTrue(oneKindCategory.isSimpleMatch(twoPair, 2));
        assertTrue(oneKindCategory.isSimpleMatch(threeOfAKind, 2));
        assertTrue(oneKindCategory.isSimpleMatch(fourOfAKind, 2));
        assertTrue(oneKindCategory.isSimpleMatch(fullHouse, 2));
        assertTrue(oneKindCategory.isSimpleMatch(fiveOfAKind, 2));
    }

    @Test
    public void testIsTwoPair() {
        assertFalse(twoKindsCategory.isCompoundMatch(noMatches, 2, 2));
        assertFalse(twoKindsCategory.isCompoundMatch(onePair, 2, 2));
        assertTrue(twoKindsCategory.isCompoundMatch(twoPair, 2, 2));
        assertFalse(twoKindsCategory.isCompoundMatch(threeOfAKind, 2, 2));
        assertFalse(twoKindsCategory.isCompoundMatch(fourOfAKind, 2, 2));
        assertTrue(twoKindsCategory.isCompoundMatch(fullHouse, 2, 2));
        assertFalse(twoKindsCategory.isCompoundMatch(fiveOfAKind, 2, 2));
    }

    @Test
    public void testIsThreeOfAKind() {
        assertFalse(oneKindCategory.isSimpleMatch(noMatches, 3));
        assertFalse(oneKindCategory.isSimpleMatch(onePair, 3));
        assertFalse(oneKindCategory.isSimpleMatch(twoPair, 3));
        assertTrue(oneKindCategory.isSimpleMatch(threeOfAKind, 3));
        assertTrue(oneKindCategory.isSimpleMatch(fourOfAKind, 3));
        assertTrue(oneKindCategory.isSimpleMatch(fullHouse, 3));
        assertTrue(oneKindCategory.isSimpleMatch(fiveOfAKind, 3));
    }

    @Test
    public void testIsFourOfAKind() {
        assertFalse(oneKindCategory.isSimpleMatch(noMatches, 4));
        assertFalse(oneKindCategory.isSimpleMatch(onePair, 4));
        assertFalse(oneKindCategory.isSimpleMatch(twoPair, 4));
        assertFalse(oneKindCategory.isSimpleMatch(threeOfAKind, 4));
        assertTrue(oneKindCategory.isSimpleMatch(fourOfAKind, 4));
        assertFalse(oneKindCategory.isSimpleMatch(fullHouse, 4));
        assertTrue(oneKindCategory.isSimpleMatch(fiveOfAKind, 4));
    }

    @Test
    public void testIsFullHouse() {
        assertFalse(twoKindsCategory.isCompoundMatch(noMatches, 3, 2));
        assertFalse(twoKindsCategory.isCompoundMatch(onePair, 3, 2));
        assertFalse(twoKindsCategory.isCompoundMatch(twoPair, 3, 2));
        assertFalse(twoKindsCategory.isCompoundMatch(threeOfAKind, 3, 2));
        assertFalse(twoKindsCategory.isCompoundMatch(fourOfAKind, 3, 2));
        assertTrue(twoKindsCategory.isCompoundMatch(fullHouse, 3, 2));
        assertFalse(twoKindsCategory.isCompoundMatch(fiveOfAKind, 3, 2));
    }

    @Test
    public void testIsYatzy() {
        assertFalse(oneKindCategory.isSimpleMatch(noMatches, 5));
        assertFalse(oneKindCategory.isSimpleMatch(onePair, 5));
        assertFalse(oneKindCategory.isSimpleMatch(twoPair, 5));
        assertFalse(oneKindCategory.isSimpleMatch(threeOfAKind, 5));
        assertFalse(oneKindCategory.isSimpleMatch(fourOfAKind, 6));
        assertFalse(oneKindCategory.isSimpleMatch(fullHouse, 5));
        assertTrue(oneKindCategory.isSimpleMatch(fiveOfAKind, 5));
    }
}