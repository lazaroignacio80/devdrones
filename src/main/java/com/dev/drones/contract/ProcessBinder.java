package com.dev.drones.contract;

import com.dev.drones.contract.to.ProcessTO;
import com.dev.drones.model.Process;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProcessBinder {

    ProcessBinder PROCESS_BINDER = Mappers.getMapper(ProcessBinder.class);

    @Mapping(target = "id", ignore = true)
    Process bind(ProcessTO source);

    ProcessTO bind(Process source);

}
