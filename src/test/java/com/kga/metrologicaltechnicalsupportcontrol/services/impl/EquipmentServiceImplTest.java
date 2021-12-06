package com.kga.metrologicaltechnicalsupportcontrol.services.impl;

import com.kga.metrologicaltechnicalsupportcontrol.model.Equipment;
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
import static org.hamcrest.Matchers.hasItem;

@SpringBootTest
public class EquipmentServiceImplTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${work-plan-file.count-title-equipment}")
    private Integer countEquipmentTest;


    @Autowired
    EquipmentServiceImpl equipmentService;

    @BeforeEach
    void setUp() {
        log.info("Class {}, method setUp, Objects.requireNonNull(FileManager.getWorkPlanFile()).exists(): {}" +
                        "equipmentService.getWorkPlanFileToDataBase().getEquipments().isEmpty(): {}",
                getClass().getName(), Objects.requireNonNull(FileManager.getWorkPlanFile()).exists(),
                !equipmentService.getWorkPlanFileToDataBase().getEquipments().isEmpty());
        if(Objects.requireNonNull(FileManager.getWorkPlanFile()).exists() && equipmentService.getWorkPlanFileToDataBase().getEquipmentSet().isEmpty()){
            log.info("Class {}, method setUp, in statement if result true", getClass().getName());
            equipmentService.getWorkPlanFileToDataBase().setEquipmentsFromFile();
        }else {log.info("Class {}, method setUp, in statement if result false", getClass().getName());}
        equipmentService.deleteAll();
    }

    @Test
    void checkingTheClassFields(){
        setUp();
        log.info("Class {}, method checkingTheClassFields, equipmentService.getEquipmentRepository().toString(): {}, equipmentService.getTechObjectRepository().getClass(): {}"
                ,getClass().getName(), equipmentService.getEquipmentRepository().toString(),equipmentService.getEquipmentRepository().toString().contains("SimpleJpaRepository"));
        assertThat(equipmentService.getEquipmentRepository().toString().contains("SimpleJpaRepository"), is(true));
        log.info("Class {}, method checkingTheClassFields, Objects.equals(equipmentService.getWorkPlanFileToDataBase().getCountTitleEquipment(), countEquipmentTest): {}"
                ,getClass().getName(), Objects.equals(equipmentService.getWorkPlanFileToDataBase().getCountTitleEquipment(), countEquipmentTest));
        assertThat(Objects.equals(equipmentService.getWorkPlanFileToDataBase().getCountTitleEquipment(), countEquipmentTest), is(true));
        tearDown();
    }

    @Test
    void shouldCreateEquipmentAndSaveDB() {
        setUp();
        String testTitle = "Test";
        Equipment equipment = new Equipment();
        equipment.setTitle(testTitle);
        equipmentService.save(equipment);
        log.info("Class {}, method shouldCreateEquipmentAndSaveDB, equipmentService.getEquipmentRepository().findAll(): {}"
                ,getClass().getName(), equipmentService.getEquipmentRepository().findAll());
        Equipment getEquipment =equipmentService.getEquipmentRepository().findAll().get(0);
        assertThat(getEquipment.getId()>0, is(true));
        assertThat(Objects.equals(getEquipment.getTitle(), testTitle), is(true));
        tearDown();
    }

    @Test
    void shouldSaveAllAndFlushFromFile() {
        setUp();
        List<Equipment> equipmentListFromWorkPlanFileToDataBase = equipmentService.saveAllAndFlushFromFile();
        List<Equipment> getEquipmentFindAll =equipmentService.getEquipmentRepository().findAll();
        log.info("Class {}, method shouldSaveAllAndFlushFromFile, " +
                        "equipmentListFromWorkPlanFileToDataBase.size(): {}, "+
                        "getEquipmentFindAll.size(): {}",
                getClass().getName(), equipmentListFromWorkPlanFileToDataBase.size(), getEquipmentFindAll.size());
        assertThat(equipmentListFromWorkPlanFileToDataBase, hasSize(getEquipmentFindAll.size()));
        tearDown();
    }

    @Test
    void saveAllAndFlush(){
        setUp();
        List<Equipment> testListEquipmentFromFile = new ArrayList<>(equipmentService.getWorkPlanFileToDataBase().getEquipments());
        List<Equipment> testListEquipment =equipmentService.saveAllAndFlush(testListEquipmentFromFile);
        log.info("Class {}, method saveAllAndFlush, " +
                        "testListEquipmentFromFile: {}, "+
                        "testListEquipment: {}",
                getClass().getName(), testListEquipmentFromFile, testListEquipment);
        assertThat(testListEquipmentFromFile, hasSize(testListEquipment.size()));
        tearDown();
    }

    @Test
    void findAll(){
        setUp();
        List<Equipment> testListEquipment =equipmentService.saveAllAndFlush(
                new ArrayList<>(equipmentService.getWorkPlanFileToDataBase().getEquipments()));
        List<Equipment> findAllList = equipmentService.findAll();
        log.info("Class {}, method findAll, " +
                        "testListTechObject: {}, "+
                        "findAllList: {}",
                getClass().getName(), testListEquipment, findAllList);
        assertThat(testListEquipment, hasSize(findAllList.size()));
        tearDown();
    }

    @Test
    void getById(){
        setUp();
        List<Equipment> testListEquipment =equipmentService.saveAllAndFlush(
                new ArrayList<>(equipmentService.getWorkPlanFileToDataBase().getEquipments()));
        log.info("Class {}, method getById, " +
                        "init testListEquipment: {}",
                getClass().getName(), testListEquipment);
        List<Equipment> findAllList = equipmentService.findAll();
        log.info("Class {}, method getById, " +
                        "init findAllList: {}",
                getClass().getName(), findAllList);
        Equipment equipment = testListEquipment.get(0);
        Long id = equipment.getId();
        Equipment equipmentGetId = equipmentService.getById(id);
        log.info("Class {}, method getById, " +
                        "equipmentGetId: {}, " +
                        "equipment: {}, "+
                        "findAllList: {}"+
                        "idTest: {}",
                getClass().getName(), equipmentGetId, equipment , findAllList, id);
        log.info("Class {}, method getById, " +
                        "equipment.getId(): {}, " +
                        "equipmentGetId.getId(): {}",
                getClass().getName(), equipment.getId(), equipmentGetId.getId());
        assertThat(Objects.equals(equipment.getId(),equipmentGetId.getId()), is(true));
        log.info("Class {}, method getById, " +
                        "equipment.getTitle(): {}, " +
                        "equipmentGetId.getTitle(): {}",
                getClass().getName(), equipment.getTitle(), equipmentGetId.getTitle());
        assertThat(Objects.equals(equipment.getTitle(),equipmentGetId.getTitle()), is(true));
        tearDown();
    }

    @Test
    void deleteById(){
        setUp();
        List<Equipment> testListTechObject =equipmentService.saveAllAndFlush(
                new ArrayList<>(equipmentService.getWorkPlanFileToDataBase().getEquipments()));
        List<Equipment> findAllList = equipmentService.findAll();
        Equipment equipment = testListTechObject.get(0);
        Long id = equipment.getId();
        equipmentService.deleteById(id);
        List<Equipment> findAllListAfterDelete = equipmentService.findAll();

        log.info("Class {}, method deleteById, " +
                        "id: {}, " +
                        "findAllList: {}, "+
                        "findAllListAfterDelete: {}",
                getClass().getName(), id, findAllList , findAllListAfterDelete);
        assertThat(findAllListAfterDelete, hasSize(findAllList.size()-1));
        assertThat(findAllListAfterDelete, not(hasItem(equipment)));
        tearDown();
    }

    @Test
    void deleteAll(){
        List<Equipment> testListTechObject =equipmentService.saveAllAndFlush(
                new ArrayList<>(equipmentService.getWorkPlanFileToDataBase().getEquipments()));
        List<Equipment> findAllList = equipmentService.findAll();
        assertThat(findAllList, hasSize(testListTechObject.size()));
        equipmentService.deleteAll();
        List<Equipment> findAllListAfterDeleteAll = equipmentService.findAll();
        assertThat(findAllListAfterDeleteAll, hasSize(0));
    }

    @AfterEach
    void tearDown(){
        log.info("Class {}, method tearDown(): ", getClass().getName());
        equipmentService.deleteAll();
    }


}
