package com.dev.drones.controller;

import com.dev.drones.contract.to.MedicationTO;
import com.dev.drones.model.Drone;
import com.dev.drones.model.Medication;
import com.dev.drones.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dev.drones.contract.DroneBinder.DRONE_BINDER;
import static com.dev.drones.contract.MedicationBinder.MEDICATION_BINDER;

@RestController
@RequestMapping("/api")
public class MedicationController {
    @Autowired
    MedicationRepository medicationRepository;

    @GetMapping("/medications")
    public ResponseEntity<List<MedicationTO>> getAllMedications() {
        try {
            List<MedicationTO> medications = new ArrayList<>();
            medicationRepository.findAll()
                    .stream()
                    .map(medication ->  MEDICATION_BINDER.bind(medication))
                    .forEach(medications::add);
            if (medications.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(medications, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/medications/{id}")
    public ResponseEntity<MedicationTO> getMedicationById(@PathVariable("id") long id) {
        Optional<Medication> medicationData = medicationRepository.findById(id);
        if (medicationData.isPresent()) {
            return new ResponseEntity<>(MEDICATION_BINDER.bind(medicationData.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/medications")
    public ResponseEntity<MedicationTO> createMedication(@Valid @RequestBody MedicationTO medicationTO) {
        try {
            Medication _medication = medicationRepository
                    .save(MEDICATION_BINDER.bind(medicationTO));
            return new ResponseEntity<>(MEDICATION_BINDER.bind(_medication), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/medications/{id}")
    public ResponseEntity<MedicationTO> updateMedication(@PathVariable("id") long id, @Valid @RequestBody MedicationTO medicationTO) {
        Optional<Medication> medicationData = medicationRepository.findById(id);
        if (medicationData.isPresent()) {
            return new ResponseEntity<>(MEDICATION_BINDER.bind(medicationRepository.save(MEDICATION_BINDER.bind(medicationTO))), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/medications/{id}")
    public ResponseEntity<HttpStatus> deleteMedication(@PathVariable("id") long id) {
        try {
            medicationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/medications")
    public ResponseEntity<HttpStatus> deleteAllMedications() {
        try {
            medicationRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
