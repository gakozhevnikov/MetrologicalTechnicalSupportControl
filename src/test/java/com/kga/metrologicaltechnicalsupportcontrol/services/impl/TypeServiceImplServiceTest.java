package com.kga.metrologicaltechnicalsupportcontrol.services.impl;

import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.MaintenanceOperations;
import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.TypeService;
import com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.maintenance.MaintenanceOperationsRepository;
import com.kga.metrologicaltechnicalsupportcontrol.services.impl.maintenance.TypeServiceImplService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class TypeServiceImplServiceTest {
    private final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    TypeServiceImplService typeServiceImplService;
    @Autowired
    MaintenanceOperationsRepository maintOperRepository;

    String typeServiceDesignationFirst = "Test Type Service Designation First";
    String typeServiceDesignationSecond = "Test Type Service Designation Second";
    List<String> typServiceFirstSecond = List.of (typeServiceDesignationFirst, typeServiceDesignationSecond);
    TypeService typeServiceFirst = new TypeService();
    TypeService typeServiceSecond = new TypeService();
    List<TypeService> typeServiceListAfterSave = new ArrayList<>();

    @BeforeEach
    void setUp() {
        log.info("Class {}, setUp", getClass().getName());
        typeServiceImplService.deleteAll();
        typeServiceFirst.setDesignation(typeServiceDesignationFirst);
        typeServiceSecond.setDesignation(typeServiceDesignationSecond);
        typeServiceListAfterSave = typeServiceImplService.saveAllAndFlush(List.of (typeServiceFirst, typeServiceSecond));
        assertThat(null!=typeServiceListAfterSave, is(true));
    }

    @AfterEach
    void tearDown() {
        log.info("Class {}, tearDown", getClass().getName());
        typeServiceImplService.deleteAll();
    }

    @Test
    void shouldSaveAndFindAll(){
        log.info("Class {}, shouldSave", getClass().getName());
        TypeService typeServiceFirstAfterSave = typeServiceImplService.save(typeServiceFirst);
        TypeService typeServiceSecondAfterSave = typeServiceImplService.save(typeServiceSecond);
        log.info("Class {}, shouldSave, typeServiceFirstAfterSave: {}, typeServiceSecondAfterSave: {}", getClass().getName(), typeServiceFirstAfterSave, typeServiceSecondAfterSave);
        assertThat(null!=typeServiceFirstAfterSave, is(true));
        assertThat(null!=typeServiceSecondAfterSave, is(true));
        List<String> typeServiceList = typeServiceImplService.findAll().stream().map(TypeService::getDesignation).collect(Collectors.toList());
        log.info("Class {}, shouldSave, typeServiceList: {}", getClass().getName(), typeServiceList);
        assertThat(typeServiceList.containsAll(typServiceFirstSecond), is(true));
    }

    @Test
    void shouldSaveAllAndFlushAndFindAll(){
        log.info("Class {}, shouldSaveAllAndFlush", getClass().getName());
        List<String> typeServiceStringList = typeServiceImplService.findAll().stream().map(TypeService::getDesignation).collect(Collectors.toList());
        log.info("Class {}, shouldSaveAllAndFlush, typeServiceList: {}", getClass().getName(), typeServiceStringList);
        assertThat(typeServiceStringList.containsAll(typServiceFirstSecond), is(true));
    }

    @Test
    void shouldDeleteAllAndFindAllEmpty(){
        log.info("Class {}, shouldDeleteAll", getClass().getName());
        typeServiceImplService.deleteAll();
        List<TypeService> typeServiceListAfterFindAll = typeServiceImplService.findAll();
        log.info("Class {}, shouldDeleteAll, typeServiceListAfterFindAll: {}", getClass().getName(), typeServiceListAfterFindAll);
        assertThat(typeServiceListAfterFindAll.isEmpty(), is(true));
    }

    @Test
    void shouldDeleteByIdAndFindAll(){
        log.info("Class {}, shouldDeleteById", getClass().getName());
        Long id = typeServiceListAfterSave.get(0).getId();
        String designation = typeServiceListAfterSave.get(0).getDesignation();
        log.info("Class {}, shouldDeleteById, id: {}, designation: {}", getClass().getName(), id, designation);
        typeServiceImplService.deleteById(id);
        List<Long> typeServiceLongList = typeServiceImplService.findAll().stream().map(TypeService::getId).collect(Collectors.toList());
        List<String> typeServiceStringList = typeServiceImplService.findAll().stream().map(TypeService::getDesignation).collect(Collectors.toList());
        log.info("Class {}, shouldDeleteById, typeServiceLongList: {}, typeServiceStringList: {}", getClass().getName(), typeServiceLongList, typeServiceStringList);
        assertThat(!typeServiceLongList.contains(id), is(true));
        assertThat(!typeServiceStringList.contains(designation), is(true));
    }

    @Test
    void shouldFindById(){
        log.info("Class {}, shouldFindByIdAndFindAll", getClass().getName());
        Long id = typeServiceListAfterSave.get(0).getId();
        String designation = typeServiceListAfterSave.get(0).getDesignation();
        log.info("Class {}, shouldFindByIdAndFindAll, id: {}, designation: {}", getClass().getName(), id, designation);
        TypeService typeServiceAfterGetById = typeServiceImplService.findById(id);
        log.info("Class {}, shouldFindByIdAndFindAll, typeServiceAfterGetById: {}", getClass().getName(), typeServiceAfterGetById);
        assertThat(typeServiceAfterGetById.getId().equals(id), is(true));
        assertThat(typeServiceAfterGetById.getDesignation().equals(designation), is(true));
    }

    @Test
    void shouldFindByDesignation(){
        log.info("Class {}, shouldFindByDesignation", getClass().getName());
        String designation = typeServiceListAfterSave.get(0).getDesignation();
        log.info("Class {}, shouldFindByDesignation, designation: {}", getClass().getName(), designation);
        TypeService typeServiceAfterFindById = typeServiceImplService.findByDesignation(designation);
        log.info("Class {}, shouldFindByDesignation, typeServiceAfterGetById: {}", getClass().getName(), typeServiceAfterFindById);
        assertThat(typeServiceAfterFindById.getDesignation().equals(designation), is(true));
    }



}
