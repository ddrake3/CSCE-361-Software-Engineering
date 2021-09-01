package edu.unl.cse.csce361.car_rental.rental_logic;

import edu.unl.cse.csce361.car_rental.backend.Backend;
import edu.unl.cse.csce361.car_rental.backend.Model;
import edu.unl.cse.csce361.car_rental.backend.ModelEntity;

public class ModelBuilder {
    private String manufacturer;
    private String model;
    Model.VehicleClass classType;
    Integer numberOfDoors;
    Model.Transmission transmission;
    Model.Fuel fuel;
    Integer fuelEconomyMPG;

    public ModelBuilder(){
        this.manufacturer = null;
        this.model = null;
        this.classType = null;
        this.numberOfDoors = 0;
        this.transmission = null;
        this.fuel = null;
        this.fuelEconomyMPG = 0;
    }

    public ModelBuilder setManufacturer(String manufacturer){
        this.manufacturer = manufacturer;
        return this;
    }

    public ModelBuilder setModel(String model){
        this.model = model;
        return this;
    }

    public ModelBuilder setClassType(Model.VehicleClass classType){
        this.classType = classType;
        return this;
    }

    public ModelBuilder setNumberOfDoors(Integer numberOfDoors){
        this.numberOfDoors = numberOfDoors;
        return this;
    }

    public ModelBuilder setTransmisson(Model.Transmission transmisson){
        this.transmission = transmisson;
        return this;
    }

    public ModelBuilder setFuel(Model.Fuel fuel){
        this.fuel = fuel;
        return this;
    }

    public ModelBuilder setFuelEconomyMPG(Integer fuelEconomyMPG){
        this.fuelEconomyMPG = fuelEconomyMPG;
        return this;
    }

    public Model build(){
        Backend b = Backend.getInstance();
        return b.createModel(this.manufacturer, this.model, this.classType, this.numberOfDoors, this.transmission, this.fuel, this.fuelEconomyMPG);
    }
}
