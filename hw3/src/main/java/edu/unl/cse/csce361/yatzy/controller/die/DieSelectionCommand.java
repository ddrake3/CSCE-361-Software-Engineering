package edu.unl.cse.csce361.yatzy.controller.die;

import edu.unl.cse.csce361.yatzy.controller.DiceController;
import edu.unl.cse.csce361.yatzy.controller.DieCommand;

/**
 * Command to select a die to be held or unheld. A held die does not change its value when the dice are rolled.
 */
public class DieSelectionCommand implements DieCommand {
    @Override
    public void execute() {
        DiceController.getController().getView(this).toggleHighlight();
    }

    @Override
    public String toString() {
        return DiceController.getController().getView(this).isHighlighted() ? "Unhold" : "Hold";
    }
}
