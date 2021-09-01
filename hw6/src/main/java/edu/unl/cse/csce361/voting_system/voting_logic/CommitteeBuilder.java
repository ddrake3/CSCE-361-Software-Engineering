package edu.unl.cse.csce361.voting_system.voting_logic;

import edu.unl.cse.csce361.voting_system.backend.Backend;
import edu.unl.cse.csce361.voting_system.backend.Committee;

public class CommitteeBuilder {

    // Personal Information ( Required field )
    private String name;
    private String password;
    private String jobTitle;

    public CommitteeBuilder() {
        this.name = null;
        this.password = null;
        this.jobTitle = null;
    }

    public CommitteeBuilder setName(String name) {
        this.name = name;

        return this;
    }

    public CommitteeBuilder setPassword(String password) {
        this.password = password;

        return this;
    }

    public CommitteeBuilder setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;

        return this;
    }

    public Committee build() {
        Committee committee;
        if (this.name == null || this.password == null || this.jobTitle == null) {
            throw new IllegalStateException("Name, password, and job title are required.");
        } else {
            committee = Backend.getInstance().createCommittee(this.name, this.password, this.jobTitle);
        }
        return committee;
    }
}
