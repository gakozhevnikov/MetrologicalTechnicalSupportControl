package com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.maintenance;

import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.MaintenanceOperations;
import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.PurposeOperations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurposeOperationsRepository extends JpaRepository<PurposeOperations, Long> {
    PurposeOperations findPurposeOperationsByDesignation(String designation);
    List<PurposeOperations> findPurposeOperationsByMaintenanceOperations (MaintenanceOperations maintenanceOperations);
}
