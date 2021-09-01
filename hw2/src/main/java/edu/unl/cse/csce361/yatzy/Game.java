package edu.unl.cse.csce361.yatzy;

import edu.unl.cse.csce361.yatzy.controller.Controller;
import edu.unl.cse.csce361.yatzy.controller.DiceController;
import edu.unl.cse.csce361.yatzy.controller.NavigationController;
import edu.unl.cse.csce361.yatzy.controller.ScoreController;
import edu.unl.cse.csce361.yatzy.view.GameBoard;
import edu.unl.cse.csce361.yatzy.view.textview.TextGameBoard;

/**
 * <p>The main class for the game of Yatzy.</p>
 *
 * <p>If running from a terminal window that emulates a VT100 terminal then you can use <code>VT100</code> as a
 * command-line argument to cause the cursor to move when refreshing instead of scrolling the screen.</p>
 */
public class Game {
    public static final int NUMBER_OF_DICE = 5;
    public static final int NUMBER_OF_DIE_SIDES = 6;

    public static void main(String[] args) {
        boolean isVT100 = ((args.length > 0) && args[0].equalsIgnoreCase("vt100"));
        GameBoard board = new TextGameBoard(isVT100);
        Controller.setBoard(board);
        DiceController.getController();
        ScoreController.getController();
        NavigationController.getController().startGame();
    }
}
