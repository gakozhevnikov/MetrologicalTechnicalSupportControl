package com.kga.metrologicaltechnicalsupportcontrol.services.impl;

import com.kga.metrologicaltechnicalsupportcontrol.model.Position;
import com.kga.metrologicaltechnicalsupportcontrol.model.TechObject;
import com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.TechObjectRepository;
import com.kga.metrologicaltechnicalsupportcontrol.services.interfaces.SecondTypeParameter;
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
public class TechObjectServiceImpl implements SecondTypeParameter<TechObject, Long, Position> {
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
        log.info("Class {}, method add, save entity.getTitle: {}",getClass().getName(), entity.getTitle());
        return techObjectRepository.saveAndFlush(entity);
    }

    @Override
    public List<TechObject> saveAllAndFlush(List<TechObject> techObjects) {
        log.info("Class {}, method saveAllAndFlush, values techObjects: {}",getClass().getName(), techObjects);
        return techObjectRepository.saveAllAndFlush(techObjects);
    }


    public List<TechObject> saveAllAndFlushFromFile() {
        List<TechObject> techObjectsList = new ArrayList<>(workPlanFileToDataBase.getTechObjects());
        log.info("Class {}, method saveAllAndFlushFromFile, value techObjectsList: {}",getClass().getName(), techObjectsList);
        return techObjectRepository.saveAllAndFlush(techObjectsList);
    }

    @Override
    public void deleteAll() {
        log.info("Class {}, method deleteAll, ", getClass().getName());
        techObjectRepository.deleteAll();
    }


    @Override
    public void deleteById(Long id) {
        log.info("Class {}, method deleteById, ", getClass().getName());
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

    @Override
    public TechObject findByTitle(String title) {
        log.info("Class {}, method findByTitle, value title: {}",getClass().getName(), title);
        return techObjectRepository.findTechObjectByTitle(title);
    }

    @Override
    public TechObject findByObject(Position position) {//need testing
        log.info("Class {}, method findByObject, value Position: {}",getClass().getName(), position);
        return techObjectRepository.findTechObjectByPositionsContains(position);
    }

}
