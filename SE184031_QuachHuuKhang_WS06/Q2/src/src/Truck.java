/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class Truck extends Vehicle {

    private double cargoCapacity;

    public Truck(String make, String model, int year, String fuelType, double fuelEfficiency) {
        super(make, model, year, fuelType, fuelEfficiency);
    }
    

    public Truck(String make, String model, int year, String fuelType, double fuelEfficiency, double cargoCapacity) {
        super(make, model, year, fuelType, fuelEfficiency);
        this.cargoCapacity = cargoCapacity;
    }

    public double getCargoCapacity() {
        return cargoCapacity;
    }

    public void setCargoCapacity(double cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    @Override
    public double calculateFuelEfficiency() {
        return super.getFuelEfficiency()*(1.0 / (1.0 + ( cargoCapacity / 1000.0)));
    }

    @Override
    public double calculateDistanceTraveled() {
        return calculateFuelEfficiency()*super.getFuelEfficiency();
    }

    @Override
    public double getMaxSpeed() {
      return 80;
    }

}
