package com.kga.metrologicaltechnicalsupportcontrol.services.impl.maintenance;

import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.MaintenanceOperations;
import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.TypeService;
import com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.maintenance.TypeServiceRepository;
import com.kga.metrologicaltechnicalsupportcontrol.services.interfaces.FindByDesignation;
import com.kga.metrologicaltechnicalsupportcontrol.services.interfaces.FindByDesignationReturnList;
import com.kga.metrologicaltechnicalsupportcontrol.services.interfaces.FindByMaintenanceOperation;
import com.kga.metrologicaltechnicalsupportcontrol.util.WorkPlanFileToDataBase;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
@Data
public class TypeServiceImpl implements FindByDesignation<TypeService, Long>, FindByMaintenanceOperation<TypeService, Long> {

    private final TypeServiceRepository typeServiceRepository;
    private final WorkPlanFileToDataBase workPlanFileToDataBase;

    @Autowired
    public TypeServiceImpl(TypeServiceRepository typeServiceRepository, WorkPlanFileToDataBase workPlanFileToDataBase) {
        this.typeServiceRepository = typeServiceRepository;
        this.workPlanFileToDataBase = workPlanFileToDataBase;
    }

    @Override
    public TypeService save(TypeService entity) {
        return typeServiceRepository.saveAndFlush(entity);
    }

    @Override
    public List<TypeService> saveAllAndFlush(List<TypeService> typeServices) {
        return typeServiceRepository.saveAllAndFlush(typeServices);
    }

    @Override
    public void deleteAll() {
        typeServiceRepository.deleteAllInBatch();
    }

    @Override
    public void deleteById(Long id) {
        typeServiceRepository.deleteById(id);
    }

    @Override
    public List<TypeService> findAll() {
        return typeServiceRepository.findAll();
    }

    @Override
    public TypeService getById(Long id) {
        return typeServiceRepository.getById(id);
    }

    @Override
    public TypeService findByDesignation(String designation) {
        return typeServiceRepository.findTypeServiceByDesignation(designation);
    }

    @Override
    public List<TypeService> findByMaintenanceOperation(MaintenanceOperations maintenanceOperations) {
        return typeServiceRepository.findTypeServiceByMaintenanceOperations(maintenanceOperations);
    }
}
