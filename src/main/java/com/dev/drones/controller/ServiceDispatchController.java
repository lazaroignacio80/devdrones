package com.dev.drones.controller;

import com.dev.drones.contract.to.DroneTO;
import com.dev.drones.contract.to.MedicationTO;
import com.dev.drones.contract.to.ProcessTO;
import com.dev.drones.model.Drone;
import com.dev.drones.model.Medication;
import com.dev.drones.model.Process;
import com.dev.drones.model.constant.ConstantEntity;
import com.dev.drones.model.type.State;
import com.dev.drones.repository.DroneRepository;
import com.dev.drones.repository.MedicationRepository;
import com.dev.drones.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.dev.drones.contract.DroneBinder.DRONE_BINDER;
import static com.dev.drones.contract.MedicationBinder.MEDICATION_BINDER;
import static com.dev.drones.contract.ProcessBinder.PROCESS_BINDER;

@RestController
@RequestMapping("/api/v1")
public class ServiceDispatchController {

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    ProcessRepository processRepository;

    // Requirement: register a drone

    @PostMapping("/services-dispatch/register-drone")
    public ResponseEntity<DroneTO> registerDrone(@Valid @RequestBody DroneTO droneTO) {
        try {
            droneTO.setState(State.IDLE);
            Drone _drone = droneRepository
                    .save(DRONE_BINDER.bind(droneTO));
            return new ResponseEntity<>(DRONE_BINDER.bind(_drone), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Requirement: load a drone with medicines

    @PostMapping("/services-dispatch/load-drone-medicines")
    public ResponseEntity<String> loadDroneMedicines(@Valid @RequestBody ProcessTO processTO) {
        try {
            double[] temp = new double[1];
            Optional<Drone> droneData = droneRepository.findById(processTO.getDrone().getId());
            DroneTO droneTOTemp = null;
            if (droneData.isPresent()) {
                droneTOTemp = DRONE_BINDER.bind(droneData.get());
                processTO.setDrone(droneTOTemp);
            }
            List<MedicationTO> medicationsList = new ArrayList<>();
            if (processTO.getMedications() != null && !processTO.getMedications().isEmpty()){
                processTO.getMedications().stream().map( medicationTO -> {
                    Optional<Medication> medicationData = medicationRepository.findById(medicationTO.getId());
                    if (medicationData.isPresent()) {
                        temp[0] += medicationData.get().getWeight();
                        return MEDICATION_BINDER.bind(medicationData.get());
                    }
                    return null;
                }).forEach(medicationsList::add);
            }
            processTO.setMedications(new HashSet<>(medicationsList));

            if ( !processTO.getDrone().getState().equals(State.IDLE) ){
                return new ResponseEntity<>(
                        "The drone must be in an idle state to be loaded",
                        HttpStatus.BAD_REQUEST);
            }

            if ( temp[0] > processTO.getDrone().getWeightLimit()){
                return new ResponseEntity<>(
                        "The drone cannot be loaded with more weight than it has as a defined limit",
                        HttpStatus.BAD_REQUEST);
            }

            if ( processTO.getDrone().getBatteryCapacity() < ConstantEntity.LEVEL_BATTERY_MIN){
                return new ResponseEntity<>(
                        "The drone cannot be in loaded status if the battery level is **lower than 25%**",
                        HttpStatus.BAD_REQUEST);
            }

            Process _process = processRepository
                    .save(PROCESS_BINDER.bind(processTO));

            // Change state to loaded
            droneTOTemp.setState(State.LOADED);

            Drone _drone = droneRepository
                    .save(DRONE_BINDER.bind(droneTOTemp));

            return new ResponseEntity<>("The drone has been successfully charged", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Requirement: check available drones for loaded

    @GetMapping("/services-dispatch/availability-drone-for-loaded")
    public ResponseEntity<List<DroneTO>> getAvailabilityDronesForLoaded() {
        try {
            List<DroneTO> drones = new ArrayList<>();
            droneRepository.findByState(State.IDLE)
                    .stream()
                    .map(drone ->  DRONE_BINDER.bind(drone))
                    .forEach(drones::add);
            if (drones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(drones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Requirement: check drone battery level for a given drone

    @GetMapping("/services-dispatch/level-battery-drone/{id}")
    public ResponseEntity<Double> getLevelBatteryDroneById(@PathVariable("id") long id) {
        Optional<Drone> droneData = droneRepository.findById(id);
        if (droneData.isPresent()) {
            return new ResponseEntity<>(DRONE_BINDER.bind(droneData.get()).getBatteryCapacity(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Requirement: check loaded medication items for a given drone

    @GetMapping("/services-dispatch/check-loaded-medication-drone/{id}")
    public ResponseEntity<ProcessTO> getLoadedMedicationDroneById(@PathVariable("id") long id) {
        List<ProcessTO> processList = new ArrayList<>();
        processRepository.findProcessByDroneId(id, State.LOADED)
                .stream()
                .map(process ->  PROCESS_BINDER.bind(process))
                .forEach(processList::add);
        if (processList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(processList.stream().findFirst().get(), HttpStatus.OK);
    }

}
