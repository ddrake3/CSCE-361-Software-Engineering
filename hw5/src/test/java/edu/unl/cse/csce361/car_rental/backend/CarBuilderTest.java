package edu.unl.cse.csce361.car_rental.backend;

import edu.unl.cse.csce361.car_rental.rental_logic.CarBuilder;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CarBuilderTest {

    CarBuilder carBuilder;

    @Before
    public void setUp() {
        carBuilder = new CarBuilder();
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
    public void carBuilderTestWithNewVin(){
        // arrange
        String model = "Explorer";
        String color = "White";
        String licensePlate = "999 ZYX";
        String vin = "ZABCDEFGH12345678";
        // act
        Car car = carBuilder.setModel(model).setColor(color).setLicensePlate(licensePlate).setVin(vin).build();
        // assert
        assertEquals(vin, car.getVin());
    }

    @Test
    public void carBuilderTestWithExistingModel(){
        // arrange
        String model = "Bolt";
        String color = "Yellow";
        String licensePlate = "SCHEME";
        String vin = "1MEYG00DBYE543210";
        // act
        Car car = carBuilder.setModel(model).setColor(color).setLicensePlate(licensePlate).setVin(vin).build();
        // assert
        assertEquals(vin, car.getVin());
    }

}
