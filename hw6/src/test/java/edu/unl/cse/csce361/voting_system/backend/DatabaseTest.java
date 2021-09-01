package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class DatabaseTest {

    Session session;

    @Before
    public void setUp() {
        session = HibernateUtil.getSession();
        session.beginTransaction();
        DatabasePopulator.createBallots().forEach(session::saveOrUpdate);
        DatabasePopulator.createVoteCarts().forEach(session::saveOrUpdate);
        DatabasePopulator.createVoters().forEach(session::saveOrUpdate);
        DatabasePopulator.createCommittees().forEach(session::saveOrUpdate);
        DatabasePopulator.createResults(session).forEach(session::saveOrUpdate);
        session.getTransaction().commit();
    }

    @After
    public void tearDown() {
        session.beginTransaction();
        DatabasePopulator.depopulateTables(session);
        session.getTransaction().commit();
    }

    @Test
    public void testGetBallots() {
        // arrange
        Set<Ballot> expectedBallots = Set.of( BallotEntity.getBallotByName("Ballot 20"),
                                              BallotEntity.getBallotByName("Ballot 23"),
                                              BallotEntity.getBallotByName("Ballot Jax") );
        // act
        Set<Ballot> actualBallots = BallotEntity.getBallots();
        // assert
        assertEquals(expectedBallots, actualBallots);
    }

    @Test
    public void testGetBallotByName() {
        // arrange
        String ballotName = "Ballot 20";
        // act
        Ballot ballot = BallotEntity.getBallotByName(ballotName);
        // assert
        assertEquals(ballotName, ballot.getBallotName());
    }

    @Test
    public void testGetVoteCartByBallotAndQuestion() {
        // arrange
        String ballotName = "Ballot 20";
        String question = "Mayor";
        Set<String> expectedAnswers = Set.of("Pat Mann", "Dawn Keykong", "No Vote");
        // act
        VoteCart voteCart = VoteCartEntity.getVoteCartByBallotAndQuestion(ballotName, question);
        // assert
        assertEquals(question, voteCart.getVoteQuestion());
        assertEquals(expectedAnswers, voteCart.getAnswerList());
    }

    @Test
    public void testRemoveVoteCartByVoteCartId() {
        // arrange
        String ballotName = "Ballot 20";
        String question = "Mayor";
        Ballot ballot = BallotEntity.getBallotByName(ballotName);
        VoteCart voteCart = VoteCartEntity.getVoteCartByBallotAndQuestion(ballotName, question);
        Set<VoteCart> expectedVoteCartSet = ballot.getVoteCartSet();
        // act
        VoteCartEntity.removeVoteCartByVoteCardId(((VoteCartEntity) voteCart).getVoteCartId());
        // assert
        assertEquals(expectedVoteCartSet, ballot.getVoteCartSet());
    }

    @Test
    public void testGetVoterByName() {
        // arrange
        String voterName = "Jayson Cheng";
        String expectedVoterAddressStart = "518 North";
        // act
        Voter voter = VoterEntity.getVoterByName(voterName);
        // assert
        assertTrue(voter.getAddress().startsWith(expectedVoterAddressStart));
    }

    @Test
    public void testGetCommitteeByName() {
        // arrange
        String committeeName = "Barrack Obama";
        String expectedCommitteeJobTitle = "Election Official";
        // act
        Committee committee = CommitteeEntity.getCommitteeByName(committeeName);
        // assert
        assertEquals(expectedCommitteeJobTitle, committee.getJobTitle());
    }
}