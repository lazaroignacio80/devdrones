package com.dev.drones.contract;

import com.dev.drones.contract.to.DroneTO;
import com.dev.drones.contract.to.MedicationTO;
import com.dev.drones.model.Drone;
import com.dev.drones.model.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MedicationBinder {

    MedicationBinder MEDICATION_BINDER = Mappers.getMapper(MedicationBinder.class);

    Medication bind(MedicationTO source);
    MedicationTO bind(Medication source);

}
