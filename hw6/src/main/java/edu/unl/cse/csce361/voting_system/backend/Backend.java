package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.PersistenceException;
import java.util.Set;

public class Backend {
    private static Backend instance;

    public static Backend getInstance() {
        if (instance == null) {
            instance = new Backend();
        }
        return instance;
    }

    private Backend() {
        super();
    }

    /**
     * Retrieves the voter that has the specified name, if such a voter exists.
     * @param name                      The name of the voter
     * @return The specified voter if it is present in the database; <code>null</code> otherwise
     */
    public Voter getVoter(String name) {
        return VoterEntity.getVoterByName(name);
    }

    /**
     * Retrieves the committee that has the specified name, if such a committee exists.
     * @param name                      The name of the committee
     * @return The specified committee if it is present in the database; <code>null</code> otherwise
     */
    public Committee getCommittee(String name) {
        return CommitteeEntity.getCommitteeByName(name);
    }

    /**
     * Retrieves the ballot that has the specified name, if such a ballot exists.
     * @param ballotName                The name of the ballot
     * @return The specified ballot if it is present in the database; <code>null</code> otherwise
     */
    public Ballot getBallot(String ballotName) {
        return BallotEntity.getBallotByName(ballotName);
    }

    /**
     * Retrieves the vote cart that has the specified ballot name and question, if such a ballot exists.
     * @param ballotName                The name of the ballot
     * @param question                  The question of the vote cart
     * @return The specified vote cart if it is present in the database; <code>null</code> otherwise
     */
    public VoteCart getVoteCart(String ballotName, String question) {
        return VoteCartEntity.getVoteCartByBallotAndQuestion(ballotName, question);
    }

    /**
     * Retrieves the collection of ballots from database
     * @return The set of ballots
     */
    public Set<Ballot> getBallotSet() {
        Set<Ballot> ballots;
        try{
            ballots = BallotEntity.getBallots();
        }
        catch (NullPointerException exception){
            System.err.println("No ballot found" + exception.getMessage());
            ballots = Set.of();
        }
        return ballots;
    }

    /**
     * Retrieve the map of voters and data whether they have voted a particular ballot
     * @param ballotName                The name of the ballot
     * @return The map of voter
     */
    public Map<String,String> getVoterActionMap(String ballotName) {
        Map<String,String> voterActionMap = new HashMap<>();
        BallotEntity ballot = (BallotEntity)getBallot(ballotName);
        Set<Voter> voters;
        try {
            voters = VoterEntity.getVoters();
        } catch (NullPointerException exception) {
            System.err.println("No voter found" + exception.getMessage());
            voters = Set.of();
        }
        for (Voter voter : voters) {
            if (voter.getVotedBallots().contains(ballot)) {
                voterActionMap.put(voter.getName(),"Yes");
            } else {
                voterActionMap.put(voter.getName(),"No");
            }
        }
        return voterActionMap;
    }

    /**
     * Retrieve the statistic of ballot result from database
     * @param ballotName                The name of ballot
     * @return The set of map result key with counts value
     */
    public Map<List<String>, Integer> getResultsSet(String ballotName) {
        return ResultEntity.getResultInfo(ballotName);
    }

    /**
     * Retrieve the statistic of vote result from database with specified vote confirmation code.
     * @param voteId The vote confirmation code
     * @return The set of map for question as key and chosen answer as value
     */
    public Map<String,String> getVoteResultsSet(String voteId) {
        return ResultEntity.getVoteResults(voteId);
    }

    /**
     * Retrieve the statistic of ballot winner from database
     * @param ballotName The name of ballot
     * @return The set of map result with question key and answer set value
     */
    public Map<String,Map<String,Integer>> getWinnersMap(String ballotName) {
        return ResultEntity.getWinnerInfo(ballotName);
    }

    /**
     * <p>Creates a new ballot with the specified parameters. The ballot name must be unique. If the ballot already
     * exists, then a null will be returned</p>
     * @param name                      The unique name of the ballot
     * @return The new ballot with the specified parameters, or a null if the ballot already existed
     */
    public Ballot createBallot(String name) {
        Ballot ballot;
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            ballot = new BallotEntity(name);
            session.saveOrUpdate(ballot);
            session.getTransaction().commit();
        } catch (RuntimeException exception) {
            session.getTransaction().rollback();
            ballot = BallotEntity.getBallotByName(name);
            if (ballot == null) {
                throw new IllegalStateException("Could not create ballot " + name + ": " +
                        exception.getMessage() + ". Could not retrieve existing ballot " + name +
                        " from database.", exception);
            }
        }
        return ballot;
    }

    /**
     * <p>Creates a new voter with the specified parameters. The voter's name must be unique. Only
     * the second street address may be <code>null</code> (which is treated as the equivalent of a blank line);
     * similarly, only the second street address may be an empty string. If the voter already exists (based on the
     * name), then the existing voter will be returned (with the existing parameters, not the specified
     * parameters).</p>
     * <p>If a new voter is created, it is guaranteed to be a {@link Voter}. If an existing voter is
     * retrieved, it is only guaranteed to be a {@link Voter}.</p>
     *
     * @param name                       The voter's name
     * @param streetAddress1             The first line of the voter's street address
     * @param streetAddress2             The second line of the voter's street address
     * @param city                       The city of the voter's address
     * @param state                      The abbreviation for the state of the voter's address
     * @param zipCode                    The voter's 5-digit zip code
     * @param socialSecurityNumber       The voter's social security number (SSN)
     * @return a new voter with the specified parameters, or a voter with the same name
     * @throws IllegalArgumentException if an argument is too long, too short, or contains illegal characters
     * @throws NullPointerException     if an argument (other than <code>streetAddress2</code>) is <code>null</code>
     */
    public Voter createVoter(String name, String streetAddress1, String streetAddress2,
                                             String city, String state, String zipCode,
                                             String socialSecurityNumber)
            throws IllegalArgumentException, NullPointerException {
        Voter voter;
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            voter = new VoterEntity(name, streetAddress1, streetAddress2, city, state, zipCode,
                    socialSecurityNumber);
            session.saveOrUpdate(voter);
            session.getTransaction().commit();
        } catch (RuntimeException exception) {
            session.getTransaction().rollback();
            voter = VoterEntity.getVoterByName(name);
            if (voter == null) {
                throw new IllegalStateException("Could not create new voter " + name + ": " +
                        exception.getMessage() + ". Could not retrieve existing voter " + name +
                        " from database.", exception);
            }
        }
        return voter;
    }

    /**
     * <p>Creates a new committee with the specified parameters. The committee's name must be unique.
     * If the committee already exists (based on the name), then the existing committee will be returned
     * (with the existing parameters, not the specified parameters).</p>
     * <p>If a new committee is created, it is guaranteed to be a {@link Committee}. If an existing committee is
     * retrieved, it is only guaranteed to be a {@link Committee}.</p>
     *
     * @param name                       The committee's name
     * @param password                   The committee's password
     * @param jobTitle                   The committee's job title
     * @return a new committee with the specified parameters, or a committee with the same name
     * @throws IllegalArgumentException if an argument is too long, too short, or contains illegal characters
     * @throws NullPointerException     if an argument (other than <code>streetAddress2</code>) is <code>null</code>
     */
    public Committee createCommittee(String name, String password, String jobTitle)
            throws IllegalArgumentException, NullPointerException {
        Committee committee;
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            committee = new CommitteeEntity(name, password, jobTitle);
            session.saveOrUpdate(committee);
            session.getTransaction().commit();
        } catch (RuntimeException exception) {
            session.getTransaction().rollback();
            committee = CommitteeEntity.getCommitteeByName(name);
            if (committee == null) {
                throw new IllegalStateException("Could not create new committee " + name + ": " +
                        exception.getMessage() + ". Could not retrieve existing committee " + name +
                        " from database.", exception);
            }
        }
        return committee;
    }

    /**
     * <p>Updates the password of the committee.</p>
     * @param committee                  The committee
     * @return an existing committee with updated password
     */
    public Committee updateCommitteePassword(Committee committee) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try{
            session.update(committee);
            session.getTransaction().commit();
        } catch (RuntimeException exception) {
            session.getTransaction().rollback();
            committee = CommitteeEntity.getCommitteeByName(committee.getName());
            if (committee == null) {
                throw new IllegalStateException("Could not update this committee : " +
                        exception.getMessage() + ". Could not retrieve existing committee from database.", exception);
            }
        }
        return committee;
    }

    /**
     * <p>Creates a new vote cart with the specified parameters. The combination of question and ballot must be
     * unique. If the question already exists in the ballot, then the existing question will be returned with the new
     * answers</p>
     * @param ballot                     The ballot the vote cart is in
     * @param question                   The name of the voteCart, (probably) the position to be voted
     * @param answers                    The choices that can be selected
     * @return a new vote cart with the specified parameters, or an existing vote cart with the same ballot name
     */
    public VoteCart createPositionVoteCart(String ballot, String question, Set<String> answers) {
        VoteCart voteCart;
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            voteCart = new VoteCartEntity(ballot, question, false, answers);
            session.saveOrUpdate(voteCart);
            session.getTransaction().commit();
        } catch (RuntimeException exception) {
            session.getTransaction().rollback();
            voteCart = VoteCartEntity.getVoteCartByBallotAndQuestion(ballot,question);
            if (voteCart == null) {
                throw new IllegalStateException("Could not create vote cart " + question + " in " + ballot + ": " +
                        exception.getMessage() + ". Could not retrieve existing vote cart " + question + " in " +
                        ballot + " from database.",exception);
            }
        }
        return voteCart;
    }

    /**
     * <p>Creates a new vote cart with the specified parameters. The combination of question and ballot must be
     * unique. If the question already exists in the ballot, then the existing question will be returned with the new
     * answers</p>
     * @param ballot                     The ballot the vote cart is in
     * @param question                   The name of the voteCart, (probably) a proposition, or the position to be voted
     * @param keyword                    A unique keyword used to identify a proposition in a ballot
     * @return a new vote cart with the specified parameters, or an existing vote cart with the same ballot name
     */
    public VoteCart createPropositionVoteCart(String ballot, String question, String keyword) {
        VoteCart voteCart;
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            voteCart = new VoteCartEntity(ballot, question, true, keyword);
            session.saveOrUpdate(voteCart);
            session.getTransaction().commit();
        } catch (RuntimeException exception) {
            session.getTransaction().rollback();
            voteCart = VoteCartEntity.getVoteCartByBallotAndQuestion(ballot, question);
            if (voteCart == null) {
                throw new IllegalStateException("Could not create vote cart " + question + " in " + ballot + ": " +
                        exception.getMessage() + ". Could not retrieve existing vote cart " + question + " in " +
                        ballot + " from database.", exception);
            }
        }
        return voteCart;
    }

    /**
     * <p>Creates a new result with the specified parameters. The combination of ballot and voter must be
     * unique. If ballot already exists with a voter, then the existing result will be returned</p>
     * @param ballot                     The published ballot
     * @param voter                      The voter
     * @param voteCart                   The vote cart based on the question in the ballot
     * @param answer                     The voter's submitted response
     * @param answer                     The random generated vote id
     * @return a new result with the specified parameters, or an existing result
     */
    public ResultEntity createResult(Ballot ballot, Voter voter, VoteCart voteCart, String answer, String voteId) {
        ResultEntity result;
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            result = voter.submitVote(ballot, voter, voteCart, voteId);
            result.setAnswer(answer);
            session.saveOrUpdate(result);
            session.getTransaction().commit();
        } catch (PersistenceException exception) {
            session.getTransaction().rollback();
            result = null;
        }
        return result;
    }

    /**
     * <p>Removes the vote cart from the ballot if the vote cart exists in the ballot.</p>
     * @param ballot                     The ballot
     * @param voteCart                   The vote cart
     */
    public void removeVoteCartFromBallot(Ballot ballot, VoteCart voteCart) {
        if (ballot instanceof BallotEntity && voteCart instanceof VoteCartEntity) {
            ((BallotEntity)ballot).removeVoteCart((VoteCartEntity) voteCart);
            VoteCartEntity.removeVoteCartByVoteCardId(((VoteCartEntity) voteCart).getVoteCartId());
        }
    }

    /**
     * <p>Determine the stage of the ballot, which consists of `open`, `ongoing`, and `closed` respectively
     * if the ballot exists.</p>
     * @param ballot                     The ballot
     */
    public Ballot proceedBallot(Ballot ballot) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            if (ballot.isEnded()) {
                throw new IllegalStateException("Ballot had been ended.");
            } else if (ballot.isPublished()) {
                ballot.end();
            } else {
                ballot.publish();
            }
            session.saveOrUpdate(ballot);
            session.getTransaction().commit();
        } catch (RuntimeException exception) {
            session.getTransaction().rollback();
            System.err.println(exception.getMessage());
        }
        return ballot;
    }

    /**
     * <p>Determine if the voter has voted for the certain ballot. If the voter has voted
     * for the ballot, it will return true, false otherwise.</p>
     * @param voter                      The voter
     * @param publishedBallot            The ballot that is ongoing now
     * @return the boolean value for hasVoted
     */
    public boolean getVoterHasVoted(Voter voter, Ballot publishedBallot) {
        boolean hasVoted = false;
        Set<BallotEntity> votedBallots = voter.getVotedBallots();
        if(votedBallots != null || !votedBallots.isEmpty()) {
            for(BallotEntity votedBallot : votedBallots) {
                if(votedBallot.getBallotName().equals(publishedBallot.getBallotName())) {
                    hasVoted = true;
                }
            }
        }
        return hasVoted;
    }
}
