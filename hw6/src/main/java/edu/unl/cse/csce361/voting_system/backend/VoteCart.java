package edu.unl.cse.csce361.voting_system.backend;

import java.util.Set;

/**
 * Represents a voting question
 */
public interface VoteCart {
    /**
     * Provide the question name to be voted
     * @return The question name to be voted
     */
    String getVoteQuestion();

    /**
     * Provide the set of vote answer
     * @return The set of vote answer
     */
    Set<String> getAnswerList();

    /**
     * Assigns this vote question to a {@link ResultEntity} object.
     *
     * @param resultEntity The {@link ResultEntity} that the question will be added to.
     */
    void addResult(ResultEntity resultEntity);
}
