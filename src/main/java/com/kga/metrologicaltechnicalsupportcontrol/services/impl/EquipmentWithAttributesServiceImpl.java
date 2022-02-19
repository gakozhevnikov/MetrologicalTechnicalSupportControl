package com.kga.metrologicaltechnicalsupportcontrol.services.impl;

import com.kga.metrologicaltechnicalsupportcontrol.model.EquipmentWithAttributes;
import com.kga.metrologicaltechnicalsupportcontrol.model.WorkPlan;
import com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.EquipmentWithAttributesRepository;
import com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.PositionRepository;
import com.kga.metrologicaltechnicalsupportcontrol.services.interfaces.BaseModelService;
import com.kga.metrologicaltechnicalsupportcontrol.util.WorkPlanFileToDataBase;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@Data
public class EquipmentWithAttributesServiceImpl implements BaseModelService<EquipmentWithAttributes, Long> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final WorkPlanFileToDataBase workPlanFileToDataBase;
    private final PositionRepository positionRepository;
    private final EquipmentWithAttributesRepository equWithAttrRepository;

    @Autowired
    public EquipmentWithAttributesServiceImpl(WorkPlanFileToDataBase workPlanFileToDataBase, PositionRepository positionRepository, EquipmentWithAttributesRepository equWithAttrRepository) {
        this.workPlanFileToDataBase = workPlanFileToDataBase;
        this.positionRepository = positionRepository;
        this.equWithAttrRepository = equWithAttrRepository;
    }

    @Override
    public EquipmentWithAttributes save(EquipmentWithAttributes entity) {
        log.info("Class {}, method save, value entity.getEquipment(): {}, entity.getSerialNumber(): {}",getClass().getName(), entity.getEquipment(), entity.getSerialNumber());
        return equWithAttrRepository.saveAndFlush(entity);
    }

    @Override
    public List<EquipmentWithAttributes> saveAllAndFlush(List<EquipmentWithAttributes> equipmentWithAttributes) {
        log.info("Class {}, method saveAllAndFlush, value equipmentWithAttributes: {}",getClass().getName(), equipmentWithAttributes);
        return equWithAttrRepository.saveAllAndFlush(equipmentWithAttributes);
    }

    @Override
    public void deleteAll() {
        log.info("Class {}, method deleteAll",getClass().getName());
        equWithAttrRepository.deleteAllInBatch();
    }

    @Override
    public void deleteById(Long id) {
        log.info("Class {}, method deleteById, value id: {}",getClass().getName(), id);
        equWithAttrRepository.deleteById(id);
    }

    @Override
    public List<EquipmentWithAttributes> findAll() {
        log.info("Class {}, method findAll",getClass().getName());
        return equWithAttrRepository.findAll();
    }

    @Override
    public EquipmentWithAttributes getById(Long id) {
        log.info("Class {}, method getById, value id: {}",getClass().getName(), id);
        Optional<EquipmentWithAttributes> optional = equWithAttrRepository.findById(id);
        return optional.orElse(null);
    }

    public List<EquipmentWithAttributes> findByEquipment_Title(String title) {
        return equWithAttrRepository.findEquipmentWithAttributesByEquipment_Title(title);
    }

    public List<EquipmentWithAttributes> findBySerialNumber(String serialNumber) {
        return equWithAttrRepository.findEquipmentWithAttributesBySerialNumber(serialNumber);
    }

    public List<EquipmentWithAttributes> findByDateVMI(LocalDateTime localDateTime){
        return equWithAttrRepository.findEquipmentWithAttributesByDateVMI(localDateTime);
    }

    public List<EquipmentWithAttributes> findByPositionTitleAndTechObjectTitle(String positionTitle, String techObjectTitle){
        return equWithAttrRepository.findEquipmentWithAttributesByPosition_TitleAndPosition_TechObject_Title(positionTitle, techObjectTitle);
    }

    public List<EquipmentWithAttributes> findByPositionTechObjectTitle(String techObjectTitle){
        return equWithAttrRepository.findEquipmentWithAttributesByPosition_TechObject_Title(techObjectTitle);
    }

    public List<EquipmentWithAttributes> saveAllAndFlushFromFile() {
        log.info("Class {}, method saveAllAndFlushFromFile",getClass().getName());
        Set<EquipmentWithAttributes> ewaSet = workPlanFileToDataBase.getWorkPlans().stream().map(WorkPlan::getEquipmentWithAttributes).collect(Collectors.toSet());
        log.info("Class {}, method saveAllAndFlushFromFile, ewaSet: {}",getClass().getName(), ewaSet);
        return equWithAttrRepository.saveAllAndFlush(new ArrayList<>(ewaSet));
    }
}
