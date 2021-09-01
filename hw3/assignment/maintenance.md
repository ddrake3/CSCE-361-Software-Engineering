#   Ya7zy Game

-   Assignment Due: July 6, 2020 at 11:00am
-   Peer Assessment Due: July, 2020 at 11:59pm

In this assignment you will address the suggestions from the code review
assignment and perform maintenance on the Yatzy game.

## Objectives

Students will:

-   Receive constructive feedback from code review comments
-   Practice software maintenance
-   Recognize the value of loose coupling when performing software maintenance
-   Gain additional experience using a version control system with multiple
    developers and an issue tracker to coordinate their efforts

## Instructions

This assignment is to be completed in assigned pairs; **no collaboration
other than with your assigned partner is permitted**.  One of the purposes of
pair-assignments is to practice teamwork. After completing the assignment you
will need to complete a peer assessment. Your contribution grade will be based
on the peer assessments and on the git history.

*Commit material that you worked on individually under your own name* using the
defaults that you set. *When (and only when) you commit material that was
developed using pair programming, override the default commit author to reflect
both authors* so that we can properly credit both authors for their contribution
grades. When you override the default commit author list both students' names,
and for the email address use a fake email address that is unique to the pair
of students by concatenating your Canvas login IDs (the angle brackets around
the email address are required):
```
git commit --author="Herbie Husker and Lil Red <hhusker20lred19@dev.null>"
```
You can use this same technique for the rare circumstance in which your partner
is briefly unable to commit code themselves:
```
git commit --author="Herbie Husker <herbie@huskers.unl.edu>"
```

##  Setup

-   You will work with the student who reviewed your code in the code review
    assignment.  You should both have some familiarity with your code:  you
    because you wrote it, and your partner because they reviewed it.

-   You and your partner only need to address the code review suggestions for
    one of your projects.  Decide which one and **post the repository URL in
    the answer field for the Canvas assignment**.

##  Assignment

### Address the Code Review

1.  Select six (6) of the improvement comments from the code review.

    1.  Navigate your web browser to your repository on `git.unl.edu` and open
        the Issue Tracker (On the left side-menu, click on `Issues`).

    1.  For each selected suggestion, create an Issue.

        As with your original development on the Yatzy Game,
        use the ability to assign issues to developers to coordinate who is
        working on which parts of the code: you should only work on an issue
        after it's assigned to you.

        Note:
        -   It is possible, even probable, that the issues will be of unequal
            difficulty; if one student works on more than three issues and the
            other works on fewer than three issues, that does not mean that you
            aren't contributing equally.

1.  Modify the code to address each of the issues.

1.  Test the game (both automated unit test and manual system tests) to confirm
    that you didn't break any existing code.
    -   Fix any faults you accidentally created.

1.  As you finish each issue, commit the code and close the issue.

### Perform Software Maintenance

Procrastination Pastimes has concluded that:

-   The terms "Small Straight" and "Large Straight" are confusing to players
    who are accustomed to the Yahtzee® definitions of Small & Large Straights
-   The market for 6-sided dice games is saturated

You have been tasked with changing the game from Yatzy to Ya7zy, in which the
player can choose to play the game with either 6-sided or 7-sided dice, as
geometrically unlikely as that seems.

**As you work on this portion of the assignment, keep track of the how many
lines you change.**

-   Keep track of how many lines you add
-   Keep track of how many lines you delete
-   Keep track of how many lines you modify (not including indentation changes)
-   Do not count `import` lines

These values will not affect your grade, but I do want you to report them at
the end of this assignment.

5.  Navigate your web browser to your repository on `git.unl.edu` and open
    the Issue Tracker. Create an issue for each of:
    -   Rename "Small Straight" and "Large Straight"
    -   Add faces file for 7-sided dice
    -   Allow player to chose the dice to play with
    -   Display the correct dice, based on the number of die sides
    -   Display correct scoring categories, based on the number of die sides
    -   New scoring categories are scored correctly

1.  Rename the "Straights" from "Small Straight" and "Large Straight" to
    "1st Straight" and "2nd Straight," respectively. That is, the ordinal should correspond to the first value in the sequence (*e.g.*, "1st Straight" is 1-2-3-4-5)
    -   You may want to take advantage of the `icu4j` library. Notice, for
        example, that `NumberScoringCommand.toString` uses this library to
        produce the strings "Ones," "Twos," "Threes," etc through a
        `RuleBasedNumberFormat` object declared in
        `AbstractDieBasedScoringCommand`.
        -   To generate ordinals, create a `RuleBasedNumberFormat` object,
            passing `RuleBasedNumberFormat.ORDINAL` to its constructor.
        -   If you wish, you can access the [Javadoc for
            `RuleBasedNumberFormat`](https://unicode-org.github.io/icu-docs/apidoc/released/icu4j/com/ibm/icu/text/RuleBasedNumberFormat.html)
    -   Make a note of how many lines you changed.
        <!-- 3 additions in AbstractDieBasedScoringCommand.
             6 deletions, 1 modification in SequenceCommand -->
    ```
            Ones        0       One Pair         0
            Twos        0       Two Pair         0
            Threes      0       Three of a Kind  0
            Fours       0       Four of a Kind   0
            Fives       0       1st Straight     0
            Sixes       0       2nd Straight     0
        Left Subtotal   0       Full House       0
        Left Bonus      0       Chance           0
                                Yatzy            0
                            Right Subtotal       0

                                                    Grand Total    0
    ```

1.  As part of preparing for a game that can use 6- or 7-sided dice, take a
    look at `src/main/resources/edu/unl/cse/csce361/yatzy/textview/d6.json`.
    This file contains the die faces. Now create
    `src/main/resources/edu/unl/cse/csce361/yatzy/textview/d7.json` that has
    this as its contents:
    ```json
    {
      "faces": [
        "+-------+%n|       |%n|       |%n|       |%n+-------+",
        "+-------+%n|       |%n|   *   |%n|       |%n+-------+",
        "+-------+%n| *     |%n|       |%n|     * |%n+-------+",
        "+-------+%n| *     |%n|   *   |%n|     * |%n+-------+",
        "+-------+%n| *   * |%n|       |%n| *   * |%n+-------+",
        "+-------+%n| *   * |%n|   *   |%n| *   * |%n+-------+",
        "+-------+%n| *   * |%n| *   * |%n| *   * |%n+-------+",
        "+-------+%n| *   * |%n| * * * |%n| *   * |%n+-------+"
      ],
      "plainBackground":       "           %n           %n           %n           %n           ",
      "highlightedBackground": "X         X%nX         X%nX         X%nX         X%nX         X"
    }
    ```
    (You could simply copy `d6.json` to `d7.json` and add the last face.)
    -   Do not count this as part of your line changes.

1.  As part of preparing for a game that can use 6- or 7-sided dice, make these
    changes to `src/main/java/edu/unl/cse/csce361/yatzy/Game.java`:
    -   Change
        ```java
        public static final int NUMBER_OF_DICE = 5;
        public static final int NUMBER_OF_DIE_SIDES = 6;
        ```
        to
        ```java
        public static final int NUMBER_OF_DICE = 5;
        public static final int NUMBER_OF_DIE_SIDES;

        static {
            NUMBER_OF_DIE_SIDES = promptForNumberOfDieSides();
        }

        enum MenuOption {
            SIX_SIDES("Play traditional Yatzy with 6-sided dice"),
            SEVEN_SIDES("Play Ya7zy with 7-sided dice"),
            EXIT("Exit program");

            private String description;

            MenuOption(String description) {
                this.description = description;
            }

            public String getDescription() {
                return description;
            }
        }

        /**
         * Prompts the player to select the number of die faces and returns that value or exits if no value is chosen.
         * @return the number of die sides the player wants
         */
        private static int promptForNumberOfDieSides() {
            int numberOfSides = 0;
            int intOption = 0;
            MenuOption menuOption = MenuOption.EXIT;
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.println("Welcome to Ya7zy by Procrastination Pastimes!");
                for (MenuOption option : MenuOption.values()) {
                    System.out.println((option.ordinal() + 1) + " -- " + option.getDescription());
                }
                System.out.println();
                System.out.print("Please select your game preference: ");
                intOption = scanner.nextInt();
                scanner.nextLine();     // consume the newline
                menuOption = MenuOption.values()[intOption - 1];
            } catch (InputMismatchException | ArrayIndexOutOfBoundsException ignored) {
                System.out.println("Please select a valid, in-range integer.");
                menuOption = MenuOption.EXIT;
            } finally {
                switch (menuOption) {
                    case SIX_SIDES:
                        numberOfSides = 6;
                        break;
                    case SEVEN_SIDES:
                        numberOfSides = 7;
                        break;
                    case EXIT:
                        System.out.println("Goodbye.");
                        System.exit(0);
                    default:
                        System.err.println("Somehow you selected an impossible option.");
                        System.exit(1);
                }
            }
            return numberOfSides;
        }
        ```
        -   Do not count this as part of your line changes.

1.  Modify
    `src/main/java/edu/unl/cse/csce361/yatzy/view/textview/TextDieView.java`
    so the `dieFacesFile` file is determined by the users input in the previous
    step.
    -   Make a note of how many lines you changed.
        <!-- 1 modification -->

1.  If necessary, modify `ScoreController.addLeftCategories` to display a
    "Sevens" category.
    -   Make a note of how many lines you changed.
        <!-- 0 changes -->
    ```
            Ones        0       One Pair         0
            Twos        0       Two Pair         0
            Threes      0       Three of a Kind  0
            Fours       0       Four of a Kind   0
            Fives       0       1st Straight     0
            Sixes       0       2nd Straight     0
            Sevens      0       Full House       0
        Left Subtotal   0       Chance           0
        Left Bonus      0       Yatzy            0
                            Right Subtotal       0

                                                    Grand Total    0
    ```

1.  Examine
    `src/main/java/edu/unl/cse/csce361/yatzy/model/scoring/BonusCategory.java`
    to determine if any changes are necessary to assure that the Left Bonus is
    now awarded when the Left Section's player-selectable categories sum to 84
    (an average of three dice in each category).

1.  Modify `ScoreController.addRightCategories` and any other code necessary to
    display and score a "3rd Straight" category for the die sequence 3-4-5-6-7
    when the player has chosen to play with 7-sided dice. The "3rd Straight"
    category *must not* be displayed when the player has chosen to play with
    6-sided dice.
    -   A "3rd Straight" is worth 25 points (3+4+5+6+7 = 25)
    -   Make a note of how many lines you changed.
        <!-- Simplest change: 3 additions
        if (Game.NUMBER_OF_DIE_SIDES == 7) {
            addRightCategory(new SequenceCommand(3), categoryModels);
        } -->
        <!-- Changes for arbitrary die sides:
        2 additions, 1 deletion, 1 modification
        for (int i = 0; i < Game.NUMBER_OF_DIE_SIDES - 4; i++) {
            addRightCategory(new SequenceCommand(i + 1), categoryModels);
        } -->
    ```
            Ones        0       One Pair         0
            Twos        0       Two Pair         0
            Threes      0       Three of a Kind  0
            Fours       0       Four of a Kind   0
            Fives       0       1st Straight     0
            Sixes       0       2nd Straight     0
            Sevens      0       3rd Straight     0
        Left Subtotal   0       Full House       0
        Left Bonus      0       Chance           0
                                Yatzy            0
                            Right Subtotal       0
                                                    Grand Total    0
    ```

### Reflection

13. Prepare a file, `REFLECTION.md`, in the top-level directory of your
    repository. Discuss with your partners the following topics and place a
    summary in `REFLECTION.md`:

    1.  Considering only the Software Maintenance portion of this assignment
        (steps 5-12), about how much time did you spend modifying the game to
        be playable with 6- and 7-sided dice? How many lines of code did you
        change (not including steps 7 & 8)? How easy or hard was it to make the
        changes?

    1.  Now that you've modified the game to be playable with 6- and 7-sided
        dice, what changes would be necessary to make it playable with 5-, 6-,
        7-, and 8-sided dice?

    1.  Suppose that you had written the original Yatzy game from scratch using
        only your coding practices and knowledge you had before starting this
        course. Now suppose you had to modify it to accommodate Procrastination
        Pastimes' changed requirements. How easy or hard do you think it would
        have been to make the changes? Make an estimate of how much time you
        would have spent modifying the game.

    1.  What does that tell you about loose coupling, the Dependency Inversion
        Principle, and named constants when it comes to making software
        maintainable?

    1.  Suppose that Procrastination Pastimes wants to change the die
        representations from
        ```
        +-------+ +-------+ +-------+ +-------+ +-------+ +-------+ +-------+
        |       | | *     | | *     | | *   * | | *   * | | *   * | | *   * |
        |   *   | |       | |   *   | |       | |   *   | | *   * | | * * * |
        |       | |     * | |     * | | *   * | | *   * | | *   * | | *   * |
        +-------+ +-------+ +-------+ +-------+ +-------+ +-------+ +-------+
        ```
        to
        ```
            1    222  33333    4  55555   66  77777
           11   2   2    3    44  5      6    7   7
            1       2   3    4 4  5555  6        7  
            1      2     3  4  4      5 6666    7   
            1     2       3 44444     5 6   6  7    
            1    2    3   3    4  5   5 6   6  7    
           111  22222  333     4   555   666   7    
        ```
        What changes would you have to make to accommodate this change request?
        What does that tell you about using configuration files instead of
        hard-coding configuration data?

    1.  You've now seen two examples of menuing systems that are loosely
        coupled from the ordering in which the menu items are presented. The
        Command Pattern (which is suitable for *much* more than menuing
        systems) allows for a very dynamic menu in which items can be added and
        removed at runtime. For the menu that you added to `Game` in step 8,
        that would be overkill. Instead, we iterated over an `enum` that
        contains the menu items.

        Contrast that with a typical text-based menuing system that students
        write:
        ```java
        System.out.println("Welcome to Ya7zy by Procrastination Pastimes!");
        System.out.println("1 -- Play traditional Yatzy with 6-sided dice");
        System.out.println("2 -- Play Ya7zy with 7-sided dice");
        System.out.println("3 -- Exit program");System.out.println();
        System.out.print("Please select your game preference: ");
        intOption = scanner.nextInt();
        scanner.nextLine();     // consume the newline
        switch (intOption) {
            case 1:
                numberOfSides = 6;
                break;
            case 2:
                numberOfSides = 7;
                break;
            case 3:
                System.out.println("Goodbye.");
                System.exit(0);
            default:
                System.err.println("Somehow you selected an impossible option.");
                System.exit(1);
        }
        ```
        If Procrastination Pastimes tasks you with adding an option for 5-sided
        dice, making the menu:
        ```
        Welcome to Ya7zy by Procrastination Pastimes!
        1 -- Play Yat5y with 5-sided dice
        2 -- Play traditional Yatzy with 6-sided dice
        3 -- Play Ya7zy with 7-sided dice
        4 -- Exit program
        ```
        How many lines would you need to change with the "typical student
        menu"? How many lines would you need to change with the enum-based menu?

## Deliverables

For grading, after the assignment is due we will clone the repository that you
identified, and we will look for:

-   Updated source code for your Ya7zy game, with changes based on the
    code review and the new requirements
-   `REFLECTION.md` in the top-level directory

*It is your responsibility to ensure that your work is in the **correct
repository** and that we can access the repository at the **time the assignment
is due**.  We will grade what we can retrieve from the repository at the time
it is due.  Any work that is not in the correct repository, or that we cannot
access, will not be graded.*

## Assignment Rubric

The assignment is worth **25 points**:

-   For each of the six issues from the code review:
    -   **0.25 points** for creating an issue from a code review improvement
        comment
    -   **0.5 point** for addressing the issue
    -   **0.25 points** for closing the issue after it has been addressed

-   For each of the six issues for software maintenance:
    -   **0.25 points** for creating the issue
    -   **0.25 points** for closing the issue after it has been addressed

-   **0.5 points** for renaming the "Straight" categories
-   **0.5 points** for creating `d7.json` as specified
-   **0.5 points** for modifying `Game.java` as specified
-   **1.5 points** for modifying `TextDieView.java` to display the correct dice
-   **1 point** for displaying the "Sevens" category when playing with
    7-sided dice but not when playing with 6-sided dice
-   **1.5 points** for displaying the "3rd Straight" category when playing with
    7-sided dice but not when playing with 6-sided dice
-   **0.5 points** for "Sevens" and "3rd Straight" categories taking correct
    scores

-   **2 points** for using good code & design principles (*e.g.*, DRY, SOLID)
-   **1 points** for coding style

-   **2 points** for performing the reflection and documenting it in
    `REFLECTION.md`

-   **2 points** for making regular commits
-   **3 points** for meaningful commit messages

This assignment is scoped for a team of 2 students. If, despite your attempts
to engage your partner, your partner does not contribute to the assignment then
we will take that into account when grading.

*If **at any time** your repository is public or has internal visibility then
you will receive a 10% penalty. Further, if another student accesses your
non-private repository and copies your solution then I will assume that you are
complicit in their academic dishonesty.*


## Contribution Rubric

The contribution is worth **10 points**:

-   **1 point** for completing peer assessment on time
-   **1 point** for contacting your partner promptly
-   **4 points** for equitable contribution based on peer assessments
-   **4 points** for equitable contribution based on git history
