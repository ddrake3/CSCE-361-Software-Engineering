# Voting System

The city of Pacopolis wants you to develop an electronic voting system. While
the mayoral race between Pat Mann and Dawn Keykong is the most visible
election, there are other elections and issues that will also need to be
decided. And, of course, the electronic voting system must be usable in future
elections, too.

-   The ballot for the November 2020 election consists of:
    -   Mayor: Pat Mann vs. Dawn Keykong
    -   City Council seat: Inky vs. Blinky
    -   Sheriff: Q. Burte, no opponent
    -   Proposition 1: Shall there be a 25¢ tax on cherries?
    -   Proposition 2: Shall liquor licenses be required for electronic bars?
    -   Proposition 3: Shall electronic race tracks be held liable for
        electronic car crashes?

-   A non-technical person must be able to create the ballot for a new
    election.

The system shall allow a voter to identify themselves through authentication,
after which they shall be presented with the ballot. After making their
selections, the voter shall be offered the opportunity to review and change
their selections. Once submitted, the voter's choices shall be recorded. The
system shall prohibit a voter from voting more than once in the same election.
At the end of the voting day, the system shall determine the winner of each
election and the outcome of each issue.

While it shall be possible for a voter to later view their recorded vote to
confirm that it was recorded correctly, and it shall be possible for someone
such as an auditor to determine *whether* a particular voter voted, it shall
be impossible for anyone other than the voter to determine *how* any
particular voter voted.

-   "Anyone other than the voter" includes database administrators and
    system administrators.

The system shall allow an unlimited number of voters to vote from their own
computers or from a shared computer at a polling location.

-   The system may be implemented in text-mode or with a GUI.

## Instruction

### Voter
1. Click on voter at the home page.
2. Sign up an account by choosing register, the application will generate an authentication code.
3. Use authentication code and name to sign in and start voting on position and proposition questions.
4 Click on forget authentication code and fill in with the name and SSN to get authentication code
5. Review questions and answers to confirm that it is recorded correctly.
6. Click on submit, the application will switch to confirmation page if it is successfully voted and answered.
7. An election code is generated for future review on that election
8. Click sign out to return home page and allow others to vote.

### Auditor
1. Click on committee at the home page.
2. Sign up an account for the auditor with the permission code of 123456.
3. Login to the auditor page.
4. Fill in the ballot name to check voter statistics, ballot statistics, and final results.

### Election Official
1. Click on committee at the home page.
2. Sign up an account for the election official with the permission code of 123456.
3. Login into the election official.
4. Click on audit for auditor functionality.
5. The table view display the status and presents of the ballot in database.
6. Key in the ballot name to add/edit the ballot
7. Next, The table view shows the presents position in the ballot, add new position table creates a new position
8. Next, the table view shows the presents proposition in the ballot, and new proposition and keyword should 
create a new proposition.