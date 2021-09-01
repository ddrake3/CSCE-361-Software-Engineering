package edu.unl.cse.csce361.voting_system.voting_logic;

import edu.unl.cse.csce361.voting_system.backend.Backend;
import edu.unl.cse.csce361.voting_system.backend.VoteCart;

import java.util.HashSet;
import java.util.Set;

public class VoteCartBuilder {
    private boolean isProposition;
    private String ballot;
    private String question;
    private Set<String> answers;
    private String keyword;

    public VoteCartBuilder() {
        this.isProposition = false;
        this.ballot = null;
        this.question = null;
        this.answers = new HashSet<>();
        this.keyword = null;
    }

    public VoteCartBuilder asProposition() {
        this.isProposition = true;
        return this;
    }

    public VoteCartBuilder setBallot(String ballot) {
        this.ballot = ballot;
        return this;
    }

    public VoteCartBuilder setQuestion(String question) {
        this.question = question;
        return this;
    }

    public VoteCartBuilder setAnswers(Set<String> answers) {
        this.answers = answers;
        return this;
    }

    public VoteCartBuilder setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public VoteCart build() {
        VoteCart voteCart;
        if (this.ballot == null || this.question == null) {
            throw new IllegalStateException("The ballot name is not assigned or the question is not set.");
        } else {
            if (this.isProposition) {
                if (this.answers.isEmpty() && this.keyword != null) {
                    voteCart = Backend.getInstance().createPropositionVoteCart(this.ballot,this.question,this.keyword);
                } else {
                    throw new IllegalStateException("To create a proposition, a keyword is required and the answer " +
                            "list shall be empty.");
                }
            } else {
                if (this.keyword == null) {
                    voteCart = Backend.getInstance().createPositionVoteCart(this.ballot,this.question,this.answers);
                } else {
                    throw new IllegalStateException("No keyword is needed for creating positional race");
                }
            }
        }
        return voteCart;
    }
}
