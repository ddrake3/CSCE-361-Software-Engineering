package edu.unl.cse.csce361.yatzy.model.die;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class DieTest {

    private Die[] dice;

    @Before
    public void setUp() {
        Die.setNumberOfSides(3);
        dice = new Die[2];
        dice[0] = new Die();
        dice[1] = new Die();
    }

    @Test
    public void testSharedIntegerStream() {
        // Arrange
        Iterator<Integer> values = List.of(3, 1).iterator();
        Die.setFutureValues(values);
        // Act
        dice[0].roll();
        dice[1].roll();
        // Assert
        assertEquals(3, dice[0].getValue());
        assertEquals(1, dice[1].getValue());
    }
}