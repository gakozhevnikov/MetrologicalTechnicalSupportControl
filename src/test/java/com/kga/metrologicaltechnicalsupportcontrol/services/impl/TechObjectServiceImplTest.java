package com.kga.metrologicaltechnicalsupportcontrol.services.impl;

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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class TechObjectServiceImplTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${work-plan-file.count-tech-object}")
    private Integer countTechObjectTest;

    @Autowired
    TechObjectServiceImpl techObjectService;

    @BeforeEach
    void setUp() {
        log.info("Class {}, method setUp, Objects.requireNonNull(FileManager.getWorkPlanFile()).exists(): {}" +
                        "techObjectService.getWorkPlanFileToDataBase().getTechObjects().isEmpty(): {}",
                getClass().getName(), Objects.requireNonNull(FileManager.getWorkPlanFile()).exists(),
                !techObjectService.getWorkPlanFileToDataBase().getTechObjectsSet().isEmpty());
        if(Objects.requireNonNull(FileManager.getWorkPlanFile()).exists() && techObjectService.getWorkPlanFileToDataBase().getTechObjectsSet().isEmpty()){
            log.info("Class {}, method setUp, in statement if result true", getClass().getName());
            techObjectService.getWorkPlanFileToDataBase().setTechObjectsFromFile();
        }else {log.info("Class {}, method setUp, in statement if result false", getClass().getName());}
        techObjectService.deleteAll();
    }

    @Test
    void checkingTheClassFields(){
        setUp();
        log.info("Class {}, method checkingTheClassFields, techObjectService.getTechObjectRepository().getClass(): {}"
                ,getClass().getName(), techObjectService.getTechObjectRepository().toString().contains("SimpleJpaRepository"));
        assertThat(techObjectService.getTechObjectRepository().toString().contains("SimpleJpaRepository"), is(true));
        log.info("Class {}, method checkingTheClassFields, Objects.equals(techObjectService.getWorkPlanFileToDataBase().getCountTechObject(), countTechObjectTest): {}"
                ,getClass().getName(), Objects.equals(techObjectService.getWorkPlanFileToDataBase().getCountTechObject(), countTechObjectTest));
        assertThat(Objects.equals(techObjectService.getWorkPlanFileToDataBase().getCountTechObject(), countTechObjectTest), is(true));
        tearDown();
    }

    @Test
    void createTechObject() {
        setUp();
        String testTitle = "Test";
        TechObject techObject = new TechObject();
        techObject.setTitle(testTitle);
        techObjectService.save(techObject);
        log.info("Class {}, method createTechObject, techObjectService.getTechObjectRepository().findAll(): {}"
                ,getClass().getName(), techObjectService.getTechObjectRepository().findAll());
        TechObject getTechObject =techObjectService.getTechObjectRepository().findAll().get(0);
        assertThat(getTechObject.getId()>0, is(true));
        assertThat(Objects.equals(getTechObject.getTitle(), testTitle), is(true));
        tearDown();
    }

    @Test
    void saveAllAndFlushFromFile() {
        setUp();
        List<TechObject> techObjectsListFromWorkPlanFileToDataBase = techObjectService.saveAllAndFlushFromFile();
        List<TechObject> getTechObjectFindAll =techObjectService.getTechObjectRepository().findAll();
        log.info("Class {}, method saveAllAndFlushFromFile, " +
                        "techObjectsListFromWorkPlanFileToDataBase.size(): {}, "+
                        "getTechObjectFindAll.size(): {}",
                getClass().getName(), techObjectsListFromWorkPlanFileToDataBase.size(), getTechObjectFindAll.size());
        assertThat(techObjectsListFromWorkPlanFileToDataBase, hasSize(getTechObjectFindAll.size()));
        tearDown();
    }

    @Test
    void saveAllAndFlush(){
        setUp();
        List<TechObject> testListTechObjectFromFile = new ArrayList<>(techObjectService.getWorkPlanFileToDataBase().getTechObjects());
        List<TechObject> testListTechObject =techObjectService.saveAllAndFlush(testListTechObjectFromFile);
        log.info("Class {}, method saveAllAndFlush, " +
                        "testListTechObjectFromFile: {}, "+
                        "testListTechObject: {}",
                getClass().getName(), testListTechObjectFromFile, testListTechObject);
        assertThat(testListTechObjectFromFile, hasSize(testListTechObject.size()));
        tearDown();
    }

    @Test
    void findAll(){
        setUp();
        List<TechObject> testListTechObject =techObjectService.saveAllAndFlush(
                new ArrayList<>(techObjectService.getWorkPlanFileToDataBase().getTechObjects()));
        List<TechObject> findAllList = techObjectService.findAll();
        log.info("Class {}, method findAll, " +
                        "testListTechObject: {}, "+
                        "findAllList: {}",
                getClass().getName(), testListTechObject, findAllList);
        assertThat(testListTechObject, hasSize(findAllList.size()));
        tearDown();
    }

    @Test
    void getById(){
        setUp();
        List<TechObject> testListTechObject =techObjectService.saveAllAndFlush(
                new ArrayList<>(techObjectService.getWorkPlanFileToDataBase().getTechObjects()));
        List<TechObject> findAllList = techObjectService.findAll();
        TechObject techObject = testListTechObject.get(0);
        Long id = techObject.getId();
        TechObject techObjectGetId =techObjectService.getById(techObject.getId());
        log.info("Class {}, method getById, " +
                        "techObjectGetId: {}, " +
                        "techObject: {}, "+
                        "findAllList: {}"+
                        "id: {}",
                getClass().getName(), techObjectGetId, techObject , findAllList, id);
        assertThat(Objects.equals(techObject.getId(),techObjectGetId.getId()), is(true));
        assertThat(Objects.equals(techObject.getTitle(),techObjectGetId.getTitle()), is(true));
        tearDown();
    }

    @Test
    void deleteById(){
        setUp();
        List<TechObject> testListTechObject =techObjectService.saveAllAndFlush(
                new ArrayList<>(techObjectService.getWorkPlanFileToDataBase().getTechObjects()));
        List<TechObject> findAllList = techObjectService.findAll();
        TechObject techObject = testListTechObject.get(0);
        Long id = techObject.getId();
        techObjectService.deleteById(id);
        List<TechObject> findAllListAfterDelete = techObjectService.findAll();

        log.info("Class {}, method getById, " +
                        "id: {}, " +
                        "findAllList: {}, "+
                        "findAllListAfterDelete: {}",
                getClass().getName(), id, findAllList , findAllListAfterDelete);
        assertThat(findAllListAfterDelete, hasSize(findAllList.size()-1));
        assertThat(findAllListAfterDelete, not(hasItem(techObject)));
        tearDown();
    }

    @Test
    void deleteAll(){
        List<TechObject> testListTechObject =techObjectService.saveAllAndFlush(
                new ArrayList<>(techObjectService.getWorkPlanFileToDataBase().getTechObjects()));
        List<TechObject> findAllList = techObjectService.findAll();
        assertThat(findAllList, hasSize(testListTechObject.size()));
        techObjectService.deleteAll();
        List<TechObject> findAllListAfterDeleteAll = techObjectService.findAll();
        assertThat(findAllListAfterDeleteAll, hasSize(0));
    }

    @AfterEach
    void tearDown() {
        log.info("Class {}, method setUp, techObjectService.getTechObjectRepository().deleteAll(): ", getClass().getName());
        techObjectService.deleteAll();
    }
}