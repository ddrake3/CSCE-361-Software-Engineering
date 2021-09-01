package edu.unl.cse.csce361.yatzy.controller.navigation;

import edu.unl.cse.csce361.yatzy.controller.NavigationController;
import edu.unl.cse.csce361.yatzy.controller.DiceController;

/**
 * Command to roll the dice.
 */
public class RollDiceCommand extends AbstractNavigationCommand {
    @Override
    public void execute() {
        NavigationController.getController().setRollingPhase();
        DiceController.getController().rollDice();
    }

    @Override
    public String toString() {
        return "Roll dice";
    }
}
