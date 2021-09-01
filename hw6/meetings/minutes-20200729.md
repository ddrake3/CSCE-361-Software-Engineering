#   (ElectronicVoting-2 / Voting System Group)

July 29, 2020

## Project Kickoff and Sprint 1 Planning Meeting

### Project Kickoff

-   Discuss project and project's scope
-   Prioritize product backlog
-   Prepare initial (non-binding) plan for project
-   Decide on branching strategy
-   Decide on coding style
-   Decide on commit style
-   Other project-level matters

### Sprint 1 Planning Meeting

-   Identify sprint goal: Make the system works (ignore additional features)
-   What backlog items are in this sprint?
-   Prepare sprint backlog
    -   Assign Sprint 1 Issues to `sprint 1` milestone

-   Managing git
    -   Use git issue board to create user stories and manage their corresponding issues
    -   Create git staging, and have our own development branch
    -   Use angular commit style

-   Creating GUI instead of using text-mode
    -   Layout: 375*667 (iPhone 6 dimension)

-   User stories
    -   Creating account
        -   As a voter, I want to register my account using name, address, and a userId (such as driving license, SSN, etc).
    -   Maintaing account
        -   As a voter, I need to type in a unique code (authentication) to login.
        -   As a voter, I want to be able to view my vote and change them before the time end.
        -   As a voter, I can choose not to vote (no vote).
    -   View statistics
        -   As an auditor, I want to be able to view if a specific voter has voted.
        -   As an auditor, I want to be able to check who is the winner for a ballot with numbers of vote.
    -   Managing the ballot
        -   As a election official, I want to be able to create a new ballot, listing position and names.
        -   As a election official, I want to make sure the ballot could not be changed after published.
        -   As a election official, I want to be able to create propositions.
    -   Authentication method
        -   Leave to sprint 2.

-   Design pattern
    -   Three tier design pattern
    -   Builder pattern in Logic layer
    -   Facade pattern for each layer

-   EXTRA CREDIT
    -   Will add test-driven development

120 minutes meeting









