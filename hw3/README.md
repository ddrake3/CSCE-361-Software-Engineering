# Yatzy

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