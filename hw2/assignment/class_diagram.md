# Class Diagram

Due: June 12, 2020 at 11:00am

## Overview

In this assignment you will prepare a simple class diagram.

## Objectives

Students will:

-   Prepare a simple class diagram
-   Practice domain modeling under guidance
-   Demonstrate UML notation to show relationships between classes

## Instructions

This assignment is to be completed individually; **no collaboration is
permitted**.

You may draw the diagram using a tool, or you may hand-draw it. *You may
**not** use a tool that generates UML diagrams from programming language code.*
If we see hallmarks of auto-generated UML, we will ask you to show us the tool
you used to prepare the diagrams. If you used a tool that auto-generates UML,
you will receive a score of 0 for this assignment.

### Setup

1.  You will use your `csce361-homework` repository for this assignment.
1.  You will need one of:
    -   A tool with which to draw UML diagrams, such as <https://draw.io>, that
        will let you save your diagram as a pdf file.
    -   A way to scan a hand-drawn diagram and save it as a pdf file. (If you
        hand-draw the diagram, you may wish to prepare the diagram with a
        pencil so you can make erasures.)

### Assignment

Procrastination Pastimes has decided to produce the game of
[Yatzy](https://en.wikipedia.org/wiki/Yatzy) (a public domain dice game similar
to [Yahtzee®](https://en.wikipedia.org/wiki/Yahtzee)). After a particularly
unfortunate incident involving dice, a slingshot, and a mug full of very hot
coffee, the company elected to produce a computerized version of the game
instead.

Prepare a domain-level UML *class diagram* for the relationships between a
game, dice, scoring categories, and similarly-related domain concepts. Because
this is domain-level, you don't need to include details such as visibility
modifiers or datatypes.  Because this is domain-level, you shouldn't include
anything not present in the problem domain; in particular, you shouldn't
include implementation details such as GUI frames and textboxes.

1.  A game has five dice and a scoresheet with several scoring categories. The
    scoring categories include number-based categories ("Ones," "Twos," etc.),
    matching categories ("One Pair," "Full House," etc.), and sequence
    categories ("Small Straight" and "Large Straight").
    -   Show that `Die` and `ScoringCategory` are part of `Game`.
    -   Use multiplicity to show that each `Game` has 5 `Die` objects. Leave
        the multiplicity for the relationship between `Game` &
        `ScoringCategory` unspecified.
    -   Show that `ScoringCategory` is an abstract class.
    -   Show that `NumberCategory`, `MatchingCategory`, and `SequenceCategory`
        inherit from `ScoringCategory`.

1.  Each die has six sides; the side facing up is the die's value. We assume
    fair dice, so when a die is rolled, each value has an equal likelihood of
    facing up.
    -   Give the `Die` class a `value` attribute, of type `Integer`. (Optional:
        show that the attribute is constrained by placing `{1≤value≤6}`
        after the type.)
    -   Give the `Die` class a `roll()` operation.

1.  Each scoring category has a score. Initially, the score is 0. When the
    player uses the dice' current values for a scoring category, that
    category's score may change (normally, the score will change, but it is
    possible to score 0 in a scoring category). Once the player has used a
    scoring category, its score cannot be changed again for the duration of the
    game. How the score is calculated will depend on the particular scoring
    category.
    -   Give the `ScoringCategory` two attributes, `score`, of type `Integer`,
        and `hasBeenScored`, of type `Boolean`.
    -   Give the `ScoringCategory` an abstract method `assignScore()` that has
        one parameter, a collection of dice.
    -   Show that `ScoringCategory`'s subclasses override `assignScore()`.

1.  A number-based category has the die value that is counted for score.
    -   Give the `NumberCategory` an appropriately-named attribute for this
        value.

1.  A sequence category has the lowest value at the start of the sequence
    (alternatively, the greatest value at the end of the sequence) to identify
    which sequence it is.
    -   Give the `SequenceCategory` an appropriately-named attribute for this
        value.

1.  A MatchingCategory has the number of dice that must satisfy the first match
    and the number of dice that must satisfy the second match. If only one
    match is necessary, then we implicitly understand that the number of dice
    needed to satisfy the "second match" is 0. For example, "Four of a Kind"
    requires 4 dice to satisfy the first match and 0 to satisfy the second,
    whereas "Two Pairs" requires 2 dice to satisfy the first match and 2 dice
    to satisfy the second match.
    -   Give the `MatchingCategory` appropriately-named attributes for this
        value.

1.  After you have completed your diagram, save it as a pdf file called
    `03-class_diagram.pdf` in your local copy of your `csce361-homework`
    repository.  Then upload (add/commit/push) to the server.


### Deliverables

For grading, we will pull updates to your `csce361-homework` repository after
the assignment is due, and we will look for:

-   `03-class_diagram.pdf` containing your class diagram.

*It is your responsibility to ensure that your work is in the master branch of
the **correct repository** and that we have the correct level of access to the
repository at the **time the assignment is due**.  We will grade what we can
retrieve from the repository at the time it is due.  Any work that is not in
the correct repository, or that we cannot access, will not be graded.*

## Rubric

The assignment is worth **8 points**:

-   **3.0 points** for including the six classes (0.5 per class).

-   **2.5 points** for including the class members as described (0.5 for each
    of `Die`, `ScoringCategory`, `NumberCategory`, `MatchingCategory`,
    `SequenceCategory`).
    -   Deduct for missing members
    -   Deduct for members in the wrong box
    -   Comment upon attributes named with verbs or operations named with nouns
    -   Comment upon providing type-signatures for operations without parameter
        names

-   **0.5 points** for showing that `ScoringCategory` is an abstract
    class, and that `ScoringCategory`'s `assignScore()` method is abstract
    (either with *italics* or with {abstact}).

-   **0.5 points** for showing inheritance between `ScoringCategory` and its
    subclasses, `NumberCategory`, `MatchingCategory`, & `SequenceCategory`.
    -   Correct notation at correct end

-   **0.5 points** for showing aggregation between `Game` and `Die` &
    `ScoringCategory`
    -   Correct notation at correct end
    -   Correct multiplicity for the `Game`-`Die` association

-   **0.5 points** for not including anything else wrong (TA discretion).

-   **0.5 points** for using a meaningful commit message when committing your
    file.


*If **at any time** your repository is public or has internal visibility then
you will receive a 10% penalty. Further, if another student accesses your
non-private repository and copies your solution then I will assume that you are
complicit in their academic dishonesty.*
