package com.dev.drones.contract.to;

import com.dev.drones.model.constant.ConstantEntity;
import com.dev.drones.model.type.Model;
import com.dev.drones.model.type.State;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class DroneTO {

    private long id;

    @NotBlank(message = "Serial number is required")
    @Size(max = ConstantEntity.SERIAL_NUMBER_MAX, message
            = "The serial number must have 100 characters maximum")
    private String serialNumber;

    @NotNull(message = "Model is required")
    private Model model;

    @NotNull(message = "Weight limit is required")
    @Max(value = ConstantEntity.WEIGHT_LIMIT_MAX, message
            = "The weight limit must have 500gr maximum")
    private double weightLimit;

    @NotNull(message = "Battery capacity is required")
    private double batteryCapacity;

    private State state;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DroneTO droneTO = (DroneTO) o;
        return id == droneTO.id && Double.compare(droneTO.weightLimit, weightLimit) == 0 && Double.compare(droneTO.batteryCapacity, batteryCapacity) == 0 && serialNumber.equals(droneTO.serialNumber) && model == droneTO.model && state == droneTO.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, model, weightLimit, batteryCapacity, state);
    }
}
