package edu.unl.cse.csce361.car_rental.backend;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class BackendTest {

    Backend backend;

    @Before
    public void setUp() {
        backend = Backend.getInstance();
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
    public void testGetCustomer() {
        // arrange
        String customerName = "Mary O'Kart";
        String expectedCustomerAddressStart = "1 Racing Road";
        // act
        Customer customer = backend.getCustomer(customerName);
        // assert
        assertTrue(customer.getAddress().startsWith(expectedCustomerAddressStart));
    }

    @Test
    public void testGetModels() {
        // arrange
        String vehicleClass = "Midsized";
        Set<String> expectedModelNames = Set.of("Malibu", "Model 3");
        // act
        Set<Model> models = backend.getModels(vehicleClass);
        Set<String> actualModelNames = models.stream().map(Model::getModel).collect(Collectors.toSet());
        // assert
        assertEquals(expectedModelNames, actualModelNames);
    }

    @Test
    public void testCreateModelWithNewModel() {
        // arrange
        String manufacturer = "Ford";
        String name = "Model A";
        Model.VehicleClass classType = Model.VehicleClass.OTHER;
        int numberOfDoors = 2;
        Model.Transmission transmission = Model.Transmission.MANUAL;
        Model.Fuel fuel = Model.Fuel.GASOLINE;
        int fuelEconomy = 14;
        // act
        Model model = backend.createModel(manufacturer, name, classType, numberOfDoors, transmission, fuel,
                fuelEconomy);
        // assert
        assertEquals(name, model.getModel());
        assertEquals(transmission, model.getTransmission());
    }

    @Test
    public void testCreateModelWithExistingModel() {
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
        Model model = backend.createModel(manufacturer, name, classType, numberOfDoors, specifiedTransmission, fuel,
                fuelEconomy);
        // assert
        assertEquals(name, model.getModel());
        assertEquals(expectedTransmission, model.getTransmission());
    }

    @Test
    public void testCreateCarWithNewVin() {
        // arrange
        String vin = "0000000000ABCDEFG";
        String color = "Blue";
        String licensePlate = "456 ABC";
        String model = "Mailibu";
        // act
        Car car = backend.createCar(model, color, licensePlate, vin);
        // assert
        assertEquals(vin, car.getVin());
    }

    @Test
    public void testCreateCarWithExistingModel() {
        // arrange
        String vin = "1234567890ABCDEFG";
        String color = "Blue";
        String licensePlate = "123 ABC";
        String model = "Ranger";
        // act
        Car car = backend.createCar(model, color, licensePlate, vin);
        // assert
        assertEquals(vin, car.getVin());
    }

    @Test
    public void testCreateCorporateCustomerWithNewName() {
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
        Customer corporateCustomer = backend.createCorporateCustomer(name, streetAddress1, streetAddress2, city, state, zipCode, corporateAccount, negotiatedRate);
        // assert
        assertEquals(name, corporateCustomer.getName());
    }

    @Test
    public void testCreateCorporateCustomerWithExistingName() {
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
        Customer corporateCustomer = backend.createCorporateCustomer(name, streetAddress1, streetAddress2, city, state, zipCode, corporateAccount, negotiatedRate);
        // assert
        assertEquals(name, corporateCustomer.getName());
    }

    @Test
    public void testIndividualCustomerWithNewName() {
        // arrange
        String name = "Derek Drake";
        String streetAddress1 = "Applebees Lane";
        String streetAddress2 = "Apt 365";
        String city = "Lincoln";
        String state = "NE";
        String zipCode = "68504";
        // act
        Customer individualCustomer = backend.createIndividualCustomer(name, streetAddress1, streetAddress2, city, state, zipCode);
        // assert
        assertEquals(name, individualCustomer.getName());
    }

    @Test
    public void testIndividualCustomerWithExistingName() {
        // arrange
        String name = "Stu Dent";
        String streetAddress1 = "Fawlty Tower";
        String streetAddress2 = "Room SB32";
        String city = "NeverLand";
        String state = "NE";
        String zipCode = "69999";
        // act
        Customer individualCustomer = backend.createIndividualCustomer(name, streetAddress1, streetAddress2, city, state, zipCode);
        // assert
        assertEquals(name, individualCustomer.getName());
    }

    @Test
    public void testIndividualCustomerWithPaymentInfo() {
        // arrange
        String name = "Rock Lee";
        String streetAddress1 = "96 Plumbers Parkway";
        String streetAddress2 = "Apt 365";
        String city = "Lincoln";
        String state = "NE";
        String zipCode = "68504";
        String paymentCardNumber = "4000123456789010";
        int paymentCardExpirationMonth = 6;
        int paymentCardExpirationYear = 2024;
        String paymentCardCvv = "123";
        // act
        Customer individualCustomer = backend.createIndividualCustomer(name, streetAddress1, streetAddress2, city, state, zipCode, paymentCardNumber, paymentCardExpirationMonth, paymentCardExpirationYear,
                paymentCardCvv);
        // assert
        assertEquals(name, individualCustomer.getName());
        assertEquals("************9010", individualCustomer.getPaymentInformation());
    }
}