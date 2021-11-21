package com.kga.metrologicaltechnicalsupportcontrol.services.impl;

import com.kga.metrologicaltechnicalsupportcontrol.model.TechObject;
import com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.TechObjectRepository;
import com.kga.metrologicaltechnicalsupportcontrol.services.interfaces.BaseModelService;
import com.kga.metrologicaltechnicalsupportcontrol.util.WorkPlanFileToDataBase;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
@Data
public class TechObjectServiceImpl implements BaseModelService<TechObject, Long> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final WorkPlanFileToDataBase workPlanFileToDataBase;
    private final TechObjectRepository techObjectRepository;

    @Autowired
    public TechObjectServiceImpl(TechObjectRepository techObjectRepository, WorkPlanFileToDataBase workPlanFileToDataBase) {
        this.workPlanFileToDataBase = workPlanFileToDataBase;
        this.techObjectRepository = techObjectRepository;
    }

    @Override
    public TechObject save(TechObject entity) {
        log.info("Class {}, method add, value entity.getTitle: {}",getClass().getName(), entity.getTitle());
        return techObjectRepository.saveAndFlush(entity);
    }

    @Override
    public List<TechObject> saveAllAndFlush(List<TechObject> techObjects) {
        log.info("Class {}, method add, values techObjects: {}",getClass().getName(), techObjects);
        return techObjectRepository.saveAllAndFlush(techObjects);
    }


    public List<TechObject> saveAllAndFlushFromFile() {
        List<TechObject> techObjectsList = new ArrayList<>(workPlanFileToDataBase.getTechObjects());
        log.info("Class {}, method createTechObject, value List<TechObject> techObjectsList: {}",getClass().getName(), techObjectsList);
        return techObjectRepository.saveAllAndFlush(techObjectsList);
    }

    @Override
    public void deleteAll() {
        log.info("Class {}, method deleteAll, ", getClass().getName());
        techObjectRepository.deleteAll();
    }


    @Override
    public void deleteById(Long id) {
        log.info("Class {}, method delete, ", getClass().getName());
        techObjectRepository.deleteById(id);
    }

    @Override
    public List<TechObject> findAll() {
        log.info("Class {}, method findAll, ", getClass().getName());
        return techObjectRepository.findAll();
    }

    @Override
    public TechObject getById(Long id) {
        log.info("Class {}, method getById, ", getClass().getName());
        Optional<TechObject> optionalTechObject = techObjectRepository.findById(id);
        return optionalTechObject.orElse(null);
    }
}
