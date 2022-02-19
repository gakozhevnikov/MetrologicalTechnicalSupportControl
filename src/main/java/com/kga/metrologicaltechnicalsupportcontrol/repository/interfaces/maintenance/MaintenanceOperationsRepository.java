package com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.maintenance;

import com.kga.metrologicaltechnicalsupportcontrol.model.Equipment;
import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.MaintenanceOperations;
import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.PurposeOperations;
import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.TypeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceOperationsRepository extends JpaRepository<MaintenanceOperations, Long> {
    MaintenanceOperations findByOperation(String operation);
    List<MaintenanceOperations> findBySequenceOrderByEquipment(Integer sequence);
    List<MaintenanceOperations> findByOperationAndSequenceOrderByEquipment(String operation, Integer sequence);
    List<MaintenanceOperations> findByTypeServiceOrderByEquipment(TypeService typeService);
    List<MaintenanceOperations> findByPurposeOperationsOrderByEquipment(PurposeOperations purposeOperations);
    List<MaintenanceOperations> findByEquipmentOrderByEquipment(Equipment equipment);
    List<MaintenanceOperations> findByEquipmentAndTypeServiceOrderByEquipment(Equipment equipment, TypeService typeService);
    List<MaintenanceOperations> findByEquipmentAndPurposeOperationsOrderByEquipment(Equipment equipment, PurposeOperations purposeOperations);
    List<MaintenanceOperations> findByEquipmentAndTypeServiceAndPurposeOperationsOrderByEquipment(Equipment equipment, TypeService typeService,PurposeOperations purposeOperations);
}
