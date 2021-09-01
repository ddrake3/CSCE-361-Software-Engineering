package edu.unl.cse.csce361.yatzy.model;

import edu.unl.cse.csce361.yatzy.model.die.Die;
import edu.unl.cse.csce361.yatzy.model.scoring.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CategoryModelTest {

    private List<DieModel> junk;
    private List<DieModel> twoTwos;
    private List<DieModel> threeThrees;
    private List<DieModel> fourFours;
    private List<DieModel> fiveFives;
    private List<DieModel> acesAndEights;
    private List<DieModel> sixesOverSevens;
    private List<DieModel> fourStraight;
    private List<DieModel> fiveStraightOn1;
    private List<DieModel> fiveStraightOn2;
    private List<DieModel> fiveStraightOn3;
    CategoryModel category;
    private List<DieModel> twoStraight;
    private List<DieModel> threeStraight;
    private List<DieModel> fiveStraight;

    @Before
    public void setUp() {
        Die[] dice = new Die[10];
        Die.setNumberOfSides(10);
        for (int i = 0; i < 10; i++) {
            dice[i] = new Die(i);
        }
        dice[0] = new Die(10);
        junk = List.of(dice[1], dice[3], dice[5], dice[7], dice[9]);
        twoTwos = List.of(dice[0], dice[2], dice[2], dice[4], dice[6]);
        threeThrees = List.of(dice[0], dice[8], dice[3], dice[3], dice[3]);
        fourFours = List.of(dice[4], dice[4], dice[4], dice[4], dice[7]);
        fiveFives = List.of(dice[5], dice[5], dice[5], dice[5], dice[5]);
        acesAndEights = List.of(dice[0], dice[1], dice[8], dice[1], dice[8]);
        sixesOverSevens = List.of(dice[6], dice[7], dice[6], dice[7], dice[6]);
        fourStraight = List.of(dice[2], dice[6], dice[1], dice[3], dice[4]);
        fiveStraightOn1 = List.of(dice[1], dice[2], dice[3], dice[4], dice[5]);
        fiveStraightOn2 = List.of(dice[2], dice[3], dice[4], dice[5], dice[6]);
        fiveStraightOn3 = List.of(dice[3], dice[4], dice[5], dice[6], dice[7]);

        twoStraight = List.of(dice[2], dice[3], dice[5], dice[7], dice[9]);
        threeStraight = List.of(dice[0], dice[3], dice[4], dice[5], dice[7]);
        fiveStraight = List.of(dice[5], dice[6], dice[7], dice[8], dice[9]);
    }

    @Test
    public void testChanceCategory() {
        category = new ChanceCategory();
        assertEquals(1 + 3 + 5 + 7 + 9, category.getHypotheticalScore(junk));
        assertEquals(10 + 2 + 2 + 4 + 6, category.getHypotheticalScore(twoTwos));
        assertEquals(10 + 8 + 3 + 3 + 3, category.getHypotheticalScore(threeThrees));
        assertEquals(4 + 4 + 4 + 4 + 7, category.getHypotheticalScore(fourFours));
        assertEquals(5 + 5 + 5 + 5 + 5, category.getHypotheticalScore(fiveFives));
        assertEquals(10 + 1 + 8 + 1 + 8, category.getHypotheticalScore(acesAndEights));
        assertEquals(6 + 7 + 6 + 7 + 6, category.getHypotheticalScore(sixesOverSevens));
        assertEquals(2 + 6 + 1 + 3 + 4, category.getHypotheticalScore(fourStraight));
        assertEquals(1 + 2 + 3 + 4 + 5, category.getHypotheticalScore(fiveStraightOn1));
        assertEquals(2 + 3 + 4 + 5 + 6, category.getHypotheticalScore(fiveStraightOn2));
        assertEquals(3 + 4 + 5 + 6 + 7, category.getHypotheticalScore(fiveStraightOn3));
    }

    @Test
    public void testNumberCategory() {
        category = new NumberCategory(3);
        assertEquals(3, category.getHypotheticalScore(junk));
        assertEquals(0, category.getHypotheticalScore(twoTwos));
        assertEquals(3 + 3 + 3, category.getHypotheticalScore(threeThrees));
        assertEquals(0, category.getHypotheticalScore(fourFours));
        assertEquals(0, category.getHypotheticalScore(fiveFives));
        assertEquals(0, category.getHypotheticalScore(acesAndEights));
        assertEquals(0, category.getHypotheticalScore(sixesOverSevens));
        assertEquals(3, category.getHypotheticalScore(fourStraight));
        assertEquals(3, category.getHypotheticalScore(fiveStraightOn1));
        assertEquals(3, category.getHypotheticalScore(fiveStraightOn2));
        assertEquals(3, category.getHypotheticalScore(fiveStraightOn3));
    }

    @Test
    public void testFiveInARowStartsAt1() {
        category = new SequenceCategory(1);
        assertFalse(category.isSatisfiedBy(junk));
        assertFalse(category.isSatisfiedBy(twoTwos));
        assertFalse(category.isSatisfiedBy(threeThrees));
        assertFalse(category.isSatisfiedBy(fourFours));
        assertFalse(category.isSatisfiedBy(fiveFives));
        assertFalse(category.isSatisfiedBy(acesAndEights));
        assertFalse(category.isSatisfiedBy(sixesOverSevens));
        assertFalse(category.isSatisfiedBy(fourStraight));
        assertTrue(category.isSatisfiedBy(fiveStraightOn1));
        assertFalse(category.isSatisfiedBy(fiveStraightOn2));
        assertFalse(category.isSatisfiedBy(fiveStraightOn3));
        assertEquals(0, category.getHypotheticalScore(fourStraight));
        assertEquals(1 + 2 + 3 + 4 + 5, category.getHypotheticalScore(fiveStraightOn1));
        assertEquals(0, category.getHypotheticalScore(fiveStraightOn2));
        assertEquals(0, category.getHypotheticalScore(fiveStraightOn3));
    }

    @Test
    public void testFiveInARowStartsAt2() {
        category = new SequenceCategory(2);
        assertFalse(category.isSatisfiedBy(junk));
        assertFalse(category.isSatisfiedBy(twoTwos));
        assertFalse(category.isSatisfiedBy(threeThrees));
        assertFalse(category.isSatisfiedBy(fourFours));
        assertFalse(category.isSatisfiedBy(fiveFives));
        assertFalse(category.isSatisfiedBy(acesAndEights));
        assertFalse(category.isSatisfiedBy(sixesOverSevens));
        assertFalse(category.isSatisfiedBy(fourStraight));
        assertFalse(category.isSatisfiedBy(fiveStraightOn1));
        assertTrue(category.isSatisfiedBy(fiveStraightOn2));
        assertFalse(category.isSatisfiedBy(fiveStraightOn3));
        assertEquals(0, category.getHypotheticalScore(fourStraight));
        assertEquals(0, category.getHypotheticalScore(fiveStraightOn1));
        assertEquals(2 + 3 + 4 + 5 + 6, category.getHypotheticalScore(fiveStraightOn2));
        assertEquals(0, category.getHypotheticalScore(fiveStraightOn3));
    }

    @Test
    public void testTwoOfAKind() {
        category = new OfAKindCategory(2);
        assertFalse(category.isSatisfiedBy(junk));
        assertTrue(category.isSatisfiedBy(twoTwos));
        assertTrue(category.isSatisfiedBy(threeThrees));
        assertTrue(category.isSatisfiedBy(fourFours));
        assertTrue(category.isSatisfiedBy(fiveFives));
        assertTrue(category.isSatisfiedBy(acesAndEights));
        assertTrue(category.isSatisfiedBy(sixesOverSevens));
        assertFalse(category.isSatisfiedBy(fourStraight));
        assertFalse(category.isSatisfiedBy(fiveStraightOn1));
        assertFalse(category.isSatisfiedBy(fiveStraightOn2));
        assertFalse(category.isSatisfiedBy(fiveStraightOn3));
        assertEquals(0, category.getHypotheticalScore(junk));
        assertEquals(2 + 2, category.getHypotheticalScore(twoTwos));
        assertEquals(5 + 5 + 5 + 5 + 5, category.getHypotheticalScore(fiveFives));
    }

    @Test
    public void testThreeOfAKind() {
        category = new OfAKindCategory(3);
        assertFalse(category.isSatisfiedBy(junk));
        assertFalse(category.isSatisfiedBy(twoTwos));
        assertTrue(category.isSatisfiedBy(threeThrees));
        assertTrue(category.isSatisfiedBy(fourFours));
        assertTrue(category.isSatisfiedBy(fiveFives));
        assertFalse(category.isSatisfiedBy(acesAndEights));
        assertTrue(category.isSatisfiedBy(sixesOverSevens));
        assertFalse(category.isSatisfiedBy(fourStraight));
        assertFalse(category.isSatisfiedBy(fiveStraightOn1));
        assertFalse(category.isSatisfiedBy(fiveStraightOn2));
        assertFalse(category.isSatisfiedBy(fiveStraightOn3));
        assertEquals(0, category.getHypotheticalScore(twoTwos));
        assertEquals(3 + 3 + 3, category.getHypotheticalScore(threeThrees));
        assertEquals(5 + 5 + 5 + 5 + 5, category.getHypotheticalScore(fiveFives));
    }

    @Test
    public void testFourOfAKind() {
        category = new OfAKindCategory(4);
        assertFalse(category.isSatisfiedBy(junk));
        assertFalse(category.isSatisfiedBy(twoTwos));
        assertFalse(category.isSatisfiedBy(threeThrees));
        assertTrue(category.isSatisfiedBy(fourFours));
        assertTrue(category.isSatisfiedBy(fiveFives));
        assertFalse(category.isSatisfiedBy(acesAndEights));
        assertFalse(category.isSatisfiedBy(sixesOverSevens));
        assertFalse(category.isSatisfiedBy(fourStraight));
        assertFalse(category.isSatisfiedBy(fiveStraightOn1));
        assertFalse(category.isSatisfiedBy(fiveStraightOn2));
        assertFalse(category.isSatisfiedBy(fiveStraightOn3));
        assertEquals(0, category.getHypotheticalScore(threeThrees));
        assertEquals(4 + 4 + 4 + 4, category.getHypotheticalScore(fourFours));
        assertEquals(5 + 5 + 5 + 5 + 5, category.getHypotheticalScore(fiveFives));
    }

    @Test
    public void testFiveOfAKind() {
        category = new OfAKindCategory(5);
        assertFalse(category.isSatisfiedBy(junk));
        assertFalse(category.isSatisfiedBy(twoTwos));
        assertFalse(category.isSatisfiedBy(threeThrees));
        assertFalse(category.isSatisfiedBy(fourFours));
        assertTrue(category.isSatisfiedBy(fiveFives));
        assertFalse(category.isSatisfiedBy(acesAndEights));
        assertFalse(category.isSatisfiedBy(sixesOverSevens));
        assertFalse(category.isSatisfiedBy(fourStraight));
        assertFalse(category.isSatisfiedBy(fiveStraightOn1));
        assertFalse(category.isSatisfiedBy(fiveStraightOn2));
        assertFalse(category.isSatisfiedBy(fiveStraightOn3));
        assertEquals(0, category.getHypotheticalScore(fourFours));
        assertEquals(CategoryModel.FIVE_OF_A_KIND_VALUE, category.getHypotheticalScore(fiveFives));
    }

    @Test
    public void testTwoPair() {
        category = new OfTwoKindsCategory(2, 2);
        assertFalse(category.isSatisfiedBy(junk));
        assertFalse(category.isSatisfiedBy(twoTwos));
        assertFalse(category.isSatisfiedBy(threeThrees));
        assertFalse(category.isSatisfiedBy(fourFours));
        assertFalse(category.isSatisfiedBy(fiveFives));
        assertTrue(category.isSatisfiedBy(acesAndEights));
        assertTrue(category.isSatisfiedBy(sixesOverSevens));
        assertFalse(category.isSatisfiedBy(twoStraight));
        assertFalse(category.isSatisfiedBy(threeStraight));
        assertFalse(category.isSatisfiedBy(fourStraight));
        assertFalse(category.isSatisfiedBy(fiveStraight));
        assertEquals(0, category.getHypotheticalScore(twoTwos));
        assertEquals(1 + 8 + 1 + 8, category.getHypotheticalScore(acesAndEights));
        assertEquals(0, category.getHypotheticalScore(fiveFives));
    }

    @Test
    public void testFullHouse() {
        category = new OfTwoKindsCategory(3, 2);
        assertFalse(category.isSatisfiedBy(junk));
        assertFalse(category.isSatisfiedBy(twoTwos));
        assertFalse(category.isSatisfiedBy(threeThrees));
        assertFalse(category.isSatisfiedBy(fourFours));
        assertFalse(category.isSatisfiedBy(fiveFives));
        assertFalse(category.isSatisfiedBy(acesAndEights));
        assertTrue(category.isSatisfiedBy(sixesOverSevens));
        assertFalse(category.isSatisfiedBy(twoStraight));
        assertFalse(category.isSatisfiedBy(threeStraight));
        assertFalse(category.isSatisfiedBy(fourStraight));
        assertFalse(category.isSatisfiedBy(fiveStraight));
        assertEquals(0, category.getHypotheticalScore(acesAndEights));
        assertEquals(3 * 6 + 2 * 7, category.getHypotheticalScore(sixesOverSevens));
        assertEquals(0, category.getHypotheticalScore(fiveFives));
    }
}