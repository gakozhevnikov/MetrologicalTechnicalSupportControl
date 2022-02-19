package com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces;

import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.MaintenanceOperations;
import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.TypeService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeServiceRepository extends JpaRepository<TypeService, Long> {
    TypeService findTypeServiceByDesignation(String designation);
    List<TypeService> findTypeServiceByMaintenanceOperations(MaintenanceOperations maintenanceOperations);
}
