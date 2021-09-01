package edu.unl.cse.csce361.car_rental.backend;

import edu.unl.cse.csce361.car_rental.rental_logic.CarBuilder;
import edu.unl.cse.csce361.car_rental.rental_logic.CorporateCustomerBuilder;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CorporateCustomerBuilderTest {

    CorporateCustomerBuilder corporateCustomerBuilder;

    @Before
    public void setUp() {
        corporateCustomerBuilder = new CorporateCustomerBuilder();
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
    public void corporateCustomerBuildWithNewName() {
        // arrange
        String name = "RL Designers";
        String streetAddress1 = "456 11th Street";
        String streetAddress2 = "";
        String city = "Broken Bow";
        String state = "NE";
        String zipCode = "68822";
        String corporateAccount = "1234567890";
        double negotiatedRate = 0.75;
        // act
        Customer corporateCustomer = corporateCustomerBuilder.setName(name).setStreetAddress1(streetAddress1).setStreetAddress2(streetAddress2).setCity(city).setState(state).setZipCode(zipCode).setCorporateAccount(corporateAccount).setNegotiatedRate(negotiatedRate).build();
        // assert
        assertEquals(name, corporateCustomer.getName());
    }

    @Test
    public void corporateCustomerBuildWithExistingName() {
        // arrange
        String name = "Cornhusker Airlines";
        String streetAddress1 = "123 Airport Avenue";
        String streetAddress2 = "";
        String city = "Lincoln";
        String state = "NE";
        String zipCode = "68524";
        String corporateAccount = "923410";
        double negotiatedRate = 0.75;
        // act
        Customer corporateCustomer = corporateCustomerBuilder.setName(name).setStreetAddress1(streetAddress1).setStreetAddress2(streetAddress2).setCity(city).setState(state).setZipCode(zipCode).setCorporateAccount(corporateAccount).setNegotiatedRate(negotiatedRate).build();
        // assert
        assertEquals(name, corporateCustomer.getName());
    }


}
