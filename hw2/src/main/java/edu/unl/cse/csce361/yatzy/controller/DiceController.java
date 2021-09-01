package edu.unl.cse.csce361.yatzy.controller;

import edu.unl.cse.csce361.yatzy.Game;
import edu.unl.cse.csce361.yatzy.controller.die.DieSelectionCommand;
import edu.unl.cse.csce361.yatzy.model.DieModel;
import edu.unl.cse.csce361.yatzy.view.DieView;
import edu.unl.cse.csce361.yatzy.view.textview.TextDieView;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for coordinating actions involving dice.
 */
public class DiceController extends Controller {
    public static final int MAXIMUM_NUMBER_OF_ROLLS = 3;

    private static DiceController instance = null;

    public static DiceController getController() {
        if (board == null) {
            throw new IllegalStateException("Need to set Controller.board before creating the instance.");
        }
        if (instance == null) {
            instance = new DiceController();
        }
        return instance;
    }

    private final Map<DieCommand, DieTuple> dieTuples;
    private int rollNumber = 0;

    private DiceController() {
        super();
        dieTuples = new HashMap<>(Game.NUMBER_OF_DICE);
        dice.forEach((die) -> this.addDie(die, new TextDieView(), new DieSelectionCommand()));
    }

    private void addDie(DieModel dieModel, DieView dieView, DieCommand dieCommand) {
        dieTuples.put(dieCommand, new DieTuple(dieModel, dieView, dieCommand));
        board.addCommand(dieCommand, dieView);
        dieModel.registerObserver(dieView);
    }

    /**
     * Gets the die model associated with a die command
     *
     * @param command a die command associated with a die model and a die view
     * @return the die model
     */
    public DieModel getModel(DieCommand command) {
        return dieTuples.get(command).model;
    }

    /**
     * Gets the die view associated with a die command
     *
     * @param command a die command associated with a die model and a die view
     * @return the die view
     */
    public DieView getView(DieCommand command) {
        return dieTuples.get(command).view;
    }

    /**
     * Rolls the dice. Specifically, for each problem-domain die, if the view is not highlighted then the model is
     * instructed to take on an in-range random value. If the die's view is highlighted then the die's model is
     * unchanged.
     */
    public void rollDice() {
        rollNumber++;
        for (DieTuple dieTuple : dieTuples.values()) {
            if (dieTuple.view.isHighlighted()) {
                dieTuple.view.unHighlight();
            } else {
                dieTuple.model.roll();
            }
        }
        if (rollNumber == MAXIMUM_NUMBER_OF_ROLLS) {
            NavigationController.getController().setScoringPhase();
        }
    }

    /**
     * At the start of each turn, the player has {@link #MAXIMUM_NUMBER_OF_ROLLS} rolls remaining; this method resets
     * the count from the previous turn.
     */
    public void resetRollNumber() {
        rollNumber = 0;
    }

    private static class DieTuple {
        public final DieModel model;
        public final DieView view;
        public final DieCommand command;

        public DieTuple(DieModel dieModel, DieView dieView, DieCommand dieCommand) {
            model = dieModel;
            view = dieView;
            command = dieCommand;
        }
    }
}
