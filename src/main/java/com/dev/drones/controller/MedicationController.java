package com.dev.drones.controller;

import com.dev.drones.model.Medication;
import com.dev.drones.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MedicationController {
    @Autowired
    MedicationRepository medicationRepository;

    @GetMapping("/medications")
    public ResponseEntity<List<Medication>> getAllMedications() {
        try {
            List<Medication> medications = new ArrayList<>();
            medicationRepository.findAll().forEach(medications::add);
            if (medications.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(medications, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/medications/{id}")
    public ResponseEntity<Medication> getMedicationById(@PathVariable("id") long id) {
        Optional<Medication> medicationData = medicationRepository.findById(id);
        if (medicationData.isPresent()) {
            return new ResponseEntity<>(medicationData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/medications")
    public ResponseEntity<Medication> createMedication(@RequestBody Medication medication) {
        try {
            Medication _medication = medicationRepository
                    .save(new Medication( medication.getName(),
                            medication.getWeight(),
                            medication.getCode(),
                            medication.getImage()));
            return new ResponseEntity<>(_medication, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/medications/{id}")
    public ResponseEntity<Medication> updateMedication(@PathVariable("id") long id, @RequestBody Medication medication) {
        Optional<Medication> medicationData = medicationRepository.findById(id);
        if (medicationData.isPresent()) {
            Medication _medication = medicationData.get();
            _medication.setName(_medication.getName());
            _medication.setWeight(_medication.getWeight());
            _medication.setCode(_medication.getCode());
            _medication.setImage(_medication.getImage());
            return new ResponseEntity<>(medicationRepository.save(_medication), HttpStatus.OK);
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
