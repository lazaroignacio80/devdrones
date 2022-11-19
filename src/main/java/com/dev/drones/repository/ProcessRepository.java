package com.dev.drones.repository;

import com.dev.drones.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<Process, Long> {
}
