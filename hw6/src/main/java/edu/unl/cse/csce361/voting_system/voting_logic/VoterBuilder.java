package edu.unl.cse.csce361.voting_system.voting_logic;

import edu.unl.cse.csce361.voting_system.backend.Backend;
import edu.unl.cse.csce361.voting_system.backend.Voter;

public class VoterBuilder {

    // Personal Information ( Required field )
    private String name;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zipCode;
    private String socialSecurityNumber;

    public VoterBuilder() {
        this.name = null;
        this.streetAddress1 = null;
        this.streetAddress2 = null;
        this.city = null;
        this.state = null;
        this.zipCode = null;
        this.socialSecurityNumber = null;
    }

    public VoterBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public VoterBuilder setAddress(String streetAddress1, String streetAddress2,
                                   String city, String state, String zipCode) {
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        return this;
    }

    public VoterBuilder setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
        return this;
    }

    public Voter build() {
        Voter voter;
        if (this.name == null) {
            throw new IllegalStateException("Name is required.");
        } else if (this.streetAddress1 == null || this.city == null || this.state == null || this.zipCode == null) {
            throw new IllegalStateException("Address except Street Address 2 must be set.");
        } else if (this.socialSecurityNumber == null) {
            throw new IllegalStateException("Social security number is required.");
        } else {
            voter = Backend.getInstance().createVoter(this.name, this.streetAddress1,
                    this.streetAddress2, this.city, this.state, this.zipCode, this.socialSecurityNumber);
        }
        return voter;
    }
}
