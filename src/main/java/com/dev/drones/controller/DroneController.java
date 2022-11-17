package com.dev.drones.controller;

import com.dev.drones.model.Drone;
import com.dev.drones.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DroneController {
    @Autowired
    DroneRepository droneRepository;

    @GetMapping("/drones")
    public ResponseEntity<List<Drone>> getAllDrones() {
        try {
            List<Drone> drones = new ArrayList<>();
            droneRepository.findAll().forEach(drones::add);
            if (drones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(drones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/drones/{id}")
    public ResponseEntity<Drone> getDroneById(@PathVariable("id") long id) {
        Optional<Drone> droneData = droneRepository.findById(id);
        if (droneData.isPresent()) {
            return new ResponseEntity<>(droneData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/drones")
    public ResponseEntity<Drone> createDrone(@RequestBody Drone drone) {
        try {
            Drone _drone = droneRepository
                    .save(new Drone( drone.getSerialNumber(),
                            drone.getModel(),
                            drone.getWeightLimit(),
                            drone.getBatteryCapacity(),
                            drone.getState()));
            return new ResponseEntity<>(_drone, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/drones/{id}")
    public ResponseEntity<Drone> updateDrone(@PathVariable("id") long id, @RequestBody Drone drone) {
        Optional<Drone> droneData = droneRepository.findById(id);
        if (droneData.isPresent()) {
            Drone _drone = droneData.get();
            _drone.setSerialNumber(_drone.getSerialNumber());
            _drone.setModel(_drone.getModel());
            _drone.setWeightLimit(_drone.getWeightLimit());
            _drone.setBatteryCapacity(_drone.getBatteryCapacity());
            _drone.setState(_drone.getState());
            return new ResponseEntity<>(droneRepository.save(_drone), HttpStatus.OK);
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
