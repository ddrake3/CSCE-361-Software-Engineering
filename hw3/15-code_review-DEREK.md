# Code Review

## Code Under Review
Yatzy Game

## Presenter
Anthony Turner

## Reviewer
Derek Drake

## Comments

1. Negative: addRightCategories in ScoreController
	- Reason: Missing quite a few of the categories.  
	- Suggestion: Add ThreeOfAKind, FourofAKind, Yatzy, SmallStraight, LargeStraight, FullHouse, and Yatzy.

2. Negative: addRightCategories in ScoreController
	- Reason: Instead of creating a new instance of OfAKindCommand or OfTwoKindsCommand in ScoreController, create a separate class and call the categories in the constructor of your new class.
	- Suggestion: Take a look at the ChanceCommand class in controller/scoring for an example.

3. Negative: notifyObservers in AbstractSubject
	- Reason: I think a foreach loop would be much more suited here.  Generally, use for loops when you have a defined range of values to iterate over, i.e. the size of the list.  Use while loops when you don't know when your exit condition will be true.
	- Suggestion: Change while loop to foreach loop to increase the readability of your code.

4. Negative: notifyObservers call in Die
	- Reason: You should only be calling notifyObservers in the cases where the value has been updated.  Where you method call is placed right now, it will always get called, even if an exception is thrown.
	- Suggestion: Move the method call to inside of the if statement.

5. Negative: addLeftCategories in ScoreController
	- Reason: Magic number violation on line 56.  Instead of out 6, you should have called the constant NUMBER_OF_DIE_SIDES.  I know this is a weird topic to get used to because I did the same thing in my assignment.
	- Suggestion: Use constants instead of magic numbers to improve readability of your code.

6. Negative: toString in OfAKindCategory
	- Reason: This goes hand-in-hand with #2.  Instead of creating the toString method in the category classes, you could have implemented it in each of the respected classes for the commands.
	- Suggestion: The ChanceCommand class is a good example for this again.

7. Positive: update in AbstractDieView
	- Reason: Specifying that this method should only update the observer if a DieSubject object references it increases your code readability.  Nice Job!

8. Positive: commit messages
	- Reason: Your commit messages were very clean and to the point.  Nicely done!
