package com.dev.drones.repository;

import com.dev.drones.model.Process;
import com.dev.drones.model.type.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcessRepository extends JpaRepository<Process, Long> {

    @Query(value="SELECT p FROM Process p WHERE p.drone.id = :myDroneId and p.drone.state = :myState")
    List<Process> findProcessByDroneId (long myDroneId, State myState);

}
