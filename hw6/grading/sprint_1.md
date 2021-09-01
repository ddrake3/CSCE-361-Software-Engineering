#   Grading notes for Sprint 1

`sprint_1` tag not found, so I used
```
git checkout `git rev-list -1 --before="2020-08-05 00:01" master`
```

-   GUI looks good

##  Meeting Minutes

4/4

-   ~~No minutes for scrum meetings~~
    -   Minutes were in repo; for some reason weren't visible in the
        20200805/0001 snapshot but were visible in earlier snapshots

##  Scrum Practices

4.75/5

-   ~~No evidence of scrum meetings (-1.5)~~
-   Scope added to sprint backlog after sprint had begun ~~(-0.5)~~
    -   On 7/31 & 8/4, issues added to sprint 1 backlog that were not
        originally part of sprint 1 backlog (-0.25)

##  Class Diagram

1/3

-   Class diagram is very incomplete (-2)

##  Design

For Sprint 1, I am only looking at whether you have an overt architecture and
are maintaining the separation of concerns.

2/2

-   Appears to be a 3-layer architecture; partitions look reasonable

##  Code Style

For Sprint 1, I am making a cursory check for compliance with your team's code
style and running static analysis.

2.55/3

-   `VoteCartEntity.getVoteCartByBallotAndQuestion()` uses `break` to exit a
    loop (-0.25)
-   Typos: "sucessfully" (completion.fxml), "delim" (VotingLogic), "Anteloupe" (
    DatabasePopulator)
-   There appear to be 15 unresovled fx:id references -- better check on those
-   DRY violation: `ElectionPropositionController` lines 47-73 &
    `ElectionPositionController` lines 65-91 (-0.1)
-   Watch for stray semicolons
-   5 unused imports (-0.1)


##  Commits

-   Watch out for unresolved merge conflicts; these will be penalties for "good
    software engineering practice"

### Messages

5/5

-   Commit messages are acceptable
-   0/56 (0%) commits are malformed  (-0.0)
```
88 commits on the master branch
216 commits among all branches
	On the master branch:
		31 merges, 1 reverts, 56 well-formatted commits, and 0 malformatted commits
	On all other branches:
		48 merges, 0 reverts, 78 well-formatted commits, and 2 malformatted commits
		2020-08-05 13:18:59-05:00 Commit b23ffe67 has 1 line too long. (Jayson Cheng)
		2020-08-05 19:30:43+00:00 Commit 0a9aad11 has 1 line too long. (jinsengcheng)

	Unresolved merge conflicts on the master branch:
		0 out of 31 merges have unresolved merge conflicts:
	Unresolved merge conflicts on all other branches:
		2 out of 50 merges have unresolved merge conflicts:
			2020-07-31 18:22:17-05:00 f7297b0a (Jayson Cheng)
			2020-08-01 19:05:37-05:00 cff46a20 (nfong2)
```

### Summary

```
Contributions by each partner to CSCE 361 / summer2020 / 36team4 on 6 branches:
	('zhengnian', 'zhengnian@huskers.unl.edu')
	 2754 total line changes in  25 commits
			java        2689 line changes
			md            55 line changes
			xml-TEMPLATE  13 line changes
		First commit:  2020-07-30 04:45:22+00:00
		Median commit: 2020-08-01 02:34:53-05:00
		Last commit:   2020-08-05 16:58:22+00:00
	('nfong2', 'nfong2@huskers.unl.edu')
	 4084 total line changes in  35 commits
			fxml          75 file changes
			java        2082 line changes
			md           216 line changes
			xml-TEMPLATE  45 line changes
		First commit:  2020-07-30 00:12:37-05:00
		Median commit: 2020-08-01 22:07:01+00:00
		Last commit:   2020-08-05 19:44:22+00:00
	('derek.drake', 'derek.drake@huskers.unl.edu')
	   60 total line changes in  11 commits
			md            60 line changes
			png           25 file changes
		First commit:  2020-08-03 23:03:00-05:00
		Median commit: 2020-08-05 02:43:55+00:00
		Last commit:   2020-08-05 11:03:44-05:00
	('jinsengcheng', 'jinsengcheng@huskers.unl.edu')
	 4868 total line changes in  42 commits
			fxml          47 file changes
			java        3134 line changes
			md           204 line changes
			xml           17 line changes
			xml-TEMPLATE  12 line changes
		First commit:  2020-07-30 01:44:09+00:00
		Median commit: 2020-08-01 05:19:20+00:00
		Last commit:   2020-08-05 19:30:43+00:00
```
