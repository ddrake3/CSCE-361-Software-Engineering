package edu.unl.cse.csce361.voting_system.backend;

import java.util.Set;

/**
 * Represents voting ballot.
 */
public interface Ballot {
    /**
     * Provide the name of the ballot
     * @return the name of the ballot
     */
    String getBallotName();

    /**
     * Provide the list of all questions in the ballot
     * @return the list of all questions in the ballot
     */
    Set<VoteCart> getVoteCartSet();

    /**
     * Provide the list of all position vote carts in the ballot
     * @return the list of all position vote carts in the ballot
     */
    Set<VoteCart> getPositionVoteCartSet();

    /**
     * Provide the list of all proposition vote carts in the ballot
     * @return the list of all proposition vote carts in the ballot
     */
    Set<VoteCart> getPropositionVoteCartSet();

    /**
     * Provide if a ballot is published
     * @return if a ballot is published
     */
    boolean isPublished();

    /**
     * Provide if a ballot is ended
     * @return if a ballot is ended
     */
    boolean isEnded();

    /**
     * Publish the ballot to let voters vote
     */
    void publish();

    /**
     * Stop the ballot when ended
     */
    void end();

    /**
     * Get the status of the ballot, whether it is published or ended
     * @return the name of the ballot's current status
     */
    String getStatus();

    /**
     * Assigns this ballot to a {@link ResultEntity} object.
     * @param resultEntity The {@link ResultEntity} that the ballot will be added to.
     */
    void addResult(ResultEntity resultEntity);

    /**
     * @return the set of voters who have voted for this ballot
     */
    Set<VoterEntity> getVoters();
}
