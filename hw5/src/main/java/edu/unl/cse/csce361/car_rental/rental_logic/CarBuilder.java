package edu.unl.cse.csce361.car_rental.rental_logic;

import edu.unl.cse.csce361.car_rental.backend.Backend;
import edu.unl.cse.csce361.car_rental.backend.Car;
import edu.unl.cse.csce361.car_rental.backend.CarEntity;

public class CarBuilder {
    private String model;
    private String color;
    private String licensePlate;
    private String vin;

    public CarBuilder(){
        this.model = null;
        this.color = null;
        this.licensePlate = null;
        this.vin = null;
    }

    public CarBuilder setModel(String model){
        this.model = model;
        return this;
    }

    public CarBuilder setColor(String color){
        this.color = color;
        return this;
    }

    public CarBuilder setLicensePlate(String licensePlate){
        this.licensePlate = licensePlate;
        return this;
    }

    public CarBuilder setVin(String vin){
        this.vin = vin;
        return this;
    }

    public Car build(){
        Backend b = Backend.getInstance();
        return b.createCar(this.model, this.color, this.licensePlate, this.vin);
    }
}
