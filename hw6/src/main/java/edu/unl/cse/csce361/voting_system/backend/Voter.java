package edu.unl.cse.csce361.voting_system.backend;

import java.util.Set;

public interface Voter {

    /** The maximum length of each of the customer's name and two street address lines */
    int MAXIMUM_LINE_LENGTH = 48;
    /** The maximum length of the city's name */
    int MAXIMUM_CITY_LENGTH = 32;
    /** The exact length of the state's abbreviation */
    int STATE_LENGTH = 2;
    /** The exact length of the zip code (without the "plus-four") */
    int ZIPCODE_LENGTH = 5;
    /** The exact length of the voter's SSN */
    int SSN_LENGTH = 9;
    /** The exact length of the authentication code */
    int AUTHENTICATION_CODE_LENGTH = 6;

    /**
     * Provide's the voter's name.
     *
     * @return the voter's name
     */
    String getName();

    /**
     * Provides the voter's complete address.
     *
     * @return the voter's address
     */
    String getAddress();

    /**
     * Sets the voter's complete address.
     */
    void setAddress(String streetAddress1, String streetAddress2, String city, String state, String zipCode);

    /**
     * Provides the voter's personal ID (Driver's license, SSN, Passport Number).
     *
     * @return the voter's personal ID.
     */
    String getSocialSecurityNumber();

    /**
     * Sets the voter's SSN.
     */
    void setSocialSecurityNumber(String ssn);

    /**
     * Provides the voter's authentication code.
     *
     * @return the voter's authentication code.
     */
    String getAuthenticationCode();

    /**
     * Assigns a voter to a {@link ResultEntity} object, indicating the voter has voted and stores their result
     * the database.
     *
     * @param ballot A {@link Ballot} object
     * @param voter A {@link Voter} object
     * @param voteCart A {@link VoteCart} object
     * @param voteId The random generated voteId in frontend controller
     *
     * @return the {@link ResultEntity} object
     */
    ResultEntity submitVote(Ballot ballot, Voter voter, VoteCart voteCart, String voteId);

    /**
     * Assigns this voter to a {@link BallotEntity} object.
     *
     * @param votedBallot The ballot object that the voter has voted will be added to.
     */
    void addResult(BallotEntity votedBallot);

    /**
     * @return the set of voter's voted ballots.
     */
    Set<BallotEntity> getVotedBallots();
}
