package edu.unl.cse.csce361.voting_system.backend;

/**
 * Represents two types of election committee, which consists of Auditor and Election Official.
 */
public interface Committee {

    /** The maximum length of each of the committee's name */
    int MAXIMUM_LINE_LENGTH = 48;
    /** The maximum length of each of the committee's password */
    int MAXIMUM_PASSWORD_LENGTH = 128;

    /**
     * Provide's the committee's name.
     *
     * @return the committee's name
     */
    String getName();

    /**
     * Provide's the committee's password.
     *
     * @return the committee's password
     */
    String getPassword();

    /**
     * Sets the committee's password.
     */
    void setPassword(String password);

    /**
     * Provide's the committee's job title.
     *
     * @return the committee's job title
     */
    String getJobTitle();
}
