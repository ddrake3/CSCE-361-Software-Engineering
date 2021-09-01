package edu.unl.cse.csce361.car_rental.rental_logic;

import edu.unl.cse.csce361.car_rental.backend.Backend;
import edu.unl.cse.csce361.car_rental.backend.CorporateCustomer;
import edu.unl.cse.csce361.car_rental.backend.CorporateCustomerEntity;
import edu.unl.cse.csce361.car_rental.backend.Customer;

public class CorporateCustomerBuilder {
    private String name;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zipCode;
    private String corporateAccount;
    private double negotiatedRate;

    public CorporateCustomerBuilder() {
        this.name = null;
        this.streetAddress1 = null;
        this.streetAddress2 = null;
        this.city = null;
        this.state = null;
        this.zipCode = null;
        this.corporateAccount = null;
        this.negotiatedRate = 0;
    }

    public CorporateCustomerBuilder setName(String name){
        this.name = name;
        return this;
    }

    public CorporateCustomerBuilder setStreetAddress1(String streetAddress1){
        this.streetAddress1 = streetAddress1;
        return this;
    }

    public CorporateCustomerBuilder setStreetAddress2(String streetAddress2){
        this.streetAddress2 = streetAddress2;
        return this;
    }

    public CorporateCustomerBuilder setCity(String city){
        this.city = city;
        return this;
    }

    public CorporateCustomerBuilder setState(String state){
        this.state = state;
        return this;
    }

    public CorporateCustomerBuilder setZipCode(String zipCode){
        this.zipCode = zipCode;
        return this;
    }

    public CorporateCustomerBuilder setCorporateAccount(String corporateAccount){
        this.corporateAccount = corporateAccount;
        return this;
    }

    public CorporateCustomerBuilder setNegotiatedRate(double negotiatedRate){
        this.negotiatedRate = negotiatedRate;
        return this;
    }

    public Customer build(){
        Backend b = Backend.getInstance();
        return b.createCorporateCustomer(this.name, this.streetAddress1, this.streetAddress2, this.city, this.state, this.zipCode, this.corporateAccount, this.negotiatedRate);
    }

}
