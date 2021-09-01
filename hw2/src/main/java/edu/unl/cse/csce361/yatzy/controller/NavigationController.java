package edu.unl.cse.csce361.yatzy.controller;

import edu.unl.cse.csce361.yatzy.controller.navigation.EndGameCommand;
import edu.unl.cse.csce361.yatzy.controller.navigation.RollDiceCommand;
import edu.unl.cse.csce361.yatzy.controller.navigation.ScoreDiceCommand;
import edu.unl.cse.csce361.yatzy.view.GameBoard;

/**
 * Controller for coordinating navigation through the game.
 */
public class NavigationController extends Controller {
    private static NavigationController instance = null;
    private GamePhase phase;

    public static NavigationController getController() {
        if (board == null) {
            throw new IllegalStateException("Need to set Controller.board before creating the instance.");
        }
        if (instance == null) {
            instance = new NavigationController();
        }
        return instance;
    }

    private NavigationCommand endGameCommand;
    private NavigationCommand rollDiceCommand;
    private NavigationCommand scoreDiceCommand;

    private void createCommands() {
        endGameCommand = new EndGameCommand();
        rollDiceCommand = new RollDiceCommand();
        scoreDiceCommand = new ScoreDiceCommand();
    }

    private NavigationController() {
        super();
        phase = GamePhase.READY;
        createCommands();
        addReadyCommands();
    }

    private void addReadyCommands() {
        board.addCommand(endGameCommand);
        board.addCommand(rollDiceCommand);
    }

    /**
     * Sets the game into the "READY" phase, in which the player has not used any rolls for the current turn.
     */
    public void setReadyPhase() {
        board.deactivateScoreCommands();
        if (!board.allScoresMade()) {
            board.addCommand(rollDiceCommand);
        }
        DiceController.getController().resetRollNumber();
        phase = GamePhase.READY;
    }

    /**
     * Sets the game into the "ROLLING" phase, in which the player has started rolling dice for the current turn but has
     * not yet exhausted their rolls.
     */
    public void setRollingPhase() {
        switch (phase) {
            case READY:
                board.activateDieCommands();
                board.addCommand(scoreDiceCommand);
                break;
            case ROLLING:
                /* do nothing unique */
                break;
            case SCORING:
                board.activateDieCommands();
                board.deactivateCommand(scoreDiceCommand);
                board.deactivateScoreCommands();
                break;
            default:
                throw new IllegalStateException("Reached unreachable code in NavigationController.setRollingPhase(). " +
                        "Phase=" + phase);
        }
        phase = GamePhase.ROLLING;
    }

    /**
     * Sets the game into the "SCORING" phase, in which the player is no longer rolling dice for the current turn but is
     * instead deciding which scoring category to apply the dice to.
     */
    public void setScoringPhase() {
        board.activateScoreCommands();
        board.deactivateDieCommands();
        board.deactivateCommand(rollDiceCommand);
        board.deactivateCommand(scoreDiceCommand);
        phase = GamePhase.SCORING;
    }

    /**
     * Starts a Yatzy game.
     */
    public void startGame() {
        try {
            board.playGame();
        } catch (NullPointerException exception) {
            System.err.println("An error occurred: " + exception + " at " + exception.getStackTrace()[0]);
        }
    }

    /**
     * Ends the game, terminating the program.
     */
    public void endGame() {
        board.endGame();
    }
}
