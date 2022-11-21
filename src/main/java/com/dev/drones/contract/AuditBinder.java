package com.dev.drones.contract;

import com.dev.drones.contract.to.AuditTO;
import com.dev.drones.model.Audit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuditBinder {

    AuditBinder AUDIT_BINDER = Mappers.getMapper(AuditBinder.class);

    Audit bind(AuditTO source);
    AuditTO bind(Audit source);
}
