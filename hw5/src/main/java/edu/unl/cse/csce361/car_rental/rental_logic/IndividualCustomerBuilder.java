package edu.unl.cse.csce361.car_rental.rental_logic;

import edu.unl.cse.csce361.car_rental.backend.Backend;
import edu.unl.cse.csce361.car_rental.backend.Customer;
import edu.unl.cse.csce361.car_rental.backend.IndividualCustomer;
import edu.unl.cse.csce361.car_rental.backend.IndividualCustomerEntity;

public class IndividualCustomerBuilder {

    private String name;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zipCode;

    public IndividualCustomerBuilder() {
        this.name = null;
        this.streetAddress1 = null;
        this.streetAddress2 = null;
        this.city = null;
        this.state = null;
        this.zipCode = null;
    }

    public IndividualCustomerBuilder setName(String name){
        this.name = name;
        return this;
    }

    public IndividualCustomerBuilder setStreetAddress1(String streetAddress1){
        this.streetAddress1 = streetAddress1;
        return this;
    }

    public IndividualCustomerBuilder setStreetAddress2(String streetAddress2){
        this.streetAddress2 = streetAddress2;
        return this;
    }

    public IndividualCustomerBuilder setCity(String city){
        this.city = city;
        return this;
    }

    public IndividualCustomerBuilder setState(String state){
        this.state = state;
        return this;
    }

    public IndividualCustomerBuilder setZipCode(String zipCode){
        this.zipCode = zipCode;
        return this;
    }

    public Customer build(){
        Backend b = Backend.getInstance();
        return b.createIndividualCustomer(this.name, this.streetAddress1, this.streetAddress2, this.city, this.state, this.zipCode);
    }

}
