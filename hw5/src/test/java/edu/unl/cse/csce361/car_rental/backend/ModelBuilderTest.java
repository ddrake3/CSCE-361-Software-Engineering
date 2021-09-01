package edu.unl.cse.csce361.car_rental.backend;

import edu.unl.cse.csce361.car_rental.rental_logic.IndividualCustomerBuilder;
import edu.unl.cse.csce361.car_rental.rental_logic.ModelBuilder;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModelBuilderTest {

    ModelBuilder modelBuilder;

    @Before
    public void setUp() {
        modelBuilder = new ModelBuilder();
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
    public void testModelBuilderWithNewModel() {
        // arrange
        String manufacturer = "Ford";
        String name = "Model A";
        Model.VehicleClass classType = Model.VehicleClass.OTHER;
        int numberOfDoors = 2;
        Model.Transmission transmission = Model.Transmission.MANUAL;
        Model.Fuel fuel = Model.Fuel.GASOLINE;
        int fuelEconomy = 14;
        // act
        Model model = modelBuilder.setManufacturer(manufacturer).setModel(name).setClassType(classType).setNumberOfDoors(numberOfDoors).setFuel(fuel).setTransmisson(transmission).setFuelEconomyMPG(fuelEconomy).build();
        // assert
        assertEquals(name, model.getModel());
        assertEquals(transmission, model.getTransmission());
    }

    @Test
    public void testIndividualCustomerBuilderWithExistingName() {
        // arrange
        String manufacturer = "Ford";
        String name = "Ranger";
        Model.VehicleClass classType = Model.VehicleClass.TRUCK;
        int numberOfDoors = 2;
        Model.Transmission specifiedTransmission = Model.Transmission.MANUAL;
        Model.Transmission expectedTransmission = Model.Transmission.AUTOMATIC;
        Model.Fuel fuel = Model.Fuel.GASOLINE;
        int fuelEconomy = 26;
        // act
        Model model = modelBuilder.setManufacturer(manufacturer).setModel(name).setClassType(classType).setNumberOfDoors(numberOfDoors).setFuel(fuel).setTransmisson(specifiedTransmission).setFuelEconomyMPG(fuelEconomy).build();
        // assert
        assertEquals(name, model.getModel());
        assertEquals(expectedTransmission, model.getTransmission());
    }
}
