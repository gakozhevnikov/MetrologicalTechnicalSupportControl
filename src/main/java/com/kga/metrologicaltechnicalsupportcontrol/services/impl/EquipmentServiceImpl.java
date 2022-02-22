package com.kga.metrologicaltechnicalsupportcontrol.services.impl;

import com.kga.metrologicaltechnicalsupportcontrol.model.Equipment;
import com.kga.metrologicaltechnicalsupportcontrol.model.documents.TypeApproval;
import com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.EquipmentRepository;
import com.kga.metrologicaltechnicalsupportcontrol.services.interfaces.SecondTypeParameter;
import com.kga.metrologicaltechnicalsupportcontrol.util.WorkPlanFileToDataBase;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class EquipmentServiceImpl implements SecondTypeParameter<Equipment, Long, TypeApproval> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final WorkPlanFileToDataBase workPlanFileToDataBase;
    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentServiceImpl(EquipmentRepository equipmentRepository, WorkPlanFileToDataBase workPlanFileToDataBase) {
        this.workPlanFileToDataBase = workPlanFileToDataBase;
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public Equipment save(Equipment entity) {
        log.info("Class {}, method save, value entity.getTitle: {}",getClass().getName(), entity.getTitle());
        return equipmentRepository.saveAndFlush(entity);
    }

    @Override
    public List<Equipment> saveAllAndFlush(List<Equipment> equipments) {
        log.info("Class {}, method saveAllAndFlush, value equipments: {}",getClass().getName(), equipments);
        return equipmentRepository.saveAllAndFlush(equipments);
    }

    public List<Equipment> saveAllAndFlushFromFile() {
        List<Equipment> equipmentList = new ArrayList<>(this.workPlanFileToDataBase.getEquipments());
        log.info("Class {}, method saveAllAndFlushFromFile, value equipments from file: {}",getClass().getName(), equipmentList);
        return equipmentRepository.saveAllAndFlush(equipmentList);
    }

    @Override
    public void deleteAll() {
        log.info("Class {}, method deleteAll",getClass().getName());
        equipmentRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        log.info("Class {}, method deleteById, value Id of equipments: {}",getClass().getName(), id);
        equipmentRepository.deleteById(id);
    }

    @Override
    public List<Equipment> findAll() {
        log.info("Class {}, method findAll",getClass().getName());
        return equipmentRepository.findAll();
    }

    @Override
    public Equipment findById(Long id) {//используй findById, getById вызывает ошибку
        log.info("Class {}, method findById, value Id of equipments: {}",getClass().getName(), id);
        Optional<Equipment> optionalEquipment = equipmentRepository.findById(id);//Do not use .getById, need .findById
        return optionalEquipment.orElse(null);//refactor with new throw
    }

    @Override
    public Equipment findByTitle(String title){
        log.info("Class {}, method findByTitle, value Id of equipments: {}",getClass().getName(), title);
        return equipmentRepository.findEquipmentByTitle(title);
    }

    @Override
    public Equipment findByObject(TypeApproval typeApproval) {//need testing
        log.info("Class {}, method findByObject, TypeApproval: {}",getClass().getName(), typeApproval);
        return equipmentRepository.findEquipmentByTypeApproval(typeApproval);
    }

}
