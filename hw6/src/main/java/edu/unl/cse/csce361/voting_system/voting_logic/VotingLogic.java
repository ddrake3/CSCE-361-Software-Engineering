package edu.unl.cse.csce361.voting_system.voting_logic;

import edu.unl.cse.csce361.voting_system.backend.Backend;
import edu.unl.cse.csce361.voting_system.backend.Ballot;
import edu.unl.cse.csce361.voting_system.backend.Committee;
import edu.unl.cse.csce361.voting_system.backend.VoteCart;
import edu.unl.cse.csce361.voting_system.backend.Voter;

import java.util.*;

/**
 * A facade for the logic subsystem.
 */
public class VotingLogic {

    private static VotingLogic instance;

    public static VotingLogic getInstance() {
        if (instance == null) {
            instance = new VotingLogic();
        }
        return instance;
    }

    private VotingLogic() {
        super();
    }

    /**
     * Used to help election official to create ballot
     * @param ballotName The name of the ballot
     * @return added ballot into database
     */
    public String createBallot(String ballotName) {
        String message = null;
        try {
            Ballot ballot = new BallotBuilder().setName(ballotName).build();
            if (ballot == null) {
                message = "No ballot is created";
            }
        } catch (RuntimeException exception) {
            message = exception.getMessage();
        }
        return message;
    }

    /**
     * Allows voter to register for an account to vote
     * @param name Voter Name
     * @param address1 Voter Address1
     * @param address2 Voter Address2
     * @param city Voter city
     * @param state Voter state
     * @param zipCode Voter zipCode
     * @param socialSecurityNumber Voter socialSecurityNumber
     * @return added voters into database
     */
    public String createVoter(String name, String address1, String address2, String city,
                              String state, String zipCode, String socialSecurityNumber) {
        String message = null;
        try {
            Voter voter = new VoterBuilder()
                    .setName(name)
                    .setAddress(address1, address2, city, state, zipCode)
                    .setSocialSecurityNumber(socialSecurityNumber)
                    .build();
            if (voter == null) {
                message = "No voter is created";
            }
        } catch (RuntimeException exception) {
            message = exception.getMessage();
        }
        return message;
    }

    /**
     * Allows committee to sign up an account either as an auditor or an election official
     * @param name Committee name
     * @param password Committee password
     * @param jobTitle Committee jobTitle
     * @return added committees into database
     */
    public String createCommittee(String name, String password, String jobTitle) {
        String message = null;
        try {
            Committee committee = new CommitteeBuilder()
                    .setName(name)
                    .setPassword(password)
                    .setJobTitle(jobTitle)
                    .build();
            if (committee == null) {
                message = "No committee is created";
            }
        } catch (RuntimeException exception) {
            message = exception.getMessage();
        }
        return message;
    }

    /**
     * Requires voter to input the correct name and authentication code to login
     * @param name Voter name
     * @param authenticationCode Voter authentication code
     * @return the info for voter whether it is register or not
     */
    public String isRegisteredVoter(String name, String authenticationCode) {
        String message = null;
        try {
            Voter voter = Backend.getInstance().getVoter(name);
            String ballotName = getPublishingBallotName();
            Ballot ballot = Backend.getInstance().getBallot(ballotName);
            if (voter != null) {
                if (!voter.getAuthenticationCode().equals(authenticationCode)) {
                    message = "Incorrect voter authentication code, please try again.";
                }
                if(Backend.getInstance().getVoterHasVoted(voter, ballot)) {
                    message = "You have already submitted your vote for this ballot.";
                }
            } else {
                message = "Invalid voter name, please register before you vote.";
            }
        } catch (RuntimeException exception) {
            message = exception.getMessage();
        }
        return message;
    }

    /**
     * Requires committee to input the correct name and password to login
     * @param name Committee name
     * @param password Committee password
     * @return the info for committee whether it is registered or not
     */
    public String isRegisteredCommittee(String name, String password) {
        String message = null;
        try {
            Committee committee = Backend.getInstance().getCommittee(name);
            if (committee != null) {
                if (!committee.getPassword().equals(password)) {
                    message = "Incorrect password, please try again.";
                }
            } else {
                message = "Invalid committee name, please sign up an account.";
            }
        } catch (RuntimeException exception) {
            message = exception.getMessage();
        }
        return message;
    }

    /**
     * Allows committee to update their password if they have forgotten their account's password
     * @param name Committee name
     * @param password Committee password
     * @return the info for committee whether they have successfully changed their password
     */
    public String updateCommitteePassword(String name, String password) {
        String errorMessage = null;
        try {
            Committee committee = Backend.getInstance().getCommittee(name);
            committee.setPassword(password);
            Backend.getInstance().updateCommitteePassword(committee);
        } catch(IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }

        return errorMessage;
    }

    /**
     * Check voter info and get authenticationCode for voter
     * @param name Voter name
     * @param socialSecurityNumber Voter social security number
     * @return authenticationCode to voter
     */
    public String getVoterAuthenticationCode(String name, String socialSecurityNumber) {
        String message;
        try {
            Voter voter = Backend.getInstance().getVoter(name);
            if (voter != null) {
                if (!voter.getSocialSecurityNumber().equals(socialSecurityNumber)) {
                    message = "Incorrect Social Security Number (SSN), please try again.";
                } else {
                    message = voter.getAuthenticationCode();
                }
            } else {
                message = "Invalid voter name, please register before you vote.";
            }
        } catch (RuntimeException exception) {
            message = exception.getMessage();
        }
        return message;
    }

    /**
     * Add the new vote cart in the existing ballot
     * @param ballotName The name of the ballot
     * @param question The position of the vote cart
     * @param answer The available candidates of the vote cart
     */
    public void addPosition(String ballotName, String question, Set<String> answer) {
        try {
            VoteCart voteCart = new VoteCartBuilder().setBallot(ballotName).setQuestion(question).
                    setAnswers(answer).build();
            if (voteCart == null) {
                throw new NullPointerException("No position in vote cart created");
            }
        } catch (RuntimeException exception) {
            System.err.println(exception.getMessage());
        }
    }

    /**
     * Add the new proposition question in the existing ballot
     * @param ballotName The name of the ballot
     * @param question The proposition of the vote cart
     * @param keyword The unique identifier of the proposition vote cart in the ballot
     * @return added proposition into database
     */
    public String addProposition(String ballotName, String question, String keyword) {
        String message = "";
        try {
            VoteCart voteCart = new VoteCartBuilder().asProposition().setBallot(ballotName).setQuestion(question).
                    setKeyword(keyword).build();
            if (voteCart == null) {
                throw new NullPointerException("No proposition in vote cart created");
            }
        } catch (IllegalStateException exception) {
            message = "Keyword already exists. Please use another keyword.";
        }
        return message;
    }

    /**
     * Get the position list in the existing ballot
     * @param ballotName the name of the ballot
     * @return position list from database
     */
    public List<String> getPositionList(String ballotName) {
        List<String> positionList = new ArrayList<>();
        Set<VoteCart> votePositionSet = Backend.getInstance().getBallot(ballotName).getPositionVoteCartSet();
        for (VoteCart votePosition : votePositionSet) {
            positionList.add(votePosition.getVoteQuestion());
        }
        return positionList;
    }

    /**
     * Get the proposition list in the existing ballot
     * @param ballotName the name of the ballot
     * @return proposition list from database
     */
    public List<String> getPropositionList(String ballotName) {
        List<String> propositionList = new ArrayList<>();
        Set<VoteCart> votePropositionSet = Backend.getInstance().getBallot(ballotName).getPropositionVoteCartSet();
        for (VoteCart voteProposition : votePropositionSet) {
            propositionList.add(voteProposition.getVoteQuestion());
        }
        return propositionList;
    }

    /**
     * Get the options list in the existing question from existing ballot
     * @param ballotName the name of the ballot
     * @param question the position or proposition of the ballot
     * @return options list from database
     */
    public List<String> getOptionsListForQuestion(String ballotName, String question) {
        VoteCart voteCart = Backend.getInstance().getVoteCart(ballotName, question);
        return new ArrayList<>(voteCart.getAnswerList());
    }

    /**
     * Remove a voteCart from existing ballot
     * @param ballotName the name of the ballot
     * @param question the position or proposition of the ballot
     * @return remove a voteCart from ballot
     */
    public String removeVoteCartFromBallot(String ballotName, String question) {
        String message;
        Ballot ballot = Backend.getInstance().getBallot(ballotName);
        VoteCart voteCart = Backend.getInstance().getVoteCart(ballotName, question);
        if (ballot != null && voteCart != null) {
            Backend.getInstance().removeVoteCartFromBallot(ballot,voteCart);
            message = "The positional race / proposition is removed.";
        } else {
            message = "The ballot or question does not exist.";
        }
        return message;
    }

    /**
     * Get a list of options for position from existing ballot
     * @param ballotName the name of the ballot
     * @return a list of options for positions
     */
    public List<String> getPositionAnswerList(String ballotName) {
        List<String> positionList = new ArrayList<>();
        Set<VoteCart> votePositionSet = Backend.getInstance().getBallot(ballotName).getPositionVoteCartSet();
        for (VoteCart votePosition : votePositionSet) {
            positionList.add(ListToString(new ArrayList<>(votePosition.getAnswerList())));
        }
        return positionList;
    }

    /**
     * Convert the list into a string to display from existing questions
     * @param questions the question of the ballot
     * @return string of question
     */
    public String ListToString(List<String> questions) {
        String delimiter = ", ";
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < questions.size() - 1) {
            sb.append(questions.get(i));
            sb.append(delimiter);
            i++;
        }
        sb.append(questions.get(i));
        return sb.toString();
    }

    /**
     * Get the status of a ballot
     * @param ballotName The name of a ballot
     * @return status as a string
     */
    public String proceedBallot(String ballotName) {
        String message;
        Ballot ballot = Backend.getInstance().getBallot(ballotName);
        String publishingBallot = getPublishingBallotName();
        if (publishingBallot.equals("")) {
            if (ballot == null) {
                message = "No such ballot exists.";
            } else if (ballot.isEnded()) {
                message = "Ballot had been ended.";
            } else {
                ballot = Backend.getInstance().proceedBallot(ballot);
                if (ballot.isPublished()) {
                    message = "Ballot is now published.";
                } else {
                    message = "Some errors occur. Ballot is not published.";
                }
            }
        } else if (publishingBallot.equals(ballot.getBallotName())) {
            ballot = Backend.getInstance().proceedBallot(ballot);
            if (ballot.isEnded()) {
                message = "Ballot is now ended.";
            } else {
                message = "Some errors occur. Ballot is not ended.";
            }
        } else {
            message = publishingBallot + " is on going. Unable to publish " + ballotName + ".";
        }
        return message;
    }

    /**
     * Get the status of a list of ballots
     * @return status as a list of strings
     */
    public List<List<String>> getBallotsStatus() {
        List<String> ballotNameList = new ArrayList<>();
        List<String> statusList = new ArrayList<>();
        Set<Ballot> ballots = Backend.getInstance().getBallotSet();
        for (Ballot ballot : ballots) {
            ballotNameList.add(ballot.getBallotName());
            statusList.add(ballot.getStatus());
        }
        return List.of(ballotNameList,statusList);
    }

    /**
     * Get a published ballot
     * @return published ballot as a string
     */
    public String getPublishingBallotName() {
        String publishingBallot = "";
        Set<Ballot> ballots = Backend.getInstance().getBallotSet();
        for (Ballot ballot : ballots) {
            if (ballot.isPublished() && !ballot.isEnded()) {
                publishingBallot = ballot.getBallotName();
            }
        }
        return publishingBallot;
    }

    /**
     * Check if someone is an auditor
     * @return if someone is an auditor as a boolean
     */
    public boolean isAuditor(String committeeName) {
        boolean isAuditor = false;
        if (Backend.getInstance().getCommittee(committeeName).getJobTitle().equalsIgnoreCase("Auditor")) {
            isAuditor = true;
        }
        return isAuditor;
    }

    /**
     * Submit the vote result
     * @param ballotName The name of the ballot
     * @param voterName The name of the voter
     * @param voteResultSet The map with ballot question as keys and voter's choices as values
     */
    public void createVoteResult(String ballotName, String voterName,
                                 Map<String, String> voteResultSet, String voteId) {
        Ballot ballot = Backend.getInstance().getBallot(ballotName);
        Voter voter = Backend.getInstance().getVoter(voterName);
        VoteCart voteCart;
        for (Map.Entry<String, String> voteResult : voteResultSet.entrySet()) {
            voteCart = Backend.getInstance().getVoteCart(ballotName, voteResult.getKey());
            Backend.getInstance().createResult(ballot, voter, voteCart, voteResult.getValue(), voteId);
        }
    }

    /**
     * Get the list of lists of strings of results
     * @param ballotName The name of the ballot
     * @return The list of results for each voter
     */
    public List<List<String>> getResultsList(String ballotName) {
        Map<List<String>,Integer> resultSet = Backend.getInstance().getResultsSet(ballotName);
        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        List<String> ansCounts = new ArrayList<>();
        for (List<String> questionAns : resultSet.keySet()) {
            questions.add(questionAns.get(0));
            answers.add(questionAns.get(1));
            ansCounts.add(resultSet.get(questionAns).toString());
        }
        return List.of(questions,answers,ansCounts);
    }

    /**
     * Retrieve the statistic of vote result by calling method in backend with specified vote confirmation code.
     * @param voteId The vote confirmation code
     * @return The set of map for question as key and chosen answer as value
     */
    public Map<String, String> getVoteResultsMap(String voteId) {
        Map<String, String> voteResults = Backend.getInstance().getVoteResultsSet(voteId);
        return voteResults;
    }

    /**
     * Get the winners from a ballot
     * @param ballotName The name of the ballot
     * @return The map of winning vote carts.
     */
    public Map<String,String> getWinners(String ballotName) {
        Map<String,String> winnerMap = new HashMap<>();
        Map<String,Map<String,Integer>> resultMap = Backend.getInstance().getWinnersMap(ballotName);
        for (String question : resultMap.keySet()) {
            int max = 0;
            StringBuilder winner = new StringBuilder();
            for (String answer : resultMap.get(question).keySet()) {
                if (!answer.equals("No Vote")) {
                    if (max < resultMap.get(question).get(answer)) {
                        max = resultMap.get(question).get(answer);
                        winner = new StringBuilder(answer);
                    } else if (max == resultMap.get(question).get(answer)) {
                        winner.append("; ").append(answer);
                    }
                }
            }
            winnerMap.put(question, winner.toString());
        }
        return winnerMap;
    }

    /**
     * Retrieve the map of voters and data whether they have voted a particular ballot
     * @param ballotName The name of the ballot
     * @return A map of voter
     */
    public Map<String,String> getVoterAction(String ballotName) {
        return Backend.getInstance().getVoterActionMap(ballotName);
    }

    /**
     * Get the number of the voters voted
     * @param ballotName The name of the ballot
     * @return the count of voters of YES
     */
    public int getNumberOfVotersVoted(String ballotName) {
        Map<String, String> voters = Backend.getInstance().getVoterActionMap(ballotName);
        return Collections.frequency(voters.values(), "Yes");
    }

    /**
     * Get the number voted in the existing ballot
     * @param ballotName The name of the ballot
     * @return the count of voted in the ballot
     */
    public int getNumberOfBallotVoted(String ballotName) {
        return Backend.getInstance().getBallot(ballotName).getVoters().size();
    }
}
