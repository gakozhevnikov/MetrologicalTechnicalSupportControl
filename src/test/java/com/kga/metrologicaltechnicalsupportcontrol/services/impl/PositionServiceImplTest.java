package com.kga.metrologicaltechnicalsupportcontrol.services.impl;

import com.kga.metrologicaltechnicalsupportcontrol.model.Position;
import com.kga.metrologicaltechnicalsupportcontrol.model.TechObject;
import com.kga.metrologicaltechnicalsupportcontrol.util.FileManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class PositionServiceImplTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${work-plan-file.count-tech-object}")
    private Integer countTechObjectTest;

    @Autowired
    TechObjectServiceImpl techObjectServiceTest;

    @Autowired
    PositionServiceImpl positionService;

    private List<TechObject> techObjectList;
    String title="Title Position in test";
    String titleSecond="Second Title Position in test";

    Position positionOne;
    Position positionTwo;
    List<Position> positionsList;


    @BeforeEach
    void setUp() {
        techObjectList = techObjectServiceTest.findAll();
        positionOne = new Position();
        positionOne.setTitle(title);
        positionOne.setTechObject(techObjectList.get(0));
        positionTwo = new Position();
        positionTwo.setTitle(titleSecond);
        positionTwo.setTechObject(techObjectList.get(1));
        positionsList = new ArrayList<>();
        positionsList.add(positionOne);
        positionsList.add(positionTwo);
        log.info("Class {}, method setUp, positionsList: {}", getClass().getName(), positionsList);
        log.info("Class {}, method setUp, techObjectList: {}", getClass().getName(), techObjectList);
        log.info("Class {}, method setUp, Objects.requireNonNull(FileManager.getWorkPlanFile()).exists(): {}" +
                        "positionService.getWorkPlanFileToDataBase().getMapObjectPosition().isEmpty(): {}",
                getClass().getName(), Objects.requireNonNull(FileManager.getWorkPlanFile()).exists(),
                positionService.getWorkPlanFileToDataBase().getMapObjectPosition().isEmpty());
        if(Objects.requireNonNull(FileManager.getWorkPlanFile()).exists() && positionService.getWorkPlanFileToDataBase().getMapObjectPosition().isEmpty()){
            log.info("Class {}, method setUp, in statement if result true", getClass().getName());
            positionService.getWorkPlanFileToDataBase().setMapObjectPositionFromFile();
        }else {log.info("Class {}, method setUp, in statement if result false", getClass().getName());}
        positionService.deleteAll();
    }

    @Test
    void shouldMethodSaveAllAndFlushFromFileInsertPositionsInDataBase() {
        log.info("Class {}, method shouldMethodSaveAllAndFlushFromFileInsertPositionsInDataBase, before positionService.saveAllAndFlushFromFile()", getClass().getName());
        positionService.saveAllAndFlushFromFile();
        log.info("Class {}, method shouldMethodSaveAllAndFlushFromFileInsertPositionsInDataBase, after positionService.saveAllAndFlushFromFile()", getClass().getName());
        List <Position> positionList = positionService.findAll();
        log.info("Class {}, method shouldMethodSaveAllAndFlushFromFileInsertPositionsInDataBase, positionList: {}", getClass().getName(), positionList);
        assertThat(!positionList.isEmpty(), is(true));
    }

    @Test
    void shouldSaveNewPositionAndFindByTitle() {
        Position position = new Position();
        position.setTitle(title);
        position.setTechObject(techObjectList.get(0));
        log.info("Class {}, method shouldSaveNewPosition, position: {}", getClass().getName(), position);
        log.info("Class {}, method shouldSaveNewPosition, techObjectList.get(0): {}", getClass().getName(), techObjectList.get(0));
        Position positionSave= positionService.save(position);
        log.info("Class {}, method shouldSaveNewPosition, positionSave: {}", getClass().getName(), positionSave);
        assertThat(positionSave.getTitle().equals(title), is(true));
        Position positionFromDataBase = positionService.findByTitle(title);
        log.info("Class {}, method shouldSaveNewPosition, positionFromDataBase: {}", getClass().getName(), positionFromDataBase);
        assertThat(positionFromDataBase.getTitle().equals(title), is(true));
    }

    @Test
    void shouldSaveAll() {
        Position position = new Position();
        position.setTitle(title);
        position.setTechObject(techObjectList.get(0));
        Position positionSecond = new Position();
        positionSecond.setTitle(titleSecond);
        positionSecond.setTechObject(techObjectList.get(1));
        log.info("Class {}, method shouldSaveAll, position: {}, positionSecond: {}", getClass().getName(), position,positionSecond);
        log.info("Class {}, method shouldSaveAll, techObjectList.get(0): {}, techObjectList.get(1): {}", getClass().getName(), techObjectList.get(0), techObjectList.get(1));
        List<Position> positionList = new ArrayList<>();
        positionList.add(position);
        positionList.add(positionSecond);
        log.info("Class {}, method shouldSaveAll, positionList: {}", getClass().getName(), positionList);
        List<Position> positionListAfterSaveAll = positionService.saveAllAndFlush(positionList);
        log.info("Class {}, method shouldSaveAll, positionListAfterSaveAll.size(): {}", getClass().getName(), positionListAfterSaveAll.size());
        assertThat(positionListAfterSaveAll, hasSize(2));
        List<String> list = new ArrayList<>();
        for (Position positionFor : positionListAfterSaveAll) {
            list.add(positionFor.getTitle());
        }
        log.info("Class {}, method shouldSaveAll, list: {}", getClass().getName(), list);
        assertThat(list, contains(title, titleSecond));
        List<Position> positionListFromDataBase = positionService.findAll();
        List<String> listFromDataBase = new ArrayList<>();
        for (Position positionFor : positionListAfterSaveAll) {
            listFromDataBase.add(positionFor.getTitle());
        }
        log.info("Class {}, method shouldSaveAll, listFromDataBase: {}", getClass().getName(), listFromDataBase);
        assertThat(listFromDataBase, contains(title, titleSecond));

    }

    @Test
    void shouldDeleteAll(){
        positionService.deleteAll();
        assertThat(positionService.findAll().isEmpty(), is(true));
    }

    @Test
    void shouldDeleteById(){
        Position position = new Position();
        position.setTitle(title);
        position.setTechObject(techObjectList.get(0));
        Position positionSecond = new Position();
        positionSecond.setTitle(titleSecond);
        positionSecond.setTechObject(techObjectList.get(1));
        List<Position> positionList = new ArrayList<>();
        positionList.add(position);
        positionList.add(positionSecond);
        List<Position> list = positionService.saveAllAndFlush(positionList);
        Long idPositionAfterSaveAll = list.get(0).getId();
        log.info("Class {}, method shouldDeleteById, idPositionAfterSaveAll: {}", getClass().getName(), idPositionAfterSaveAll);
        positionService.deleteById(idPositionAfterSaveAll);
        List<Position> positionsListArterDelete = positionService.findAll();
        log.info("Class {}, method shouldDeleteById, idPositionAfterSaveAll: {}", getClass().getName(), positionsListArterDelete);
        List<Long> listId = new ArrayList<>();
        for (Position positionFor : positionsListArterDelete) {
            listId.add(positionFor.getId());
        }
        assertThat(listId, not(contains(idPositionAfterSaveAll)));

    }

    @Test
    void shouldGetById(){//
        List<Position> list = positionService.saveAllAndFlush(positionsList);
        Position positionAfterSaveAllAndFlush = list.get(0);
        String titleAfterGetById = positionAfterSaveAllAndFlush.getTitle();//Если есть два одинаковых titla то будет ошибка т.к. возвращатся два а не один
        Long idPositionAfterSaveAll = positionAfterSaveAllAndFlush.getId();
        log.info("Class {}, method shouldGetById, positionAfterSaveAllAndFlush: {}", getClass().getName(), positionAfterSaveAllAndFlush);
        Position positionAfterGetById = positionService.findById(idPositionAfterSaveAll);
        String titleAfterGEtById = positionAfterGetById.getTitle();
        log.info("Class {}, method shouldGetById, positionAfterGetById: {}", getClass().getName(), positionAfterGetById);
        assertThat(titleAfterGetById.equals(titleAfterGEtById), is(true));
    }

    @Test
    void shouldFindByTitle(){
        log.info("Class {}, method shouldFindByTitle, positionsList: {}", getClass().getName(), positionsList);
        List<Position> positionListMeth = positionService.saveAllAndFlush(positionsList);
        log.info("Class {}, method shouldFindByTitle, positionList: {}", getClass().getName(), positionListMeth);
        String titleFindByTitle = positionService.findByTitle(title).getTitle();
        log.info("Class {}, method shouldFindByTitle, titleFindByTitle: {}", getClass().getName(), titleFindByTitle);
        assertThat(titleFindByTitle.equals(title), is(true));
    }

    @Test
    void shouldFindByObject(){
        log.info("Class {}, method shouldFindByObject, positionsList: {}", getClass().getName(), positionsList);
        List<Position> positionListMeth = positionService.saveAllAndFlush(positionsList);
        log.info("Class {}, method shouldFindByTitle, positionList: {}", getClass().getName(), positionListMeth);
        TechObject techObject = techObjectList.get(0);
        log.info("Class {}, method shouldFindByTitle, techObject: {}", getClass().getName(), techObject);
        String techObjectTitle = techObject.getTitle();
        log.info("Class {}, method shouldFindByTitle, techObjectTitle: {}", getClass().getName(), techObjectTitle);
        Set<Position> positionSetByObject= positionService.findByObject(techObject);
        log.info("Class {}, method shouldFindByTitle, positionSetByObject: {}", getClass().getName(), positionSetByObject);
        String techObjectTitleAfterGetByObject = positionSetByObject.stream().map(Position::getTechObject)
                .collect(Collectors.toList()).get(0).getTitle();
        log.info("Class {}, method shouldFindByTitle, techObjectTitleAfterGetByObject: {}", getClass().getName(), techObjectTitleAfterGetByObject);
        log.info("Class {}, method shouldFindByTitle, before assertThat, techObjectTitle: {}, techObjectTitleAfterGetByObject: {}",
                getClass().getName(), techObjectTitle, techObjectTitleAfterGetByObject);
        assertThat(techObjectTitleAfterGetByObject.equals(techObjectTitle), is(true));
    }

    @AfterEach
    void tearDown() {
        log.info("Class {}, method setUp, techObjectService.deleteAll(): ", getClass().getName());
        positionService.deleteAll();
    }
}
