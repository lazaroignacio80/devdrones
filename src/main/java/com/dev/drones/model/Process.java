package com.dev.drones.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "process")
public class Process{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Drone drone;

    @ManyToMany
    private Set<Medication> medications;

    public Process(){

    }

    public Process(Drone drone, Set<Medication> medications) {
        this.drone = drone;
        this.medications = medications;
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

    public Set<Medication> getMedications() {
        return medications;
    }

    public void setMedications(Set<Medication> medications) {
        this.medications = medications;
    }
}
