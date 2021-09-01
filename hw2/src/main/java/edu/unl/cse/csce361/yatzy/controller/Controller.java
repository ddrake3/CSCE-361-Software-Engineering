package edu.unl.cse.csce361.yatzy.controller;

import edu.unl.cse.csce361.yatzy.Game;
import edu.unl.cse.csce361.yatzy.model.die.Die;
import edu.unl.cse.csce361.yatzy.model.DieModel;
import edu.unl.cse.csce361.yatzy.view.GameBoard;

import java.util.List;

/**
 * Base class for all Controller classes, responsible for initializing references used throughout the controller
 * subsystem.
 */
public abstract class Controller {

    protected static GameBoard board = null;

    protected static final List<DieModel> dice;

    static {
        Die.setNumberOfSides(Game.NUMBER_OF_DIE_SIDES);
        dice = List.of(new Die(), new Die(), new Die(), new Die(), new Die());
    }

    public static void setBoard(GameBoard gameBoard) {
        board = gameBoard;
    }
}
