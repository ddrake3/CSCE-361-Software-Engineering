package edu.unl.cse.csce361.yatzy.view.textview;

/**
 * <p>A very simple terminal-based display. Can be used to place arbitrary strings in arbitrary locations in a terminal
 * window. StringBox will <i>not</i> redraw the screen. Instead, it will produce a string that, when printed, will fill
 * the screen, causing the screen's previous contents to scroll off of the screen.</p>
 *
 * <p>A typical usage is:</p>
 * <pre><code>
 *     StringBox stringBox = new StringBox();
 *     String screen = stringBox
 *             .placeString(...)
 *                  ⋮
 *             .placeString(...)
 *             .toString();
 *     System.out.println(screen);
 * </code></pre>
 *
 * <p>Note that StringBox is <i>not</i> robust to hidden characters, such as VT100 escape sequences.</p>
 *
 * <p>If found in a project, note that the most-current code can be found in
 * <a href="https://git.unl.edu/bohn/string_box">its own repository</a>.</p>
 */
public class StringBox {
    /**
     * Horizontal alignment directives
     */
    public enum Horizontal {LEFT, CENTER, RIGHT}

    /**
     * Vertical alignment directives
     */
    public enum Vertical {TOP, CENTER, BOTTOM}

    private int maximumWidth;
    private int maximumHeight;
    private int logicalHeight;

    private StringRow[] rows;

    /**
     * <p>Produces a StringBox suitably-sized for a standard 24×80 terminal. The StringBox will be 23×80; if the string
     * is printed with <code>System.out.println()</code> then it will leave the cursor on the 24th line, where the
     * user can enter their input without scrolling the top of the string off the screen.</p>
     *
     * <p>Alternatively, you might create a StringBox using <code>StringBox(24,80)</code>. If the string is printed
     * with <code>System.out.print()</code> then it will leave the cursor at the end of the 24th line without scrolling
     * the top of the string off of the screen. This particular style would be useful if you place the prompt on the
     * 24th line.</p>
     *
     * @see #StringBox(int, int)
     */
    public StringBox() {
        this(23, 80);    // standard terminal is 24×80, but leave room for the user's input
    }

    /**
     * <p>Produces an arbitrarily-sized StringBox. The StringBox should be no wider than your terminal and should be
     * at least one line shorter than your terminal. If the string is printed with <code>System.out.println()</code>
     * then it will leave the cursor on the last line, where the user can enter their input without scrolling the top
     * of the string off the screen.</p>
     *
     * <p>Alternatively, you might create a StringBox whose height is the height of your terminal. If the string is
     * printed with <code>System.out.print()</code> then it will leave the cursor at the end of the last line without
     * scrolling the top of the string off of the screen. This particular style would be useful if you place the
     * prompt on the last line.</p>
     *
     * @param boxHeight the number of rows in this StringBox
     * @param boxWidth  the number of columns in this StringBox
     */
    public StringBox(int boxHeight, int boxWidth) {
        if (boxHeight > 0 && boxWidth > 0) {
            this.maximumHeight = boxHeight;
            this.maximumWidth = boxWidth;
            rows = new StringRow[boxHeight];
            for (int i = 0; i < boxHeight; i++) {
                rows[i] = new StringRow(boxWidth);
            }
            logicalHeight = 0;
        } else {
            throw new IllegalArgumentException("String Box must be at least 1 character wide by 1 character tall. " +
                    "Dimensions " + boxWidth + "×" + boxHeight + " are too small.");
        }
    }

    /**
     * <p>Places a string in the StringBox with its upper-left corner in the specified location. If the string
     * contains multiple lines, each line after the first will be placed in the row subsequent to the previous line,
     * and the lines will be left-justified. Any portions of the string that would be placed outside the StringBox's
     * defined boundaries will be silently truncated.</p>
     *
     * <p>Equivalent to <code>placeString(string, Vertical.TOP, topRow, Horizontal.LEFT, leftColumn)</code>.</p>
     *
     * @param string     the string to be placed in the StringBox
     * @param topRow     the row on which the first line of the string should be placed
     * @param leftColumn the column in which the first character of each row should be placed
     * @return the current StringBox object, suitable for chained calls
     * @see #placeString(String, Vertical, int, Horizontal, int)
     */
    public StringBox placeString(String string, int topRow, int leftColumn) {
        return placeString(string, Vertical.TOP, topRow, Horizontal.LEFT, leftColumn);
    }

    /**
     * <p>Places a string in the StringBox.</p>
     *
     * <p>If <code>verticalAlignment</code> is <code>TOP</code> then the string's first line will be in the
     * <code>verticalPosition</code>'th row, and each subsequent line will be in each subsequent row. If
     * <code>verticalAlignment</code> is <code>BOTTOM</code> then the string's last line will be in the row immediately
     * preceding the <code>verticalPosition</code>'th row, and each preceding line will be in each preceding row.</p>
     *
     * <p>If <code>horizontalAlignment</code> is <code>LEFT</code> then each line will be left-justified to the
     * <code>horizontalPosition</code>'th column. If <code>horizontalAlignment</code> is <code>RIGHT</code> then each
     * line will be right-justified to the <code>horizontalPosition</code>'th column; that is, the rightmost character
     * will be immediately to the left of the <code>horizontalPosition</code>'th column.</p>
     *
     * <p>In cases of <code>CENTER</code> alignment: if there are an odd number of lines then the center line will be on
     * the <code>verticalPosition</code>'th row. Similarly, if there are an odd number of characters in a line then the
     * center character will be in the <code>horizontalPosition</code>'th column. If there are an even number of rows
     * (or characters) then the row (character) immediately below (to the right of) the midpoint will be in the
     * designated row (column).</p>
     *
     * @param string              the string to be placed in the StringBox
     * @param verticalAlignment   vertical alignment directive (top/bottom)
     * @param verticalPosition    the row on which the string should be top/bottom aligned
     * @param horizontalAlignment horizontal alignment directive (left/right)
     * @param horizontalPosition  the column on which the string should be top/bottom aligned
     * @return the current StringBox object, suitable for chained calls
     */
    public StringBox placeString(String string, Vertical verticalAlignment, int verticalPosition,
                                 Horizontal horizontalAlignment, int horizontalPosition) {
        String[] nullishString = {"null"};
        String[] strings = string != null ? string.split(System.lineSeparator()) : nullishString;
        // This would benefit from the switch expression in JDK 12+
        int topRow = verticalAlignment == Vertical.TOP ? verticalPosition :
                verticalAlignment == Vertical.BOTTOM ? verticalPosition - strings.length :
                        verticalAlignment == Vertical.CENTER ? verticalPosition - strings.length / 2 :
                                0;
        int firstRow = Math.max(topRow, 0);
        int lastRow = Math.min(topRow + strings.length, maximumHeight);
        int offset = -topRow;
        for (int i = firstRow; i < lastRow; i++) {
            rows[i].placeSubstring(strings[i + offset], horizontalAlignment, horizontalPosition);
        }
        logicalHeight = Math.max(logicalHeight, lastRow);
        return this;
    }

    /**
     * <p>Generates the string that the client code produced by calling {@link #placeString(String, int, int)} and
     * its related methods. Any unused lines between the last line of text and the bottom of the StringBox will be
     * filled with newLines so that when the string is printed, the previous string will fully scroll off of the screen.
     *
     * <p>Equivalent to <code>toString(true)</code>.</p>
     *
     * @return the string built by calls to {@link #placeString(String, int, int)} and its related methods
     * @see #toString(boolean)
     */
    @Override
    public String toString() {
        return toString(true);
    }

    /**
     * Generates the string that the client code produced by calling {@link #placeString(String, int, int)} and
     * its related methods. If this method's argument is <code>true</code>, then any unused lines between the last line
     * of text and the bottom of the StringBox will be filled with newLines so that when the string is printed, the
     * previous string will fully scroll off of the screen. If the argument is <code>false</code>, then the returned
     * string will stop after the last line of text.
     *
     * @param padToHeight indicates whether newlines should be placed after the last line of text
     * @return the string built by calls to {@link #placeString(String, int, int)} and its related methods
     */
    public String toString(boolean padToHeight) {
        StringBuilder returnString = new StringBuilder(maximumHeight * (maximumWidth + 1));
        if (logicalHeight > 0) {
            returnString.append(rows[0].toString());
        }
        for (int i = 1; i < logicalHeight; i++) {
            returnString.append(System.lineSeparator()).append(rows[i].toString());
        }
        if (padToHeight) {
            for (int i = logicalHeight; i < maximumHeight; i++) {
                returnString.append(System.lineSeparator()).append(rows[i].toString());
            }
        }
        return returnString.toString();
    }

    /**
     * A helper inner class. Client code should not directly access StringRow objects.
     */
    static class StringRow {
        private final StringBuilder stringBuilder;
        private final int maximumWidth;
        private int rightEdge;

        public StringRow(int maximumWidth) {
            this.maximumWidth = maximumWidth;
            stringBuilder = new StringBuilder(maximumWidth);
        }

        public StringRow placeSubstring(String string, int leftColumn) {
            return placeSubstring(string, Horizontal.LEFT, leftColumn);
        }

        public StringRow placeSubstring(String string, Horizontal horizontalAlignment, int horizontalPosition) {
            String modifiedString = string
                    .replace("\t", "    ")
                    .replace("\r", "")
                    .replace("\n", "\\");
            // This would also benefit from the switch expression in JDK 12+
            int leftColumn = horizontalAlignment == Horizontal.LEFT ? horizontalPosition :
                    horizontalAlignment == Horizontal.RIGHT ? horizontalPosition - modifiedString.length() :
                            horizontalAlignment == Horizontal.CENTER ?
                                    horizontalPosition - modifiedString.length() / 2 :
                                    0;
            if (leftColumn > rightEdge) {
                int paddingSize = leftColumn - rightEdge;
                modifiedString = " ".repeat(paddingSize) + modifiedString;
                leftColumn -= paddingSize;
            }
            if (leftColumn < 0) {
                modifiedString = modifiedString.substring(-leftColumn);
                leftColumn = 0;
            }
            int stringLength = modifiedString.length();
            stringBuilder.replace(leftColumn, leftColumn + stringLength, modifiedString);
            rightEdge = Math.max(rightEdge, leftColumn + stringLength);
            int overshoot = rightEdge - maximumWidth;
            if (overshoot > 0) {
                rightEdge -= overshoot;
                stringBuilder.setLength(rightEdge);
            }
            return this;
        }

        @Override
        public String toString() {
            return stringBuilder.toString();
        }
    }
}
