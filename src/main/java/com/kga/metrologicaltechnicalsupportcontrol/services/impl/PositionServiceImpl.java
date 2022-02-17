package com.kga.metrologicaltechnicalsupportcontrol.services.impl;


import com.kga.metrologicaltechnicalsupportcontrol.model.Position;
import com.kga.metrologicaltechnicalsupportcontrol.model.TechObject;
import com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.PositionRepository;
import com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.TechObjectRepository;
import com.kga.metrologicaltechnicalsupportcontrol.services.interfaces.SecondTypeParameterReturnSetFindByObject;
import com.kga.metrologicaltechnicalsupportcontrol.util.WorkPlanFileToDataBase;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Getter
@Setter
@Data
public class PositionServiceImpl implements SecondTypeParameterReturnSetFindByObject<Position, Long, TechObject> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final WorkPlanFileToDataBase workPlanFileToDataBase;
    private final PositionRepository positionRepository;
    private final TechObjectRepository techObjectRepository;

    @Autowired
    public PositionServiceImpl(WorkPlanFileToDataBase workPlanFileToDataBase, PositionRepository positionRepository, TechObjectRepository techObjectRepository) {
        this.workPlanFileToDataBase = workPlanFileToDataBase;
        this.positionRepository = positionRepository;
        this.techObjectRepository = techObjectRepository;
    }

    @Override
    public Position save(Position position) {
        log.info("Class {}, method save, value position.getTitle: {}",getClass().getName(), position.getTitle());
        return positionRepository.save(position);
    }

    @Override
    public List<Position> saveAllAndFlush(List<Position> positions) {
        log.info("Class {}, method saveAllAndFlush, value position.getTitle: {}",getClass().getName(), positions);
        return positionRepository.saveAllAndFlush(positions);
    }

    public List<Position> saveAllAndFlushFromFile() {
        Map<String, Set<String>> mapObjectPosition =workPlanFileToDataBase.getMapObjectPosition();
        List<Position> positionList = new ArrayList<>();
        /*List<Object> multiFieldsList =
                multiFieldMap.entrySet()
                        .stream()
                        .flatMap(e -> e.getValue()
                                .stream()
                                .map(o -> queryService.query(e.getKey(), queryService.property("id").eq(o))))
                        .collect(Collectors.toList());*/
        /*List<Position>  positionList = mapObjectPosition.entrySet()
                .stream()
                .flatMap(position -> position.getValue().stream()
                        .map(valuePosition-> new Position().setTitle(valuePosition)))*/

        mapObjectPosition.forEach((key, value) -> {
            TechObject techObject = techObjectRepository.findTechObjectByTitle(key);
            for (String title : value) {
                Position position = new Position();
                position.setTitle(title);
                position.setTechObject(techObject);
                positionList.add(position);
            }
        });
        log.info("Class {}, method saveAllAndFlushFromFile, value position.getTitle: {}",getClass().getName(), positionList);
        return positionRepository.saveAllAndFlush(positionList);
    }

    @Override
    public void deleteAll() {
        log.info("Class {}, method deleteAll",getClass().getName());
        positionRepository.deleteAllInBatch();
    }

    @Override
    public void deleteById(Long id) {
        log.info("Class {}, method deleteById",getClass().getName());
        positionRepository.deleteById(id);
    }

    @Override
    public List<Position> findAll() {
        log.info("Class {}, method findAll",getClass().getName());
        return positionRepository.findAll();
    }

    @Override
    public Position getById(Long id) {
        log.info("Class {}, method getById, value id: {}",getClass().getName(), id);
        Optional<Position> positionOptional = positionRepository.findById(id);
        return positionOptional.orElse(null);
    }

    @Override
    public Position findByTitle(String title) {
        log.info("Class {}, method findByTitle, value title {}",getClass().getName(), title);
        return positionRepository.findPositionByTitle(title);
    }

    @Override
    public Set<Position> findByObject(TechObject techObject) {
        log.info("Class {}, method findByObject, value TechObject {}",getClass().getName(), techObject);
        return positionRepository.findPositionsByTechObject(techObject);
    }
}
