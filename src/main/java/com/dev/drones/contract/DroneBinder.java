package com.dev.drones.contract;

import com.dev.drones.contract.to.DroneTO;
import com.dev.drones.model.Drone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DroneBinder {

    DroneBinder DRONE_BINDER = Mappers.getMapper(DroneBinder.class);

    Drone bind(DroneTO source);
    DroneTO bind(Drone source);

}
