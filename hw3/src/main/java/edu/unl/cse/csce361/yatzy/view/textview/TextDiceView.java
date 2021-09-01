package edu.unl.cse.csce361.yatzy.view.textview;

import edu.unl.cse.csce361.yatzy.Game;
import edu.unl.cse.csce361.yatzy.view.DieView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * The subview to display the dice.
 */
public class TextDiceView {
    private final List<DieViewTuple> dice;
    private final StringBox stringBox;
    private final int tab;
    private final int height;

    public TextDiceView() {
        dice = new ArrayList<>(5);
        height = TextDieView.faces[0].split(System.lineSeparator()).length + 1;
        int width = TextGameBoard.DISPLAY_WIDTH;
        tab = width / Game.NUMBER_OF_DICE;
        stringBox = new StringBox(height, width);
    }

    /**
     * Adds a die to the view.
     *
     * @param dieView the subview for the specific die
     * @param labeler generates a string to provide the selection number and whether selecting the die will "hold" or
     *                "unhold" the die
     */
    public void addDie(DieView dieView, Supplier<String> labeler) {
        if (dice.size() < Game.NUMBER_OF_DICE) {
            DieViewTuple tuple = new DieViewTuple(dieView, labeler);
            dice.add(tuple);
            if (dice.indexOf(tuple) % 2 == 1) {
                tuple.dieView.toggleInversion();
            }
        } else {
            throw new IllegalStateException("Attempted to add more than " + Game.NUMBER_OF_DICE +
                    " dice to view.");
        }
    }

    @Override
    public String toString() {
        for (int i = 0; i < dice.size(); i++) {
            int horizontalPosition = tab / 2 + i * tab;
            DieViewTuple die = dice.get(i);
            String label = die.labeler.get();
            stringBox
                    .placeString(die.dieView.toString(), StringBox.Vertical.TOP, 0,
                            StringBox.Horizontal.CENTER, horizontalPosition)
                    .placeString(" ".repeat(die.lastLabel.length()), StringBox.Vertical.BOTTOM, height,
                            StringBox.Horizontal.CENTER, horizontalPosition)
                    .placeString(label, StringBox.Vertical.BOTTOM, height,
                            StringBox.Horizontal.CENTER, horizontalPosition);
            die.lastLabel = label;
        }
        return stringBox.toString();
    }

    private static class DieViewTuple {
        public final DieView dieView;
        public final Supplier<String> labeler;
        public String lastLabel;

        public DieViewTuple(DieView dieView, Supplier<String> labeler) {
            this.dieView = dieView;
            this.labeler = labeler;
            lastLabel = "";
        }
    }
}
