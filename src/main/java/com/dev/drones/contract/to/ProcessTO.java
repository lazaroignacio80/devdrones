package com.dev.drones.contract.to;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class ProcessTO implements Serializable {

    private long id;

    private DroneTO drone;

    private Set<MedicationTO> medications;

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

    public Set<MedicationTO> getMedications() {
        return medications;
    }

    public void setMedications(Set<MedicationTO> medications) {
        this.medications = medications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessTO processTO = (ProcessTO) o;
        return id == processTO.id && drone.equals(processTO.drone) && medications.equals(processTO.medications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, drone, medications);
    }
}
