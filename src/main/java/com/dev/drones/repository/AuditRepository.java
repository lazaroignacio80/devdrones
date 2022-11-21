package com.dev.drones.repository;

import com.dev.drones.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository  extends JpaRepository<Audit, Long> {
}
