package edu.unl.cse.csce361.yatzy.view;

import edu.unl.cse.csce361.yatzy.controller.Command;
import edu.unl.cse.csce361.yatzy.controller.DieCommand;
import edu.unl.cse.csce361.yatzy.controller.NavigationCommand;
import edu.unl.cse.csce361.yatzy.controller.ScoringCommand;

/**
 * The player's interface to the game, containing the various sub-views. As the player's interface, includes the
 * display-input-respond loop.
 */
public interface GameBoard {
    enum CategorySide {NUMBER_SIDE, COMBO_SIDE}

    /**
     * Displays a message to the user.
     *
     * @param message the message to be displayed
     */
    void setMessage(String message);

    /**
     * Adds a die command for a particular die.
     *
     * @param command the command that manipulates the die
     * @param dieView the view for that die
     */
    void addCommand(DieCommand command, DieView dieView);

    /**
     * Adds a scoring category command, either on the left-side of the scoresheet or the right-side of the scoresheet.
     *
     * @param command the command to assign a score
     * @param side    the side of the scoresheet on which to place the scoring category
     */
    void addCommand(ScoringCommand command, CategorySide side);

    /**
     * Adds a scoring subtotal command, either on the left-side of the scoresheet or the right-side of the scoresheet.
     *
     * @param command the command to compute the subtotal
     * @param side    the side of the scoresheet on which to place the subtotal
     */
    void addSubtotalCommand(ScoringCommand command, CategorySide side);

    /**
     * Adds a scoring grand total command to the scoresheet.
     *
     * @param command the command to compute the grand total
     */
    void addGrandTotalCommand(ScoringCommand command);

    /**
     * Adds a navigation command (alternately called an option command) to the game.
     *
     * @param command the navigation command to be added
     */
    void addCommand(NavigationCommand command);

    /**
     * Makes it possible for the user to select die commands.
     */
    void activateDieCommands();

    /**
     * Makes it possible for the user to select scoring category commands.
     */
    void activateScoreCommands();

    /**
     * Makes it no longer possible for the user to select a particular command.
     *
     * @param command the command to be deactivated
     */
    void deactivateCommand(Command command);

    /**
     * Makes it no longer possible for the user to select die commands.
     */
    void deactivateDieCommands();

    /**
     * makes it no longer possible for the user to select scoring category commands.
     */
    void deactivateScoreCommands();

    /**
     * Reports whether the player has scored in all scoring categories.
     *
     * @return <code>false</code> if there are unscored categories, <code>true</code> otherwise
     */
    boolean allScoresMade();

    boolean replaceCommand(Command commandToBeRemoved, Command replacementCommand);

    /**
     * Sets the condition to end the display-input-response loop.
     */
    void endGame();

    /**
     * The display-input-response loop.
     */
    void playGame();
}
