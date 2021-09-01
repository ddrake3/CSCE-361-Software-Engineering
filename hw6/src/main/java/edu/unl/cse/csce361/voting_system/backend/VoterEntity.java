package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.*;

/**
 * Hibernate implementation of {@link Voter}.
 */
@Entity
public class VoterEntity implements Voter {

    /* Database code */

    /**
     * Retrieve the collection of voters from the database
     * @return The set of voters
     */
    static Set<Voter> getVoters() {
        Session session = HibernateUtil.getSession();
        String hql = "from VoterEntity";
        List<Voter> voters = session.createQuery(hql,Voter.class).list();
        return Set.copyOf(voters);
    }

    /**
     * Retrieves the voter that has the specified name, if such a voter exists.
     *
     * @param name The name of the voter
     * @return The specified voter if it is present in the database; <code>null</code> otherwise
     */
    static Voter getVoterByName(String name) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Voter voter = null;
        try {
            voter = session.bySimpleNaturalId(VoterEntity.class).load(name);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            System.err.println("Could not load Voter " + name + ". " + exception.getMessage());
        }
        return voter;
    }


    /* POJO code */

    @SuppressWarnings("unused")
    @Id
    @GeneratedValue
    private Long voterId;

    @NaturalId
    @Column(length = Voter.MAXIMUM_LINE_LENGTH)   // apparently we have to define the size to enforce NaturalID's uniqueness?
    private String name;

    @Column(nullable = false, length = Voter.MAXIMUM_LINE_LENGTH)
    private String streetAddress1;
    @Column(nullable = false, length = Voter.MAXIMUM_LINE_LENGTH)
    private String streetAddress2;
    @Column(nullable = false, length = Voter.MAXIMUM_CITY_LENGTH)
    private String city;
    @Column(nullable = false, length = Voter.STATE_LENGTH)
    private String state;
    @Column(nullable = false, length = Voter.ZIPCODE_LENGTH)
    private String zipCode;

    @Column(nullable = false, length = Voter.SSN_LENGTH)
    private String socialSecurityNumber;

    @Column(nullable = false, length = Voter.AUTHENTICATION_CODE_LENGTH)
    private String authenticationCode;

    @ManyToMany(mappedBy = "voters", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BallotEntity> votedBallots;

    public VoterEntity() {
        super();
    }

    public VoterEntity(String name, String streetAddress1, String streetAddress2,
                          String city, String state, String zipCode, String socialSecurityNumber)
            throws IllegalArgumentException, NullPointerException {
        this.name = name;
        setAddress(streetAddress1, streetAddress2, city, state, zipCode);
        setSocialSecurityNumber(socialSecurityNumber);
        setAuthenticationCode();
        votedBallots = new HashSet<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAddress() {
        StringBuilder address = new StringBuilder();
        address.append(streetAddress1).append(System.lineSeparator());
        if (!streetAddress2.equals("")) {
            address.append(streetAddress2).append(System.lineSeparator());
        }
        address.append(city).append(", ").append(state).append("  ").append(zipCode);
        return address.toString();
    }

    public void setAddress(String streetAddress1, String streetAddress2, String city, String state, String zipCode)
            throws IllegalArgumentException, NullPointerException {
        if ((streetAddress1 == null) || (city == null) || state == null || zipCode == null) {
            throw new NullPointerException("In an address, only the second street address line can be null.");
        }
        if (streetAddress1.isBlank() || city.isBlank() || state.isBlank() || zipCode.isBlank()) {
            throw new IllegalArgumentException("In an address, only the second street address line can be blank.");
        }
        if (streetAddress1.length() > Voter.MAXIMUM_LINE_LENGTH) {
            throw new IllegalArgumentException("First address line is " + streetAddress1.length() +
                    " characters long, exceeding maximum length of " + Voter.MAXIMUM_LINE_LENGTH + " characters.");
        }
        if ((streetAddress2 != null) && (streetAddress2.length() > Voter.MAXIMUM_LINE_LENGTH)) {
            throw new IllegalArgumentException("First address line is " + streetAddress2.length() +
                    " characters long, exceeding maximum length of " + Voter.MAXIMUM_LINE_LENGTH + " characters.");
        }
        if (city.length() > Voter.MAXIMUM_CITY_LENGTH) {
            throw new IllegalArgumentException("First address line is " + city.length() +
                    " characters long, exceeding maximum length of " + Voter.MAXIMUM_CITY_LENGTH + " characters.");
        }
        if (state.length() != Voter.STATE_LENGTH) {
            throw new IllegalArgumentException("State abbreviation must be exactly " + Voter.STATE_LENGTH + " letters");
        }
        Set<Character> illegalCharacters = ValidationUtil.containsOnlyAlphabeticLetters(state);
        if (!illegalCharacters.isEmpty()) {
            throw new IllegalArgumentException("State can contain only alphabetic letters. " +
                    "Illegal characters found: " + illegalCharacters);
        }
        if (zipCode.length() != Voter.ZIPCODE_LENGTH) {
            throw new IllegalArgumentException("Zip code must contain exactly " + Voter.ZIPCODE_LENGTH + " digits");
        }
        illegalCharacters = ValidationUtil.containsOnlyDigits(zipCode);
        if (!illegalCharacters.isEmpty()) {
            throw new IllegalArgumentException("Zip code can contain only decimal digits. " +
                    "Illegal characters found: " + illegalCharacters);
        }
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = Optional.ofNullable(streetAddress2).orElse("");
        this.city = city;
        this.state = state.toUpperCase();
        this.zipCode = zipCode;
    }

    @Override
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber)
            throws IllegalArgumentException, NullPointerException {
        if (socialSecurityNumber.length() != Voter.SSN_LENGTH) {
            throw new IllegalArgumentException("SSN must contain exactly " + Voter.SSN_LENGTH + " digits");
        }
        Set<Character> illegalCharacters = ValidationUtil.containsOnlyDigits(socialSecurityNumber);
        if (!illegalCharacters.isEmpty()) {
            throw new IllegalArgumentException("SSN can contain only decimal digits. " +
                    "Illegal characters found: " + illegalCharacters);
        }
        this.socialSecurityNumber = socialSecurityNumber;
    }

    @Override
    public String getAuthenticationCode() {
        return authenticationCode;
    }

    public void setAuthenticationCode() {
        Random randomAuthCode = new Random();
        int authCode = randomAuthCode.nextInt(899999) + 100000;
        this.authenticationCode = Integer.toString(authCode);
    }

    @Override
    public ResultEntity submitVote(Ballot ballot, Voter voter, VoteCart voteCart, String voteId) {
        return new ResultEntity(ballot, this, voteCart, voteId);
    }

    @Override
    public void addResult(BallotEntity votedBallot) {
        votedBallots.add(votedBallot);
        votedBallot.getVoters().add(this);
    }

    @Override
    public Set<BallotEntity> getVotedBallots() {
        return votedBallots;
    }
}
