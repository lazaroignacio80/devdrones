package com.dev.drones.controller;

import com.dev.drones.contract.to.DroneTO;
import com.dev.drones.model.Drone;
import com.dev.drones.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dev.drones.contract.DroneBinder.DRONE_BINDER;

@RestController
@RequestMapping("/api")
public class DroneController {
    @Autowired
    DroneRepository droneRepository;

    @GetMapping("/drones")
    public ResponseEntity<List<DroneTO>> getAllDrones() {
        try {
            List<DroneTO> drones = new ArrayList<>();
            droneRepository.findAll()
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

    @GetMapping("/drones/{id}")
    public ResponseEntity<DroneTO> getDroneById(@PathVariable("id") long id) {
        Optional<Drone> droneData = droneRepository.findById(id);
        if (droneData.isPresent()) {
            return new ResponseEntity<>(DRONE_BINDER.bind(droneData.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/drones")
    public ResponseEntity<DroneTO> createDrone(@Valid @RequestBody DroneTO droneTO) {
        try {
            Drone _drone = droneRepository
                    .save(DRONE_BINDER.bind(droneTO));
            return new ResponseEntity<>(DRONE_BINDER.bind(_drone), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/drones/{id}")
    public ResponseEntity<DroneTO> updateDrone(@PathVariable("id") long id, @Valid @RequestBody DroneTO droneTO) {
        Optional<Drone> droneData = droneRepository.findById(id);
        if (droneData.isPresent()) {
            return new ResponseEntity<>(DRONE_BINDER.bind(droneRepository.save(DRONE_BINDER.bind(droneTO))), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/drones/{id}")
    public ResponseEntity<HttpStatus> deleteDrone(@PathVariable("id") long id) {
        try {
            droneRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/drones")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            droneRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
