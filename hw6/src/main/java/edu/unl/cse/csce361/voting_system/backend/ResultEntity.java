package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.Session;

import javax.persistence.*;
import java.util.*;

/**
 * Hibernate representation of rental association between a {@link Ballot}, a {@link Voter}, and a {@link VoteCart}.
 */
@Entity
public class ResultEntity {

    /**
     * Retrieve the statistic of ballot result from database
     * @param ballotName The name of ballot
     * @return The set of map result key with counts value
     */
    static Map<List<String>,Integer> getResultInfo(String ballotName) {
        Map<List<String>,Integer> resultList = new HashMap<>();
        Session session = HibernateUtil.getSession();
        String hql = "select R from ResultEntity R, BallotEntity B where R.ballot=B.id and B.ballotName='" + ballotName + "'";
        List<ResultEntity> results = session.createQuery(hql,ResultEntity.class).list();
        for (ResultEntity result : results) {
            List<String> temp = new ArrayList<>();
            temp.add(result.getVoteCart().getVoteQuestion());
            temp.add(result.getAnswer());
            if (resultList.containsKey(temp)) {
                int val = resultList.get(temp)+1;
                resultList.put(temp,val);
            } else {
                resultList.put(temp,1);
            }
        }
        return resultList;
    }

    /**
     * Retrieve the statistic of vote result from database with specified vote confirmation code.
     * @param voteId The vote confirmation code
     * @return The set of map for question as key and chosen answer as value
     */
    static Map<String,String> getVoteResults(String voteId) {
        Map<String,String> resultList = new HashMap<>();
        Session session = HibernateUtil.getSession();
        String hql = "select R from ResultEntity R where R.voteId='" + voteId + "'";
        List<ResultEntity> results = session.createQuery(hql,ResultEntity.class).list();
        for (ResultEntity result : results) {
            resultList.put(result.getVoteCart().getVoteQuestion(), result.getAnswer());
        }
        return resultList;
    }

    /**
     * Retrieve the statistic of ballot winner from database
     * @param ballotName The name of ballot
     * @return The set of map result with question key and answer set value
     */
    static Map<String,Map<String,Integer>> getWinnerInfo(String ballotName) {
        Map<String,Map<String,Integer>> resultList = new HashMap<>();
        Session session = HibernateUtil.getSession();
        String hql = "select R from ResultEntity R, BallotEntity B where R.ballot=B.id and B.ballotName='" + ballotName + "'";
        List<ResultEntity> results = session.createQuery(hql,ResultEntity.class).list();
        for (ResultEntity result : results) {
            String question = result.getVoteCart().getVoteQuestion();
            Map<String,Integer> tempMap = new HashMap<>();
            if (resultList.containsKey(question)) {
                if (resultList.get(question).containsKey(result.getAnswer())) {
                    tempMap.putAll(resultList.get(question));
                    int val = tempMap.get(result.getAnswer())+1;
                    tempMap.put(result.getAnswer(), val);
                    resultList.put(question,tempMap);
                } else {
                    tempMap.putAll(resultList.get(question));
                    tempMap.put(result.getAnswer(), 1);
                    resultList.put(question,tempMap);
                }
            } else {
                tempMap.put(result.getAnswer(), 1);
                resultList.put(question,tempMap);
            }
        }
        return resultList;
    }

    /* POJO code */

    @SuppressWarnings("unused")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long resultId;

    @ManyToOne(fetch = FetchType.EAGER)
    private BallotEntity ballot;

    @ManyToOne(fetch = FetchType.EAGER)
    private VoteCartEntity voteCart;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private String voteId;

    public ResultEntity() {
        super();
    }


    public ResultEntity(Ballot ballot, Voter voter, VoteCart voteCart, String voteId) throws IllegalArgumentException, NullPointerException{
        if (ballot == null) {
            throw new NullPointerException("Ballot cannot be null.");
        }
        if (voteCart == null) {
            throw new NullPointerException("Vote cart cannot be null.");
        }
        voter.addResult((BallotEntity) ballot);
        ballot.addResult(this);
        voteCart.addResult(this);
        this.voteId = voteId;
    }

    void setBallot(BallotEntity ballotEntity) {
        this.ballot = ballotEntity;
    }

    void setVoteCart(VoteCartEntity voteCartEntity) {
        this.voteCart = voteCartEntity;
    }

    void setAnswer(String answer) throws IllegalArgumentException {
        Set<String> answerList = voteCart.getAnswerList();
        if (answerList.contains(answer)) {
            this.answer = answer;
        }
        else {
            throw new IllegalArgumentException("The answer doesn't exist in the answer list.");
        }
    }

    public Ballot getBallot() {
        return this.ballot;
    }

    public VoteCart getVoteCart() {
        return this.voteCart;
    }

    public String getAnswer() {
        return this.answer;
    }
}
