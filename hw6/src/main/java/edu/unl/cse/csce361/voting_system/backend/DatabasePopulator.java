package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.MappingException;
import org.hibernate.Session;

import javax.persistence.PersistenceException;
import java.util.HashSet;
import java.util.Set;

public class DatabasePopulator {

    static Set<Ballot> createBallots() {
        System.out.println("Creating ballots...");
        return Set.of(
                new BallotEntity("Ballot 20"),
                new BallotEntity("Ballot 23"),
                new BallotEntity("Ballot Jax")
        );
    }

    static Set<VoteCart> createVoteCarts() {
        System.out.println("Creating vote carts...");
        return Set.of(
                new VoteCartEntity("Ballot 20", "Mayor", false,Set.of("Pat Mann", "Dawn Keykong")),
                new VoteCartEntity("Ballot 20", "City Council seat",false, Set.of("Inky", "Blinky")),
                new VoteCartEntity("Ballot 20", "Sheriff",false, Set.of("Q. Burte")),
                new VoteCartEntity("Ballot 20", "Shall there be a 25¢ tax on cherries?", true,"cherries"),
                new VoteCartEntity("Ballot 23", "City Council seat", false,Set.of("Spiderman", "Inky")),
                new VoteCartEntity("Ballot 23", "Mayor",false, Set.of("Iron Man", "Captain America","Inky")),
                new VoteCartEntity("Ballot 23", "State Senator",false, Set.of("Edward Newgate", "Gol D Roger", "Silvers Rayleigh")),
                new VoteCartEntity("Ballot 23", "Shall there be a 20¢ tax on corns?", true,"corns")
        );
    }

    static Set<VoterEntity> createVoters() {
        System.out.println("Creating voters...");
        return Set.of(
                new VoterEntity("Jayson Cheng", "518 North", "22nd Street", "Lincoln", "NE", "68503", "933107845"),
                new VoterEntity("Nicholas Fong", "628 South", "23rd Street", "Lincoln", "NE", "68503", "933408125"),
                new VoterEntity("Zheng Nian Yap", "31 T St", "23rd Street", "Lincoln", "NE", "68503", "381901829"),
                new VoterEntity("Derek Drake", "440 South Avenue", "Chris Blvd", "Lincoln", "NE", "68503", "931010371"),
                new VoterEntity("Christopher Bohn", "2323 T St", "Nice Pkwy", "Lincoln", "NE", "68503", "103391891"),
                new VoterEntity("Mary Onsen", "218 S St", "Acorn Pkwy", "Lincoln", "NE", "68504", "139845678"),
                new VoterEntity("Socrates", "3232 A St", "Atlantis", "Omaha", "NE", "68463", "176593891"),
                new VoterEntity("Confucius", "772 East St", "Bad Pkwy", "Omaha", "NE", "68999", "103395253")
        );
    }

    static Set<CommitteeEntity> createCommittees() {
        System.out.println("Creating committees...");
        return Set.of(
                new CommitteeEntity("Ricky Fox", "asdfghjkl1234", "Auditor"),
                new CommitteeEntity("Monkey D Luffy", "qwertyu1010", "Auditor"),
                new CommitteeEntity("Portgas Ace", "mlafds231las", "Auditor"),
                new CommitteeEntity("Audra Harith", "poiuytrewq1234", "Election Official"),
                new CommitteeEntity("Barrack Obama", "zxcvbnm098765", "Election Official"),
                new CommitteeEntity("Mickey Mouse", "kdsal13em1ms", "Election Official")
        );
    }

    static Set<ResultEntity> createResults(Session session) {
        System.out.println("Creating results...");
        Ballot ballot;
        Voter voter;
        VoteCart voteCart;
        ResultEntity result;
        Set<ResultEntity> results = new HashSet<>();

        // first vote result by first voter
        ballot = session.bySimpleNaturalId(BallotEntity.class).load("Ballot 23");
        ballot.publish();
        voter = session.bySimpleNaturalId(VoterEntity.class).load("Derek Drake");
        voteCart = session.bySimpleNaturalId(VoteCartEntity.class).load("Mayor_Ballot 23");;
        result = voter.submitVote(ballot, voter, voteCart, "123456789");
        result.setAnswer("Inky");
        results.add(result);

        // second vote result by first voter
        ballot = session.bySimpleNaturalId(BallotEntity.class).load("Ballot 23");
        voter = session.bySimpleNaturalId(VoterEntity.class).load("Derek Drake");
        voteCart = session.bySimpleNaturalId(VoteCartEntity.class).load("City Council seat_Ballot 23");;
        result = voter.submitVote(ballot, voter, voteCart, "123456789");
        result.setAnswer("Inky");
        results.add(result);

        // third vote result by first voter
        ballot = session.bySimpleNaturalId(BallotEntity.class).load("Ballot 23");
        voter = session.bySimpleNaturalId(VoterEntity.class).load("Derek Drake");
        voteCart = session.bySimpleNaturalId(VoteCartEntity.class).load("Proposition_corns_Ballot 23");;
        result = voter.submitVote(ballot, voter, voteCart, "123456789");
        result.setAnswer("No");
        results.add(result);

        // first vote result by second voter
        ballot = session.bySimpleNaturalId(BallotEntity.class).load("Ballot 23");
        voter = session.bySimpleNaturalId(VoterEntity.class).load("Christopher Bohn");
        voteCart = session.bySimpleNaturalId(VoteCartEntity.class).load("City Council seat_Ballot 23");;
        result = voter.submitVote(ballot, voter, voteCart, "098765321");
        result.setAnswer("No Vote");
        results.add(result);

        // second vote result by second voter
        ballot = session.bySimpleNaturalId(BallotEntity.class).load("Ballot 23");
        voter = session.bySimpleNaturalId(VoterEntity.class).load("Christopher Bohn");
        voteCart = session.bySimpleNaturalId(VoteCartEntity.class).load("Mayor_Ballot 23");;
        result = voter.submitVote(ballot, voter, voteCart, "098765321");
        result.setAnswer("Iron Man");
        results.add(result);

        // first vote result by third voter
        ballot = session.bySimpleNaturalId(BallotEntity.class).load("Ballot 23");
        voter = session.bySimpleNaturalId(VoterEntity.class).load("Socrates");
        voteCart = session.bySimpleNaturalId(VoteCartEntity.class).load("Mayor_Ballot 23");;
        result = voter.submitVote(ballot, voter, voteCart, "456789431");
        result.setAnswer("Iron Man");
        results.add(result);

        // first vote result by forth voter
        ballot = session.bySimpleNaturalId(BallotEntity.class).load("Ballot 23");
        voter = session.bySimpleNaturalId(VoterEntity.class).load("Confucius");
        voteCart = session.bySimpleNaturalId(VoteCartEntity.class).load("Mayor_Ballot 23");;
        result = voter.submitVote(ballot, voter, voteCart, "431456654");
        result.setAnswer("Iron Man");
        results.add(result);
        ballot.end();

        return results;
    }


    static void depopulateTables(Session session) {
        System.out.println("Emptying tables...");
        session.createQuery("delete from ResultEntity").executeUpdate();
        session.createQuery("delete from VoteCartEntity").executeUpdate();
        session.createQuery("delete from BallotEntity").executeUpdate();
        session.createQuery("delete from VoterEntity").executeUpdate();
        session.createQuery("delete from CommitteeEntity").executeUpdate();
    }

    public static void main(String[] args) {
        System.out.println("Creating Hibernate session...");
        Session session = HibernateUtil.getSession();
        System.out.println("Starting Hibernate transaction...");
        session.beginTransaction();
        try {
            depopulateTables(session);
            createBallots().forEach(session::saveOrUpdate);
            createVoteCarts().forEach(session::saveOrUpdate);
            createVoters().forEach(session::saveOrUpdate);
            createCommittees().forEach(session::saveOrUpdate);
            createResults(session).forEach(session::saveOrUpdate);
            System.out.println("Concluding Hibernate transaction...");
            session.getTransaction().commit();
            System.out.println("Success! The database has been populated.");
        } catch (MappingException mappingException) {
            System.err.println("Problem encountered when creating a table. The most likely problem is a missing\n" +
                    "    <mapping class=\"...\"/> tag in hibernate.cfg.xml, but it's possible the\n" +
                    "    problem is that the programmer is attempting to load an interface instead\n" +
                    "    of an entity.");
            StackTraceElement[] trace = mappingException.getStackTrace();
            System.err.println("  " + trace[trace.length - 1]);
            System.err.println("  " + mappingException.getMessage());
            session.getTransaction().rollback();
        } catch (PersistenceException persistenceException) {
            System.err.println("Problem encountered when populating or depopulating a table. It's not clear why\n" +
                    "    this would happen unless it's a network or server failure. But it's probably\n" +
                    "    something completely unexpected.");
            StackTraceElement[] trace = persistenceException.getStackTrace();
            System.err.println("  " + trace[trace.length - 1]);
            System.err.println("  " + persistenceException.getMessage());
            System.err.println("  " + persistenceException.getCause().getCause().getMessage());
            session.getTransaction().rollback();
        } catch (Exception exception) {
            System.err.println("Problem encountered that (probably) has nothing to do with creating and\n" +
                    "    (de)populating tables. This is (most likely) is a plain, old-fashioned\n" +
                    "    programming boo-boo.");
            StackTraceElement[] trace = exception.getStackTrace();
            System.err.println("  " + trace[trace.length - 1]);
            System.err.println("  " + exception.getMessage());
            session.getTransaction().rollback();
        } finally {
            HibernateUtil.closeSession(session);
        }
    }
}
