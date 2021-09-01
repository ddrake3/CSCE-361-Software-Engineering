# Yatzy Game

-   Assignment Due: June 29, 2020 at ~~11:00am~~ 3:00pm (CDT, UTC-5)
-   Peer Assessment Due: June 29, 2020 at 11:59pm

In this assignment you will implement a simple dice game.

##  Objectives

Students will:

-   Gain additional experience developing software with a partner
    -   Gain experience using a version control system with multiple developers
    -   Gain experience using an issue tracker to coordinate efforts
-   Demonstrate good software engineering practices
-   Learn three design patterns
    -   Command Pattern
    -   Observer Pattern
    -   Template Method Pattern
-   Learn the Model-View-Controller architectural style

##  Instructions

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

1.  You and your partner will work on a shared repository, which has been
    prepared for you.

    1.  Navigate to your shared directory
        (<https://git.unl.edu/csce_361/summer2020/12pairNN/>, where *NN* is your
        team number).

    1.  Verify that the repository is private, and that you and your partner
        both have Maintainer access.

1.  Both students should:

    1.  Clone the project: `git clone <URL>` (here the angle brackets should
        not be included).

        -   **Do *NOT* place your yatzy repository inside your
            csce361-homework repository!**

    1.  Import the project into your IDE. The project is set up as a Maven
        project, so you can follow your IDE's instructions to import a Maven
        project.

##  Issue Tracker

We have pre-populated your repository's Issue Tracker with issues for the
various parts of the assignment that need to be completed. **We do *not*
guarantee that the pre-populated issues are complete; this document is the
authoritative source of requirements.**

-   A good way to coordinate who is working on which parts of the code is to
    use the [web interface](https://docs.gitlab.com/ee/user/project/issues/index.html#issue-page)
    to "assign" an issue to a team member.
-   You may [add more issues](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#create-a-new-issue)
    to the Issue Tracker if you wish. This is common when you discover more
    tasks that need to be accomplished or when you want to divide an existing
    issue into finer-grained tasks (the original issue would still exist, but
    the finer-grained issues may make it easier to divide the work).
-   When you have completed a task, [close the corresponding issue](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#closing-issues).
    You can do this through the issue tracker, or you can do it through a
    commit message.
    -   To close an issue through a commit message, include a keyword such as
        `closes #12` in a commit message. You cannot create a commit message
        only to close an issue; this must be a commit message for adding/
        changing/removing files or for a merge.  See <https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#default-closing-pattern>
        for keywords that will close an issue.

##  Assignment

Procrastination Pastimes has decided to produce the game of
[Yatzy](https://en.wikipedia.org/wiki/Yatzy) (a public domain dice game similar
to [Yahtzee®](https://en.wikipedia.org/wiki/Yahtzee)). After a particularly
unfortunate incident involving dice, a slingshot, and a mug full of very hot
coffee, the company elected to produce a computerized version of the game
instead.

Look over the starter code, view
[this short demonstration](https://use.vg/yf2idk) and run the program to get a
feel for how the program works.

### Architecture

The starter code has a Model-View-Controller (MVC) architecture. Review this
architectural style in *Software Engineering* pp162-163 and *Engineering
Software Products* pp104-105. As you add to the starter code, preserve the MVC
architecture.
-   Code that displays information to the user must be in the *view* subsystem.
    -   In this particular MVC variant, input from the user is obtained through
        the view subsystem before being passed to the controller subsystem.
        This design choice was made because the original version of this code
        supports multiple user interfaces. For simplicity, *this* version
        supports only a text-based user interface, and the remaining
        inheritance hierarchy was collapsed, except for `AbstractDieView`.
-   Code that coordinates the game is in the *controller* subsystem.
-   Code that represents problem-domain concepts is in the *model* subsystem.
    This code does not need to exactly mirror the problem-domain concepts, but
    this is where the game state belongs.

With very limited exceptions, code in one subsystem is not allowed to depend on
concrete classes in another subsystem. The limited exceptions are:
-   In the starter code, some classes in the controller subsystem instantiate
    concrete classes as part of initializing the game. Outside of these
    instantiations, all code that references objects from a different subsystem
    do so using interfaces.
    -   There is a way to eliminate even these dependences, but that is a
        design pattern for another day
-   Your code may reference constants declared in the `Game` class, any public
    `Enum` types, any code within the same subsystem, and interfaces in other
    subsystems.

### Functional Requirements

The [Wikipedia page for Yatzy](https://en.wikipedia.org/wiki/Yatzy) contains
the description of the game. You will implement a one-player version.
-   There are five dice. In each turn, the player may roll some or all of the
    dice up to three times.
-   After the first or second roll, the player *may* score the dice against a
    scoring category and end the turn. After the third roll, the player *must*
    score the dice against a scoring category and end the turn.
-   The scoring categories are generally grouped into the "Upper Section" and
    the "Lower Section"; however, due to the display's aspect ratio, we will
    refer to them as the "Left Section" and the "Right Section."
    -   The Left Section has these scoring categories:
        -   Ones:   The sum of all dice showing 1 pip
        -   Twos:   The sum of all dice showing 2 pips
        -   Threes: The sum of all dice showing 3 pips
        -   Fours:  The sum of all dice showing 4 pips
        -   Fives:  The sum of all dice showing 5 pips
        -   Sixes:  The sum of all dice showing 6 pips
        -   Bonus (not player-selectable): if the Left Section's player-
            selectable categories sum to 63 (an average of three dice in each
            category), then the player receives a 50-point bonus
    -   The Right Section has these scoring categories:
        -   One Pair (2 dice showing the same number of pips), scored as the
            sum of those 2 dice
        -   Two Pair (2 different pairs of dice), scored as the sum of the dice
            in those pairs
            -   The face-value of the two different pairs *must* be different;
                for example, 1-2-2-3-3 may be scored as Two Pair, but 1-2-2-2-2
                may not be scored as Two Pair
        -   Three of a Kind (3 dice showing the same number of pips), scored as
            the sum of those 3 dice
        -   Four of a Kind (4 dice showing the same number of pips), scored as
            the sum of those 4 dice
        -   Small Straight (the combination of 1-2-3-4-5), scored as the sum of
            those 5 dice
            -   Note that this is *not* the same definition of a "small
                straight" in Yahtzee®
        -   Large Straight (the combination of 2-3-4-5-6), scored as the sum of
            those 5 dice
            -   Note that this is *not* the same definition of a "large
                straight" in Yahtzee®
        -   Full House (three of a kind and a pair), scored as the sum of those
            5 dice
            -   The face-value of the pair *must* be different than the face-
                value of the three of a kind; for example, 2-2-2-3-3 may be
                scored as a Full House, but 2-2-2-2-2 may not be scored as Full
                House
        -   Chance (any combination of dice), scored as the sum of those 5 dice
        -   Yatzy (5 dice showing the same number of pips), scored as 50 points
    -   Dice that do not conform to a scoring category's requirements may be
        assigned to that category, but they will receive a score of 0.

### Observer Pattern

You will use the *Observer Pattern* to update the view when the dice are rolled.

-   HFDP, [Chapter 2](https://learning.oreilly.com/library/view/head-first-design/0596007124/ch02.html)

Because Java's implementation of the Observer Pattern is deprecated, we will
use our own implementation.

1.  The methods in `AbstractSubject` currently do nothing. Implement the
    `registerObserver`, `removeObserver`, and `notifyObservers` methods to
    provide the behavior required of the subject in the Observer Pattern.

Now make use of the Observer Pattern. The starter code already has the
registration calls; you need to:

2.  In the starter code, the `rollDice` method in `controller/DiceController`
    updates the view with each die's new value. Locate and delete this line:
    ```
    dieTuple.view.setValue(dieTuple.model.getValue());
    ```

1.  Update the `roll` method in `model/die/Die`. Because this is the method
    where something interesting happens, it needs to call the `notifyObservers`
    method.

1.  The `view/AbstractDieView` is an observer. Implement the `update` method in
    `AbstractDieView`.

1.  Run the game to confirm that the dice that are displayed update when they
    are rolled.

### Template Method Pattern

You will use the *Template Method Pattern* to obtain the scoring categories'
scores.

-   HFDP, [Chapter 8](https://learning.oreilly.com/library/view/head-first-design/0596007124/ch08.html)

The `model/scoring/AbstractDieBasedCategory` class is the base class for the
scoring categories that use dice to determine the score (as opposed to the
scoring categories that use the sums of other scoring categories to
determine their scores). Unlike Yahtzee®, almost all of the die-based scores
are the sums of a subset of the dice. This means that the
`getHypotheticalScore` can be implemented in `AbsttractDieBasedCategory` if it
had a way to get the dice that should be added together.

6.  Create a
    `protected abstract List<DieModel> getSatisfyingDice(List<DieModel> dice)`
    method in `AbstractDieBasedCategory`.

1.  Implement `getHypotheticalScore` in `AbstractDieBasedCategory` to use
    `getSatisfyingDice` to produce the score that the dice would yield, if the
    dice were assigned to the scoring category.

1.  Implement the methods in `AbstractMatchingCategory`, `NumberCategory`,
    `OfAKindCategory`, `OfTwoKindsCategory`, `SequenceCategory`, and
    `ChanceCategory`.
    -   Except for `OfAKindCategory`, do *not* override `getHypotheticalScore`
        in the subclasses.
    -   You do need to override `getHypotheticalScore` in `OfAKindCategory`
        because a "Yatzy" (five of a kind) is scored as 50 points instead of as
        the sum of the dice. Override `getHypotheticalScore` and use
        `super.getHypotheticalScore` for the cases that are not "Yatzy."

**NOTE** The `model/scoring/MatchingCategoryTest` test class can be used to
test your implementations of `OfAKindCategory.isSimpleMatch` and
`OfTwoKindsCategory.isCompoundMatch`. The `model/scoring/CategoryModelTest`
test class can be used to test the full implementations of each of
`AbstractDieBasedCategory`'s subclasses.

### Command Pattern

You will use the *Command Pattern* to respond to user input.

-   HFDP, [Chapter 6](https://learning.oreilly.com/library/view/head-first-design/0596007124/ch06.html)


9.  Examine `controller/Command`.
    -   Notice that it declares an `execute()` method, just like HFDP shows.
    -   Notice that it also declares `toString()`. Even though this method is
        already declared as part of Java's `Object` base class, we re-declare
        it in `Command` to instruct implementers what `toString()` should
        return.

1.  Examine `DiceController`, `NavigationController` and `ScoreController`.
    Notice that their constructors (and helper methods) instantiate `Command`
    objects and pass them to the game board.

1.  Examine the `playGame` method in `view/textview/TextGameBoard`.
    Specifically, look at the two lines in the `try` block. **Observe that the
    code in `TextGameBoard` has no knowledge of what these commands are and
    what they do.** This code is decoupled from what actions the user can take:
    Changing what options are available to the user will require no change to
    any code in the view subsystem.

1.  Examine `controller/navigation/RollDiceCommand` and other `Command` classes
    to get a sense of how much logic is appropriate for the `execute` method.

1.  The `addLeftCategories` method in `ScoreController` creates only the "Ones"
    category. Modify it to create the "Twos," "Threes," "Fours," "Fives," and
    "Sixes" categories.

1.  Modify `model/scoring/BonusCategory`'s constructor so that it expects all
    six scoring categories instead of just one.

1.  The `Command` classes for the die-based scoring categories would all have
    identical code in their `execute` methods (as well as other methods), and
    so we have created `controller/scoring/AbstractDieBasedScoringCommand`.
    Implement the `execute` method in `AbstractDieBasedScoringCommand`.

1.  Run the game to check that you can now assign scores and that the scores
    are reflected on the scoresheet.

1.  Implement additional die-based scoring commands for the missing Right-Side
    scoring categories.

1.  Modify the `addRightCategories` method in `ScoreController` to create the
    missing Right-Side categories.

1.  Run the game to check that all scoring categories are displayed.

### Preparation for Next Assignment

20. After you've finished this assignment, and *no later than 1 hour after this
    assignment is due*, each student must fork a copy of the repository to
    their own gitlab account.
    -   Add the professor, the TA, and your new partner as *Maintainers* to
        your forked copy.
    -   When your new partner gives you access to their repository, **do not**
        fork their repository.

## Deliverables

For grading, we will pull the `yatzy` repositories after the assignment
is due, and we will look in the Maven-conventional directories for:

-   Source code for your Yatzy game

*It is your responsibility to ensure that your work is in the master branch of
the **correct repository** and that we have the correct level of access to the
repository at the **time the assignment is due**.  We will grade what we can
retrieve from the repository at the time it is due.  Any work that is not in
the correct repository, or that we cannot access, will not be graded.*

## Rubric

The assignment is worth **34 points**:

-   **4 points** for preserving the MVC architecture
    -   Code that contains the game state is in the model
    -   Code that interacts with the user is in the view
    -   Code that coordinates the game is in the controller
    -   Code in one subsystem does not depend on concrete classes in other  
        subsystems (exception: you may *instantiate* concrete CategoryModel
        classes in the Controller classes' constructors)

-   **5 points** for implementing and using the Observer Pattern
    -   `AbstractSubject` implemented
    -   `Die.roll` calls `notifyObservers`
    -   `AbstractDieView.update` implemented
    -   No code (other than the view) explicitly updates the view

-   **4 points** for implementing and using the Template Method Pattern
    -   `AbstractDieBasedCategory.getHypotheticalScore` uses protected abstract
        method as part of computing the score
    -   That method is overridden in each subclass as appropriate
    -   `getHypotheticalScore` is *not* overridden in any subclasses except for
        the Yatzy special case

-   **5 points** for implementing and using the Command Pattern
    -   `AbstractDieBasedScoringCommand.execute` implemented, delegating its
        behavior
    -   All die-based scoring Commands implemented
    -   All scoring categories added to game board

-   **4 points** for the game meeting all of its functional requirements

-   **4 points** for using good code & design principles (*e.g.*, DRY, SOLID)

-   **2 points** for coding style

-   **1 point** for forking a copy of the `12pairNN` repository to your own
    gitlab account and giving the professor, the TA, and your new partner access

-   **2 points** for making regular commits throughout the project

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

## Footnote
