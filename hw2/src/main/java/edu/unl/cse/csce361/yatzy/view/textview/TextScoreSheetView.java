package edu.unl.cse.csce361.yatzy.view.textview;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 * The subview to display the scoresheet.
 */
public class TextScoreSheetView {
    private final List<CategoryViewTuple> leftSide;
    private final List<CategoryViewTuple> rightSide;
    private CategoryViewTuple grandTotal;
    private final StringBox stringBox;
    private final int[] labelTabs;
    private final int[] scoreTabs;
    private final int height;

    public TextScoreSheetView() {
        leftSide = new LinkedList<>();
        rightSide = new LinkedList<>();
        height = 12;
        labelTabs = new int[]{0, 20, 45};
        scoreTabs = new int[]{17, 42, 61};
        int width = scoreTabs[2];
        stringBox = new StringBox(height, width);
    }

    /**
     * Adds a number-based scoring category (e.g., Ones, Twos, ...). Also used for the left-side bonus and left-side
     * subtotal.
     *
     * @param labeler generates a String to display the category name, the selection number, and the completion mark
     * @param scorer  generates an Integer to display the category's score
     */
    public void addNumberCategory(Supplier<String> labeler, Supplier<Integer> scorer) {
        leftSide.add(new CategoryViewTuple(labeler, scorer));
    }

    /**
     * Adds a scoring category that is based on combinations of dice other than a desired face. Also used for the
     * right-side subtotal.
     *
     * @param labeler generates a String to display the category name, the selection number, and the completion mark
     * @param scorer  generates an Integer to display the category's score
     */
    public void addComboCategory(Supplier<String> labeler, Supplier<Integer> scorer) {
        rightSide.add(new CategoryViewTuple(labeler, scorer));
    }

    /**
     * Adds the grand total scoring category.
     *
     * @param labeler generates a String to display the category name
     * @param scorer  generates an Integer to display the category's score
     */
    public void addGrandTotal(Supplier<String> labeler, Supplier<Integer> scorer) {
        grandTotal = new CategoryViewTuple(labeler, scorer);
    }

    @Override
    public String toString() {
        for (int row = 0; row < height; row++) {
            stringBox
                    .placeString(leftSide.size() > row ? leftSide.get(row).labeler.get() : "",
                            StringBox.Vertical.TOP, row, StringBox.Horizontal.LEFT, labelTabs[0])
                    .placeString(leftSide.size() > row ? leftSide.get(row).scorer.get().toString() : "",
                            StringBox.Vertical.TOP, row, StringBox.Horizontal.RIGHT, scoreTabs[0])
                    .placeString(rightSide.size() > row ? rightSide.get(row).labeler.get() : "",
                            StringBox.Vertical.TOP, row, StringBox.Horizontal.LEFT, labelTabs[1])
                    .placeString(rightSide.size() > row ? rightSide.get(row).scorer.get().toString() : "",
                            StringBox.Vertical.TOP, row, StringBox.Horizontal.RIGHT, scoreTabs[1])
                    .placeString(" ", StringBox.Vertical.TOP, row, StringBox.Horizontal.RIGHT, scoreTabs[2]);
        }
        stringBox
                .placeString(grandTotal.labeler.get(), StringBox.Vertical.BOTTOM, height,
                        StringBox.Horizontal.LEFT, labelTabs[2])
                .placeString(grandTotal.scorer.get().toString(), StringBox.Vertical.BOTTOM, height,
                        StringBox.Horizontal.RIGHT, scoreTabs[2]);
        return stringBox.toString();
    }

    private static class CategoryViewTuple {
        public final Supplier<String> labeler;
        public final Supplier<Integer> scorer;

        private CategoryViewTuple(Supplier<String> labeler, Supplier<Integer> scorer) {
            this.labeler = labeler;
            this.scorer = scorer;
        }
    }
}
