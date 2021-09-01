package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.*;

/**
 * Hibernate implementation of {@link Ballot}
 */
@Entity
public class BallotEntity implements Ballot {

    public static final int MAXIMUM_NAME_LENGTH = 96;

    /**
     * Retrieves the collection of ballot from database
     * @return The set of ballots
     */
    static Set<Ballot> getBallots() {
        Session session = HibernateUtil.getSession();
        String hql = "from BallotEntity";
        List<Ballot> votePositionList = session.createQuery(hql, Ballot.class).list();
        return Set.copyOf(votePositionList);
    }

    /**
     * Retrieves the ballot that has the specified ballot name, if such a ballot exists
     * @param ballotName The unique name of the ballot
     * @return The specified ballot if it is present in the database; <code>null</code> otherwise
     */
    static Ballot getBallotByName(String ballotName) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Ballot ballot = null;
        try {
            ballot = session.bySimpleNaturalId(BallotEntity.class).load(ballotName);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            session.getTransaction().rollback();
            System.err.println("Could not load Ballot " + ballotName + ". " + exception.getMessage());
        }
        return ballot;
    }

    @SuppressWarnings("unused")
    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @Column(length = MAXIMUM_NAME_LENGTH)
    private String ballotName;

    @OneToMany(mappedBy = "ballot", cascade = CascadeType.ALL)
    private Set<VoteCartEntity> voteCartSet;

    @Column
    boolean isPublished;

    @Column
    boolean isEnded;

    @OneToMany(mappedBy = "ballot", cascade = CascadeType.ALL)
    private List<ResultEntity> results;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<VoterEntity> voters;

    public BallotEntity() {
        super();
    }

    public BallotEntity(String ballotName) {
        super();
        this.ballotName = ballotName;
        this.voteCartSet = new HashSet<>();
        this.isPublished = false;
        this.isEnded = false;
        results = new ArrayList<>();
        voters = new HashSet<>();
    }

    @Override
    public String getBallotName() {
        return this.ballotName;
    }

    @Override
    public Set<VoteCart> getVoteCartSet() {
        return Collections.unmodifiableSet(this.voteCartSet);
    }

    @Override
    public Set<VoteCart> getPositionVoteCartSet() {
        Set<VoteCart> voteCart = new HashSet<>();
        for (VoteCart voteCartEntity : getVoteCartSet()) {
            if (voteCartEntity instanceof VoteCartEntity) {
                if (!((VoteCartEntity)voteCartEntity).getIsProposition()) {
                    voteCart.add(voteCartEntity);
                }
            }
        }
        return voteCart;
    }

    @Override
    public Set<VoteCart> getPropositionVoteCartSet() {
        Set<VoteCart> voteCart = new HashSet<>();
        for (VoteCart voteCartEntity : getVoteCartSet()) {
            if (voteCartEntity instanceof VoteCartEntity) {
                if (((VoteCartEntity)voteCartEntity).getIsProposition()) {
                    voteCart.add(voteCartEntity);
                }
            }
        }
        return voteCart;
    }

    public void addVoteCart(VoteCartEntity voteCartEntity) {
        this.voteCartSet.add(voteCartEntity);
        voteCartEntity.setBallot(this);
    }

    public void removeVoteCart(VoteCartEntity voteCartEntity) {
        this.voteCartSet.remove(voteCartEntity);
    }

    @Override
    public boolean isPublished() {
        return this.isPublished;
    }

    @Override
    public boolean isEnded() {
        return this.isEnded;
    }

    @Override
    public void publish() {
        if (this.isEnded) {
            throw new IllegalStateException("The ballot has ended.");
        } else {
            this.isPublished = true;
        }
    }

    @Override
    public void end() {
        if (this.isPublished) {
            this.isEnded = true;
        } else {
            throw new IllegalStateException("The ballot has not been published.");
        }
    }

    @Override
    public void addResult(ResultEntity result) {
        results.add(result);
        result.setBallot(this);
    }

    @Override
    public Set<VoterEntity> getVoters() {
        return voters;
    }

    public String getStatus() {
        String status;
        if (this.isEnded) {
            status = "Closed";
        } else if (this.isPublished) {
            status = "OnGoing";
        } else {
            status = "Open";
        }
        return status;
    }
}
