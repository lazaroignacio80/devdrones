package com.dev.drones.tasks;

import com.dev.drones.contract.to.AuditTO;
import com.dev.drones.contract.to.DroneTO;
import com.dev.drones.repository.AuditRepository;
import com.dev.drones.repository.DroneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.dev.drones.contract.AuditBinder.AUDIT_BINDER;
import static com.dev.drones.contract.DroneBinder.DRONE_BINDER;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    AuditRepository auditRepository;

    @Scheduled(cron = "0 */2 * ? * *")
    public void scheduleTaskDroneBatteryLevels() {
        logger.info("Cron Task Drone Battery Levels:: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        List<DroneTO> drones = new ArrayList<>();
        droneRepository.findAll()
                .stream()
                .map(drone ->  DRONE_BINDER.bind(drone))
                .forEach(droneTO -> {
                    AuditTO auditTO = new AuditTO();
                    auditTO.setDrone(droneTO);
                    auditTO.setDateTime(new Date());
                    auditTO.setBatteryLevel(droneTO.getBatteryCapacity());
                    auditRepository.save(AUDIT_BINDER.bind(auditTO));
                });
    }
}
