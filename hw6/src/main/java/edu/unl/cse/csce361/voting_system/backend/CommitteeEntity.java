package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * Hibernate implementation of {@link Committee}.
 */
@Entity
public class CommitteeEntity implements Committee {

    /* Database code */

    /**
     * Retrieves the committee that has the specified name, if such a committee exists.
     *
     * @param name The name of the committee
     * @return The specified committee if it is present in the database; <code>null</code> otherwise
     */
    static Committee getCommitteeByName(String name) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Committee committee = null;
        try {
            committee = session.bySimpleNaturalId(CommitteeEntity.class).load(name);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            System.err.println("Could not load Committee " + name + ". " + exception.getMessage());
        }
        return committee;
    }

    /* POJO code */

    @SuppressWarnings("unused")
    @Id
    @GeneratedValue
    private Long committeeId;

    @NaturalId
    @Column(length = Committee.MAXIMUM_LINE_LENGTH)   // apparently we have to define the size to enforce NaturalID's uniqueness?
    private String name;

    @Column(nullable = false, length = Committee.MAXIMUM_PASSWORD_LENGTH)
    private String password;

    @Column(nullable = false, length = Committee.MAXIMUM_LINE_LENGTH)
    private String jobTitle;

    public CommitteeEntity() {        // required 0-argument constructor
        super();
    }

    public CommitteeEntity(String name, String password, String jobTitle)
            throws IllegalArgumentException, NullPointerException {
        this.name = name;
        this.password = password;
        this.jobTitle = jobTitle;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws IllegalArgumentException, NullPointerException {
        if (password == null) {
            throw new NullPointerException("The password cannot be null.");
        }
        if (password.isBlank()) {
            throw new IllegalArgumentException("The password cannot be blank.");
        }
        if (password.length() > Committee.MAXIMUM_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Password is " + password.length() +
                    " characters long, exceeding maximum length of " + Committee.MAXIMUM_PASSWORD_LENGTH + " characters.");
        }
        this.password = password;
    }

    @Override
    public String getJobTitle() {
        return jobTitle;
    }
}
