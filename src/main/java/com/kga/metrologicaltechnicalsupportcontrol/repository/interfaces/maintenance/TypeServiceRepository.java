package com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.maintenance;

import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.MaintenanceOperations;
import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.TypeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeServiceRepository extends JpaRepository<TypeService, Long> {
    TypeService findTypeServiceByDesignation(String designation);
}
