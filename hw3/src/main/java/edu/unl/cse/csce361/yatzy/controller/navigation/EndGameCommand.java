package edu.unl.cse.csce361.yatzy.controller.navigation;

import edu.unl.cse.csce361.yatzy.controller.NavigationController;

/**
 * Command to end the game.
 */
public class EndGameCommand extends AbstractNavigationCommand {

    @Override
    public void execute() {
        NavigationController.getController().endGame();
    }

    @Override
    public String toString() {
        return "End game";
    }
}
