package com.kga.metrologicaltechnicalsupportcontrol.services.interfaces;

import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.MaintenanceOperations;

import java.util.List;

public interface FindByMaintenanceOperation<T,ID> extends BaseModelService<T, ID>{
    List<T> findByMaintenanceOperation(MaintenanceOperations maintenanceOperations);
}
