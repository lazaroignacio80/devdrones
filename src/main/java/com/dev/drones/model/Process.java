package com.dev.drones.model;

import com.dev.drones.model.base.EntityBase;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "process")
public class Process extends EntityBase {

    @ManyToOne
    private Drone drone;

    @ManyToMany
    private Set<Medication> medications;

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
