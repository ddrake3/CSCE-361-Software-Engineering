package edu.unl.cse.csce361.voting_system.voting_logic;

import edu.unl.cse.csce361.voting_system.backend.Backend;
import edu.unl.cse.csce361.voting_system.backend.Ballot;

public class BallotBuilder {
    private String ballotName;

    public BallotBuilder() {
        this.ballotName = null;
    }

    public BallotBuilder setName(String name) {
        this.ballotName = name;
        return this;
    }

    public Ballot build() {
        Ballot ballot;
        if (this.ballotName != null) {
            ballot = Backend.getInstance().createBallot(this.ballotName);
        } else {
            throw new IllegalStateException("The ballot name is required.");
        }
        return ballot;
    }
}
