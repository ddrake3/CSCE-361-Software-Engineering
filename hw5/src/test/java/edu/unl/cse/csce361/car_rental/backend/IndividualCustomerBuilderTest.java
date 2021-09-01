package edu.unl.cse.csce361.car_rental.backend;

import edu.unl.cse.csce361.car_rental.rental_logic.CarBuilder;
import edu.unl.cse.csce361.car_rental.rental_logic.IndividualCustomerBuilder;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IndividualCustomerBuilderTest {

    IndividualCustomerBuilder individualCustomerBuilder;

    @Before
    public void setUp() {
        individualCustomerBuilder = new IndividualCustomerBuilder();
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        DatabasePopulator.createModels().forEach(session::saveOrUpdate);
        DatabasePopulator.createCars().forEach(session::saveOrUpdate);
        DatabasePopulator.createCorporateCustomers().forEach(session::saveOrUpdate);
        DatabasePopulator.createIndividualCustomers().forEach(session::saveOrUpdate);
        DatabasePopulator.createRentals(session).forEach(session::saveOrUpdate);
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
    public void testIndividualCustomerBuilderWithNewName() {
        // arrange
        String name = "Derek Drake";
        String streetAddress1 = "Applebees Lane";
        String streetAddress2 = "Apt 365";
        String city = "Lincoln";
        String state = "NE";
        String zipCode = "68504";
        // act
        Customer individualCustomer = individualCustomerBuilder.setName(name).setStreetAddress1(streetAddress1).setStreetAddress2(streetAddress2).setCity(city).setState(state).setZipCode(zipCode).build();
        // assert
        assertEquals(name, individualCustomer.getName());
    }

    @Test
    public void testIndividualCustomerBuilderWithExistingName() {
        // arrange
        String name = "Stu Dent";
        String streetAddress1 = "Fawlty Tower";
        String streetAddress2 = "Room SB32";
        String city = "NeverLand";
        String state = "NE";
        String zipCode = "69999";
        // act
        Customer individualCustomer = individualCustomerBuilder.setName(name).setStreetAddress1(streetAddress1).setStreetAddress2(streetAddress2).setCity(city).setState(state).setZipCode(zipCode).build();
        // assert
        assertEquals(name, individualCustomer.getName());
    }
}
