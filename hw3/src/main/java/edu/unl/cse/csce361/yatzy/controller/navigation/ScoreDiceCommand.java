package edu.unl.cse.csce361.yatzy.controller.navigation;

import edu.unl.cse.csce361.yatzy.controller.NavigationController;

/**
 * Command to stop rolling dice and to give the player the ability to select a scoring category.
 */
public class ScoreDiceCommand extends AbstractNavigationCommand {
    @Override
    public void execute() {
        NavigationController.getController().setScoringPhase();
    }

    @Override
    public String toString() {
        return "Score Dice";
    }
}
