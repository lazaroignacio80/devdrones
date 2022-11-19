package com.dev.drones.model;

import com.dev.drones.model.type.Model;
import com.dev.drones.model.type.State;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "drones")
public class Drone{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "serialNumber")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "model")
    private Model model;

    @Column(name = "weightLimit")
    private double weightLimit;

    @Column(name = "batteryCapacity")
    private double batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    public Drone() {
        super();
    }

    public Drone(String serialNumber,
                 Model model,
                 double weightLimit,
                 double batteryCapacity,
                 State state) {
        super();
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(double weightLimit) {
        this.weightLimit = weightLimit;
    }

    public double getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(double batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Drone [id=" + this.getId() + ", serialNumber=" + serialNumber + ", model=" + model + ", weightLimit=" + weightLimit + ", batteryCapacity=" + batteryCapacity + ", state=" + state +"]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drone drone = (Drone) o;
        return Double.compare(drone.weightLimit, weightLimit) == 0 && Double.compare(drone.batteryCapacity, batteryCapacity) == 0 && serialNumber.equals(drone.serialNumber) && model == drone.model && state == drone.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, model, weightLimit, batteryCapacity, state);
    }
}
