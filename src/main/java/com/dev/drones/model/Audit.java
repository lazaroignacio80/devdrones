package com.dev.drones.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "audits")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Drone drone;

    @Column(name = "dateTime")
    private Date dateTime;

    @Column(name = "batteryLevel")
    private double batteryLevel;

    public Audit(){

    }

    public Audit(Drone drone, Date dateTime, double batteryLevel) {
        this.drone = drone;
        this.dateTime = dateTime;
        this.batteryLevel = batteryLevel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
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
        Audit audit = (Audit) o;
        return id == audit.id && Double.compare(audit.batteryLevel, batteryLevel) == 0 && drone.equals(audit.drone) && dateTime.equals(audit.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, drone, dateTime, batteryLevel);
    }
}
