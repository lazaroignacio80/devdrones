package com.dev.drones.repository;

import com.dev.drones.model.Drone;
import com.dev.drones.model.type.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    List<Drone> findByState(State state);
}
