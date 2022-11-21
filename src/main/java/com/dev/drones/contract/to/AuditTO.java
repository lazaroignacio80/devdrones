package com.dev.drones.contract.to;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class AuditTO implements Serializable {

    private long id;

    private DroneTO drone;

    private Date dateTime;

    private double batteryLevel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DroneTO getDrone() {
        return drone;
    }

    public void setDrone(DroneTO drone) {
        this.drone = drone;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditTO auditTO = (AuditTO) o;
        return id == auditTO.id && Double.compare(auditTO.batteryLevel, batteryLevel) == 0 && drone.equals(auditTO.drone) && dateTime.equals(auditTO.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, drone, dateTime, batteryLevel);
    }
}
