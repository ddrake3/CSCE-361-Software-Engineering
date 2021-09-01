package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A voting cart consisting the question, and its corresponding voting choices
 */
@Entity
public class VoteCartEntity implements VoteCart {

    /**
     * Retrieves the vote cart that has the specified question and ballot name, if such a vote cart exists
     * @param ballotName The unique name of the ballot
     * @param question The question name of the vote cart
     * @return The specified vote cart if it is present in the database; <code>null</code> otherwise
     */
    static VoteCart getVoteCartByBallotAndQuestion(String ballotName, String question) {
        VoteCart voteCart = null;
        Ballot ballot = BallotEntity.getBallotByName(ballotName);
        for (VoteCart voteCartEntity : ballot.getVoteCartSet()) {
            if (question.equals(voteCartEntity.getVoteQuestion())) {
                voteCart = voteCartEntity;
                break;
            }
        }
        return voteCart;
    }

    /**
     * Removes a vote cart given its id
     * @param voteCartId The id of a given vote cart
     */
    static void removeVoteCartByVoteCardId (String voteCartId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        String hql = "delete from VoteCartEntity C where C.voteCartId='" + voteCartId + "'";
        session.createQuery(hql).executeUpdate();
        session.getTransaction().commit();
    }

    @SuppressWarnings("unused")
    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @Column(length = BallotEntity.MAXIMUM_NAME_LENGTH * 2)
    // Unique id to identify a VoteCart
    private String voteCartId;

    @Column(length = BallotEntity.MAXIMUM_NAME_LENGTH)
    private String question;

    @ManyToOne(fetch = FetchType.EAGER)
    private BallotEntity ballot;

    @Column
    private boolean isProposition;

    @ElementCollection
    private Set<String> answers;

    @Column(length = BallotEntity.MAXIMUM_NAME_LENGTH)
    private String keyword;

    @OneToMany(mappedBy = "voteCart", cascade = CascadeType.ALL)
    private List<ResultEntity> results;

    public VoteCartEntity() {
        super();
    }

    /**
     * Constructor used to create a position vote cart. An exception is thrown if managed to use this constructor to
     * create a proposition vote cart
     * @param ballot The name of the ballot
     * @param question The positional race of the candidates
     * @param isProposition <false>if a position vote cart want to be created</false>. Should be false to use the
     *                      constructor
     * @param answers The list of the candidates for the positional race
     */
    public VoteCartEntity(String ballot, String question, boolean isProposition, Set<String> answers) {
        super();
        if (question == null) {
            throw new NullPointerException("Question cannot be null.");
        }
        this.question = question;
        setBallot(ballot);
        if (this.isProposition = isProposition) {
            throw new IllegalArgumentException("Position vote cart is created instead of Proposition.");
        } else {
            if (answers == null) {
                this.answers = new HashSet<>();
            } else {
                this.answers = new HashSet<>(answers);
            }
            this.answers.add("No Vote");
            this.keyword = question;
            setVoteCartId(question);
            results = new ArrayList<>();
        }
    }

    /**
     * Constructor used to create a proposition vote cart. An exception is thrown if managed to use the constructor to
     * create a position vote cart
     * @param ballot The name of the ballot
     * @param question The proposition
     * @param isProposition <true>if a proposition vote cart want to be created</true>. Should be true to use the
     *                      constructor
     * @param keyword A unique keyword in every ballot to identify the specific vote cart
     */
    public VoteCartEntity(String ballot, String question, boolean isProposition, String keyword) {
        super();
        if (question == null) {
            throw new NullPointerException("Question cannot be null.");
        }
        this.question = question;
        setBallot(ballot);
        if (this.isProposition = isProposition) {
            this.answers = Set.of("Yes","No", "No Vote");
            this.keyword = keyword;
            setVoteCartId(keyword);
            results = new ArrayList<>();
        } else {
            throw new IllegalArgumentException("Proposition vote cart is created instead of Position.");
        }
    }

    public String getVoteCartId() {
        return this.voteCartId;
    }

    public BallotEntity getBallot() {
        return this.ballot;
    }

    @Override
    public String getVoteQuestion() {
        return this.question;
    }

    public boolean getIsProposition() {
        return this.isProposition;
    }

    @Override
    public Set<String> getAnswerList() {
        return this.answers;
    }

    @Override
    public void addResult(ResultEntity result) {
        results.add(result);
        result.setVoteCart(this);
    }

    public void setVoteCartId(String keyword) {
        if (keyword == null) {
            throw new NullPointerException("Keyword cannot be null");
        } else {
            if (isProposition) {
                this.voteCartId = "Proposition_" + keyword + "_" + this.getBallot().getBallotName();
            } else {
                this.voteCartId = keyword + "_" + this.getBallot().getBallotName();
            }
        }
    }

    public void setBallot(String ballot) {
        BallotEntity ballotEntity = null;
        try {
            ballotEntity = HibernateUtil.getSession().bySimpleNaturalId(BallotEntity.class).load(ballot);
        } catch (Exception e) {
            System.err.println("Error while loading ballot: either the required Java class is not a mapped entity\n" +
                    "    (unlikely), or the entity does not have a simple natural ID (also unlikely).");
            System.err.println("  " + e.getMessage());
            System.err.println("Please inform the developer that the error occurred in\n" +
                    "    VoteCart.setBallot(String).");
            ballotEntity = null;
            System.err.println("Resuming, leaving " + this.toString() + " without an assigned ballot.");
        } finally {
            if (ballotEntity != null) {
                ballotEntity.addVoteCart(this);
            } else {
                this.ballot = null;
            }
        }
    }

    public void setBallot(BallotEntity ballot) {
        this.ballot = ballot;
    }
}
