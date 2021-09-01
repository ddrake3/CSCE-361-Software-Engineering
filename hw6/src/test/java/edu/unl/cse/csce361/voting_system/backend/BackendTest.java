package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class BackendTest {

    Backend backend;

    @Before
    public void setUp() {
        backend = Backend.getInstance();
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        DatabasePopulator.createBallots().forEach(session::saveOrUpdate);
        DatabasePopulator.createVoteCarts().forEach(session::saveOrUpdate);
        DatabasePopulator.createVoters().forEach(session::saveOrUpdate);
        DatabasePopulator.createCommittees().forEach(session::saveOrUpdate);
        DatabasePopulator.createResults(session);
        session.getTransaction().commit();
    }

    @After
    public void tearDown() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        DatabasePopulator.depopulateTables(session);
        session.getTransaction().commit();
    }

    @Test
    public void testGetVoter() {
        // arrange
        String voterName = "Jayson Cheng";
        String expectedVoterAddressStart = "518 North";
        // act
        Voter voter = backend.getVoter(voterName);
        // assert
        assertTrue(voter.getAddress().startsWith(expectedVoterAddressStart));
    }

    @Test
    public void testGetCommittee() {
        // arrange
        String committeeName = "Ricky Fox";
        String expectedCommitteePassword = "asdfghjkl1234";
        String expectedCommitteeTitle = "Auditor";
        // act
        Committee committee = backend.getCommittee(committeeName);
        // assert
        assertEquals(expectedCommitteePassword, committee.getPassword());
        assertEquals(expectedCommitteeTitle, committee.getJobTitle());
    }

    @Test
    public void testGetBallot() {
        // arrange
        String ballotName = "Ballot 20";
        // act
        Ballot ballot = backend.getBallot(ballotName);
        // assert
        assertEquals(ballotName, ballot.getBallotName());
    }

    @Test
    public void testGetVoteCart() {
        // arrange
        String ballotName = "Ballot 20";
        String question = "Mayor";
        // act
        VoteCart voteCart = backend.getVoteCart(ballotName, question);
        // assert
        assertEquals(question, voteCart.getVoteQuestion());
    }

    @Test
    public void testGetBallotSet() {
        // arrange
        Set<Ballot> expectedBallots = Set.of(backend.getBallot("Ballot 20"),
                                     backend.getBallot("Ballot 23"),
                                     backend.getBallot("Ballot Jax"));
        // act
        Set<Ballot> actualBallots = backend.getBallotSet();
        // assert
        assertEquals(expectedBallots, actualBallots);
    }

    @Test
    public void testCreateBallot() {
        // arrange
        String ballotName = "Fall 2020 Ballot";
        // act
        Ballot ballot = backend.createBallot(ballotName);
        // assert
        assertEquals(ballotName, ballot.getBallotName());
    }

    @Test
    public void testCreateVoter() {
        // arrange
        String name = "Roronoa Zoro";
        String address1 = "31 T St";
        String address2 = "22nd Street";
        String city = "Wano";
        String state = "WA";
        String zipCode = "98503";
        String socialSecurityNumber = "913923458";
        // act
        Voter voter = backend.createVoter(name, address1, address2, city, state, zipCode, socialSecurityNumber);
        // assert
        assertEquals(name, voter.getName());
        assertTrue(voter.getAddress().startsWith(address1));
        assertEquals(socialSecurityNumber, voter.getSocialSecurityNumber());
        assertEquals(6, voter.getAuthenticationCode().length());
    }

    @Test
    public void testCreateCommittee() {
        // arrange
        String name = "Test Committee";
        String password = "TestPassword123";
        String jobTitle = "Auditor";
        // act
        Committee committee = backend.createCommittee(name, password, jobTitle);
        // assert
        assertEquals(name, committee.getName());
        assertEquals(password, committee.getPassword());
        assertEquals(jobTitle, committee.getJobTitle());
    }

    @Test
    public void testUpdateCommitteePassword() {
        // arrange
        String expectedPassword = "NotAPassword123";
        Committee committee = backend.getCommittee("Monkey D Luffy");
        // act
        committee.setPassword(expectedPassword);
        committee = backend.updateCommitteePassword(committee);
        // assert
        assertEquals(expectedPassword, committee.getPassword());
    }

    @Test
    public void testCreatePositionVoteCart() {
        // arrange
        String ballotName = "Ballot 20";
        String expectedQuestion = "Governor";
        Set<String> expectedAnswers = Set.of("No Vote", "Brook", "Donquixote Doflamingo");
        // act
        VoteCart voteCart = backend.createPositionVoteCart(ballotName, expectedQuestion, expectedAnswers);
        // assert
        assertEquals(expectedQuestion, voteCart.getVoteQuestion());
        assertEquals(expectedAnswers, voteCart.getAnswerList());

    }

    @Test
    public void testCreatePropositionVoteCart() {
        // arrange
        String ballotName = "Ballot 20";
        String expectedQuestion = "Shall there be a 10¢ tax on gum?";
        String keyword = "gum";
        Set<String> expectedAnswers = Set.of("Yes", "No", "No Vote");
        // act
        VoteCart voteCart = backend.createPropositionVoteCart(ballotName, expectedQuestion, keyword);
        // assert
        assertEquals(expectedQuestion, voteCart.getVoteQuestion());
        assertEquals(expectedAnswers, voteCart.getAnswerList());
    }

    @Test
    public void testCreateResult() {
        // arrange
        Ballot ballot = backend.getBallot("Ballot 20");
        Voter voter = backend.getVoter("Derek Drake");
        VoteCart voteCart = backend.getVoteCart("Ballot 20", "Mayor");
        String answer = "Dawn Keykong";
        // act
        ResultEntity resultEntity = backend.createResult(ballot, voter, voteCart, answer, "343140567");
        // assert
        assertEquals(ballot, resultEntity.getBallot());
        assertEquals(voteCart, resultEntity.getVoteCart());
        assertEquals(answer, resultEntity.getAnswer());
    }

    @Test
    public void testRemoveVoteCartFromBallot() {
        // arrange
        Ballot ballot = backend.getBallot("Ballot 23");
        VoteCart voteCartToRemove = backend.getVoteCart("Ballot 23", "City Council seat");
        backend.getVoteCart("Ballot 23", "Mayor");
        Set<VoteCart> expectedVoteCartSet = Set.of(backend.getVoteCart("Ballot 23", "Mayor"),
                backend.getVoteCart("Ballot 23","State Senator"),
                backend.getVoteCart("Ballot 23","Shall there be a 20¢ tax on corns?"));
        // act
        backend.removeVoteCartFromBallot(ballot, voteCartToRemove);
        // assert
        assertEquals(expectedVoteCartSet, ballot.getVoteCartSet());
    }

    @Test
    public  void testProceedBallotWhenPublished() {
        // arrange
        Ballot ballot = backend.getBallot("Ballot 20");
        ballot.publish();
        // act
        ballot = backend.proceedBallot(ballot);
        // assert
        assertTrue(ballot.isEnded());
    }

    @Test
    public void testProceedBallotWhenNotPublished() {
        // arrange
        Ballot ballot = backend.getBallot("Ballot 20");
        // act
        ballot = backend.proceedBallot(ballot);
        // assert
        assertTrue(ballot.isPublished());
    }

    @Test
    public void testGetVoterHasVoted() {
        // arrange
        Ballot ballot = backend.getBallot("Ballot 20");
        ballot.publish();
        Voter voter = backend.getVoter("Derek Drake");
        VoteCart voteCart = backend.getVoteCart("Ballot 20", "Mayor");
        String answer = "Dawn Keykong";
        backend.createResult(ballot, voter, voteCart, answer, "343140567");
        // act
        boolean voted = backend.getVoterHasVoted(voter, ballot);
        // assert
        assertTrue(voted);
    }
}
