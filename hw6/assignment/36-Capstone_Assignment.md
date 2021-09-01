# CSCE 361 Capstone Project

-   Sprint 1: July 28 - August 4, 2020
-   Sprint 2: August 5-12, 2020
-   Presentations/Demonstrations:
    -   Presentation slidedeck and recording due: August 13, 2020 at 5pm (CDT,
        UTC-5)

-   Minutes Due: 11:59pm on the day of the corresponding meeting
-   Peer Assessments Due: at end of each sprint
    -   11:59pm on August 4
    -   11:59pm on August 12

In this assignment, you will work on a team of ~~3-4~~ 4-5 students to apply the
principles and practices you learned in CSCE 361 on a multi-week project.

### Objectives

Students will:

-   Gain experience developing software on a team
-   Use the Scrum development process
-   Demonstrate good software engineering practices

## Instructions

This assignment is to be completed in assigned teams; **no collaboration
outside of your teams is permitted**. Twice during the assignment you will need
to complete a peer assessment. Your teamwork grade will be based on the
peer assessments and on the git history.

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

1.  Your team has been be provided with a shared repository with a nearly-empty
    Maven project. There is no need to fork the repository: it is yours to work
    in. Each of you should clone the repository to your local computer and
    import the Maven project into your IDE.

2.  Note that there are portions of the `pom.xml` file that are commented-out.
    If you plan to use JavaFX, MySQL, Hibernate, and/or any of the other
    commented-out libraries, then you must uncomment the relevant sections.

3.  If there are other libraries you need, **do not** place jar files in your
    repository. Instead, search <https://mvnrepository.com/> for the relevant
    dependency to place in your `pom.xml` file.

    *If **at any time** your repository is public or has internal visibility
    then you will receive a 10% penalty. Further, if another student accesses
    your non-private repository and copies your solution then I will assume
    that you are complicit in their academic dishonesty.*

##  Assignment

You and your team have already been assigned your project. The inital
requirements for the project are in an earlier document and are also in the
README.md file in your repository.

### Requirements Elicitation

The requirements are incomplete. This is both accidental and intentional. It is
accidental in that, like all requirements, the customer has expectations that
they didn't realize think to articulate. You will need to elicit the missing
requirements. You may do that by asking questions to clarify vague
requirements, by having me "use" a paper prototype to get feedback, or any of
the other techniques described in the requirements engineering lesson.

There is a [requirements elicitation
thread](https://piazza.com/class/kavslp56w7g7d4?cid=125) on Piazza where I will
add any new requirements as we discover them. All teams will be responsible for
any requirements added to that thread during the first week of the assignment.

The incompleteness of the requirements is also intentional in that I expect
each team to participate in requirements elicitation. *Teams that do not elicit
requirements will be responsible for at least one more requirement than what is
included in that thread.*

**NOTE**: A key Scrum practice is that you do not add scope to a sprint once it
has started. I will allow you to add to sprint 1 any new requirements
discovered by July 29. Any new requirements discovered after July 29 must not
be added to sprint 1's backlog.

### Development Process

You will use a modified Scrum process.

-   You will have two sprints, each lasting <!--1½ weeks--> 8 days.
    Normally, this is too short to be an effective sprint; however, we want you
    to practice a multi-sprint process.
-   You will have a combined kickoff meeting and sprint 1 planning meeting on
    July 28.
    -   You may have an informal kickoff meeting at any time before your
        combined kickoff and sprint 1 planning meeting.
-   You will have a combined sprint 1 review & retrospective and sprint 2
    planning meeting on August 5.
-   Your project retrospective will be part of the preparation for your
    presentation.
-   You will hold scrum meetings on *at least three the other days* for each
    sprint. You may choose to set up a Zoom meeting or meet through any other
    medium your team likes.
    -   You may hold additional scrum meetings. (Normally you would hold one
        per day.)
-   You will not need to provide a burndown chart. Instead, during your daily
    scrums, make a note of which Issues have been closed since the last scrum
    (as well as any new issues that were opened since the last scrum).
-   You will record a short presentation and demonstrate your program. We will
    provide details at a later date.

### Agendas and Minutes

We will provide agendas for the required meetings. You may add material to the
agendas if you wish. The agendas, as discussed in class, are a good starting
point for the meeting's minutes.

Your team will need to record minutes in plain-text or markdown from each
meeting and place them in the `meetings` directory of your repository. The name
of each minutes file shall be `minutes-yyyymmdd.md` where *yyyy* is the year,
*mm* is the two-digit month, and *dd* is the two-digit day of the month. For
example, the minutes from the combined kickoff & sprint 1 planning meeting
would be named `minutes-20200728.md`.

You will have a minimum of four minutes per sprint: the minutes for the sprint
planning meeting and minutes for each of the three required scrum meetings. If
you hold additional scrum meetings, please include minutes for all scrum
meetings.

**The minutes for each meeting are due at 11:59pm CDT (UTC-5) on the day of the
meeting.**

### Backlogs and Issues

Consider the stated requirements (and the game rules, if applicable) to be the
principle source of your product backlog. There will be other sources for your
product backlog, such as any additional requirements we give you and the need
for a user interface.

In each sprint planning meeting, decide on the goal for the sprint and
formulate the sprint backlog.

Following the Scrum practice of self-organizing teams, each team member should
assign Issues to themselves. Equally dividing Issues at the start of a sprint
is *not* a Scrum practice. Instead, when a team member has no Issues to work
on, they assign an Issue (or Issues) to themselves -- in our case, you'll do
that through the GitLab web interface.

Note: it may be that some issues will require more than one team member, but
GitLab's Issue Tacker will only let you assign it to one person. That's okay.
The purpose of self-assigning Issues is to make sure nobody's duplicating
effort. The Issue Tracker will *not* be used to judge contribution levels.

#### Issue Flow

Your repository has two Milestones (from the web interface left-side menu,
select `Issues` -> `Milestones` to see them). When you create an Issue, you can
leave it unattached to any Milestone, or you can assign it to a Milestone. When
you decide that an Issue is part of your sprint 1 backlog or part of your
sprint 2 backlog, attach it to the relevant Milestone (from the web interface,
select the Issue, and on the right-side menu there is an option to attach the
Issue to a Milestone).

The Milestone page conveniently shows which Issues attached to the Milestone
are open and not assigned to anyone, which are open and assigned, and which are
closed. This is a convenient way to observe your progress in the absence of a
burndown chart. (The Enterprise Edition of GitLab has a built-in tool that
creates burndown charts based on Issue status. The Community Edition that UNL
uses does not.)

**The Issues attached to the `Sprint 1` Milestone and to the `Sprint 2`
Milestone will constitute your sprint backlogs.**

#### Adding Additional Issues Mid-Sprint

A key Scrum practice is not to add scope to a sprint after the sprint has
begun. If you realize there is something to be done that you hadn't thought of
before, it should be placed in a future sprint.

You can, however, add new Issues to the current sprint's backlog in two
circumstances:

-   If you realize that completing an Issue that is part of the current sprint
    requires that you first complete another task, you can create an Issue for
    that other task and place it in the current sprint's backlog. (Please
    indicate in the Issue's comments that it is a prerequisite for the other
    Issue.)
-   If you have exhausted the current sprint's backlog then you may, only with
    the professor's concurrence, add scope to the current sprint.

### Tests

Use your best judgement when deciding whether and when to create unit tests.

If you find that you need to debug code, I strongly encourage you to create a
JUnit test demonstrating the bug, and then use the debugger to run the test and
step-through its execution to see where the internal values and/or the
behavior deviates from what you expected.

<!-- ***Note: The Honors teams* must *create JUnit tests as part of Test-Driven
Development.*** -->

### Design

Use your best judgement when designing your program. We will look for evidence
of applying the design principles covered in class.

### Persistent Data

If you need to store persistent data, you may do so using a CSV file, a JSON
file, a database (using either Hibernate or JDBC), or some other reasonable
means. If you are using a database and store your authentication credentials in
a resources file (such as `resources/database.properties` or
`resources/hibernate.cfg.xml`), be sure to place that file is listed in your
`resources/.gitignore` file. Also be sure to include a template for us to
create our own resources file with our authentication credentials.

<!-- **I do not anticipate that any of the projects would require you to remove
persisted data, so the difficulties we had with `PettingZoo.sellAnimal()` will
not appear in the capstone.** -->

### External Libraries

If you need external libraries, **do not** place jar files in your repository.
Instead, search <https://mvnrepository.com/> for the relevant dependency to
place in your `pom.xml` file.

### Model

Place a class diagram in your repository at the end of each sprint. (You may
update it more frequently if you wish.) We do not require sequence diagrams nor
any other UML diagrams. Using a tool to auto-generate this diagram from your
source code is acceptable; a hand-prepared class diagram is also acceptable.
The tradeoff is that an auto-generated diagram is guaranteed to be consistent
with the code, but a hand-crafted diagram is going to be able to depict the
useful information and only the useful information.

If you wish to break your class diagram into multiple diagrams (and if your
tool supports this) then this is acceptable if it improves readability/
understandability.

The diagram(s) must be in pdf, jpg, or png format. Before saving the diagram,
please arrange the classes so the structure of the system is clear.

### Instructions to build and run the program

Add to `README.md` any special instructions we'll need to build and run your
program, such as any dependencies that aren't in the source code or the pom.xml
file. If we cannot compile and run your program, we cannot grade the
functionality.

##  Deliverables

-   Source code for your application
-   Minutes from your meetings
-   Class diagram
-   Updated `pom.xml` and `README.md` (as necessary)

*It is your responsibility to ensure that your work is in the **correct
repository** and that we can access the repository at the **time each
deliverable is due**.  We will grade what we can retrieve from the repository
at the time it is due.  Any work that is not in the correct repository, or that
we cannot access, will not be graded.*

##  Miscellanea

-   **Copying models or code off of the internet and placing it into your
    deliverables is completely out of the question.**  You may look at examples
    to understand something you're trying to teach yourself, but *you must
    indicate these sources in the deliverables.*
-   (If you implement a GUI) You may copy artwork from outside sources to
    include in your project if and only if you have the license to do so.  If
    the artwork is in the public domain or is licensed under a Creative Commons
    license then you likely do not need to take explicit action to obtain the
    license.  Otherwise, you probably will have to pay for the license (be sure
    that the license permits redistribution).  *Be sure to comply with the
    terms of the license, whether it's a Creative Commons license or a
    commercial license, and be sure to indicate the source in the deliverables*.
-   **Underperforming team members** If a student is habitually failing to be a
    contributing member of your team beyond your ability to resolve, you may
    ask your TA or the professor for help resolving the problem.  In extreme
    circumstances, the professor may remove that student from the team.  This
    can occur only *after* we've discussed your team's dilemma and
    concurred that no better option is available.  A fired team member may
    complete the project on his/her own, or join a group with other fired team
    members (if any exist).
-   **Toxic team members** If a student's behavior is harming the other team
    members' ability to learn and perform, the professor may remove that
    student from the team.  This can occur only *after* we've discussed your
    team's dilemma and concurred that no better option is available.  A fired
    team member may complete the project on his/her own, or join a group with
    other fired team members (if any exist).  If necessary, the case will be
    referred to Student Affairs for appropriate action.

## Assignment Rubric

The assignment is worth **100 points**:

-   **8 points** for providing meeting minutes (1 point per meeting).
-   **15 points** for implementing the functionality as required.
-   **10 points** for following Scrum practices.
-   **16 points** for making good design decisions.
-   **16 points** for using good coding style.
-   **6 points** for class diagram (3 points per sprint)
-   **10 points** for meaningful and well-formatted commit messages.
-   **9 points** for otherwise following good software engineering practices.
-   **10 points** for presentation/demonstration.

## Contribution Rubric

The contribution is worth **20 points**:

-   **1 point** for completing sprint 1 peer assessment
-   **1 point** for completing sprint 2 peer assessment
-   **5 points** for equitable contribution based on sprint 1 peer assessments
-   **5 points** for equitable contribution based on sprint 2 peer assessments
-   **8 points** for equitable contribution based on git history

## Extra Credit

Your team may earn up to *5 points* of extra credit (equivalent to 1 percentage
point on your final course grade). Generally, this will possible by
incorporating a course topic that is not required for the capstone assignment
and was not covered in a previous assignment.

-   If your team practices test-driven development for the full capstone
    assignment, that is worth 5 points. (Not available for an Honors team.)
-   If your team practices pair programming for the all (or almost all) of the
    capstone assignment, that is worth 5 points.
-   If your project has an "or" requirement (*i.e.*, it must have a GUI or be
    network-based) then you may turn that "or" into an "and" for 3 points.
-   If your team's project has a GUI then explicitly making accessibility a
    requirement for your software is worth 2 points.
-   Making diversity a process requirement is worth 2 points (having a diverse
    team is not sufficient: by the end of sprint 1, you must have a plan to
    make your software appealing to a diverse market; by the end of sprint 2,
    you must show that you worked to implement that plan).
-   Many other, similar, options are possible. If you have a suggestion,
    discuss it with the professor early in Sprint 1.

If your team attempts more than one extra-credit activity, the bonus points will
accumulate up to a maximum of 5 points.
