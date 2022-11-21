package com.dev.drones.controller;

import com.dev.drones.contract.to.MedicationTO;
import com.dev.drones.contract.to.ProcessTO;
import com.dev.drones.model.Drone;
import com.dev.drones.model.Medication;
import com.dev.drones.model.Process;
import com.dev.drones.repository.DroneRepository;
import com.dev.drones.repository.MedicationRepository;
import com.dev.drones.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static com.dev.drones.contract.DroneBinder.DRONE_BINDER;
import static com.dev.drones.contract.MedicationBinder.MEDICATION_BINDER;
import static com.dev.drones.contract.ProcessBinder.PROCESS_BINDER;

@RestController
@RequestMapping("/api")
public class ProcessController {

    @Autowired
    ProcessRepository processRepository;

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    DroneRepository droneRepository;

    @GetMapping("/process")
    public ResponseEntity<List<ProcessTO>> getAllProcess() {
        try {
            List<ProcessTO> processList = new ArrayList<>();
            processRepository.findAll()
                    .stream()
                    .map(process ->  PROCESS_BINDER.bind(process))
                    .forEach(processList::add);
            if (processList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(processList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/process/{id}")
    public ResponseEntity<ProcessTO> getProcessById(@PathVariable("id") long id) {
        Optional<Process> processData = processRepository.findById(id);
        if (processData.isPresent()) {
            return new ResponseEntity<>(PROCESS_BINDER.bind(processData.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/process")
    public ResponseEntity<ProcessTO> createProcess(@Valid @RequestBody ProcessTO processTO) {
        try {
            Optional<Drone> droneData = droneRepository.findById(processTO.getDrone().getId());
            if (droneData.isPresent()) {
                processTO.setDrone(DRONE_BINDER.bind(droneData.get()));
            }
            List<MedicationTO> medicationsList = new ArrayList<>();
            if (processTO.getMedications() != null && !processTO.getMedications().isEmpty()){
                processTO.getMedications().stream().map( medicationTO -> {
                    Optional<Medication> medicationData = medicationRepository.findById(medicationTO.getId());
                    if (medicationData.isPresent()) {
                        return MEDICATION_BINDER.bind(medicationData.get());
                    }
                    return null;
                }).forEach(medicationsList::add);
            }
            processTO.setMedications(new HashSet<>(medicationsList));
            Process _process = processRepository
                    .save(PROCESS_BINDER.bind(processTO));
            return new ResponseEntity<>(PROCESS_BINDER.bind(_process), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/process/{id}")
    public ResponseEntity<ProcessTO> updateDrone(@PathVariable("id") long id, @Valid @RequestBody ProcessTO processTO) {
        Optional<Process> processData = processRepository.findById(id);
        if (processData.isPresent()) {
            Optional<Drone> droneData = droneRepository.findById(processTO.getDrone().getId());
            if (droneData.isPresent()) {
                processTO.setDrone(DRONE_BINDER.bind(droneData.get()));
            }
            List<MedicationTO> medicationsList = new ArrayList<>();
            if (processTO.getMedications() != null && !processTO.getMedications().isEmpty()){
                processTO.getMedications().stream().map( medicationTO -> {
                    Optional<Medication> medicationData = medicationRepository.findById(medicationTO.getId());
                    if (medicationData.isPresent()) {
                        return MEDICATION_BINDER.bind(medicationData.get());
                    }
                    return null;
                }).forEach(medicationsList::add);
            }
            processTO.setMedications(new HashSet<>(medicationsList));
            return new ResponseEntity<>(PROCESS_BINDER.bind(processRepository.save(PROCESS_BINDER.bind(processTO))), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/process/{id}")
    public ResponseEntity<HttpStatus> deleteDrone(@PathVariable("id") long id) {
        try {
            processRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/process")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            processRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
