package com.kga.metrologicaltechnicalsupportcontrol.services.impl;

import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.PurposeOperations;
import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.TypeService;
import com.kga.metrologicaltechnicalsupportcontrol.services.impl.maintenance.PurposeOperationsService;
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
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class PurposeOperationsServiceTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    PurposeOperationsService purpOperService;

    String purpOperDesignationFirst = "PurposeOperationsDesignationFirst";
    String purpOperDesignationSecond = "PurposeOperationsDesignationSecond";
    List<String> purpOperDesignationsList = List.of(purpOperDesignationFirst,purpOperDesignationSecond);
    PurposeOperations purpOperFirst= new PurposeOperations();
    PurposeOperations purpOperSecond= new PurposeOperations();
    List<PurposeOperations> purpOperList = new ArrayList<>();
    List<PurposeOperations> purpOperListAfterSave = new ArrayList<>();

    @BeforeEach
    void setUp() {
        log.info("Class {}, setUp", getClass().getName());
        purpOperFirst.setDesignation(purpOperDesignationFirst);
        purpOperSecond.setDesignation(purpOperDesignationSecond);
        purpOperList = List.of(purpOperFirst, purpOperSecond);
        purpOperService.deleteAll();
        List<PurposeOperations> purpOperListCheckAfterDelEmpty = purpOperService.findAll();
        log.info("Class {}, setUp, purpOperListCheckAfterDelEmpty: {}", getClass().getName(), purpOperListCheckAfterDelEmpty);
        assertThat(purpOperListCheckAfterDelEmpty.isEmpty(), is(true));
        purpOperListAfterSave = purpOperService.saveAllAndFlush(purpOperList);
        assertThat(!purpOperListAfterSave.isEmpty(), is(true));
    }

    @AfterEach
    void tearDown() {
        log.info("Class {}, tearDown", getClass().getName());
        purpOperService.deleteAll();
    }

    @Test
    void shouldSaveAndFindAll(){
        log.info("Class {}, shouldSaveAndFindAll", getClass().getName());
        purpOperService.deleteAll();
        List<PurposeOperations> purpOperListCheckAfterDelEmpty = purpOperService.findAll();
        assertThat(purpOperListCheckAfterDelEmpty.isEmpty(), is(true));
        PurposeOperations purposeOperationsFirstAfterSave = purpOperService.save(purpOperFirst);
        PurposeOperations purposeOperationsSecondAfterSave = purpOperService.save(purpOperSecond);
        log.info("Class {}, shouldSaveAndFindAll, purposeOperationsFirstAfterSave: {}, purposeOperationsSecondAfterSave: {}",
                getClass().getName(), purposeOperationsFirstAfterSave, purposeOperationsSecondAfterSave);
        assertThat(null!=purposeOperationsFirstAfterSave, is(true));
        assertThat(null!=purposeOperationsSecondAfterSave, is(true));
        List<String> purpOperServiceList = purpOperService.findAll().stream().map(PurposeOperations::getDesignation).collect(Collectors.toList());
        log.info("Class {}, shouldSaveAndFindAll, purpOperServiceList: {}", getClass().getName(), purpOperServiceList);
        assertThat(purpOperServiceList.containsAll(purpOperDesignationsList), is(true));
    }

    @Test
    void shouldFindById(){
        log.info("Class {}, shouldFindById", getClass().getName());
        Long id = purpOperListAfterSave.get(0).getId();
        log.info("Class {}, shouldFindById, id: {}", getClass().getName(), id);
        PurposeOperations purposeOperations = purpOperService.findById(id);
        log.info("Class {}, shouldFindById, purposeOperations: {}", getClass().getName(), purposeOperations);
        assertThat(purposeOperations.getDesignation().equals(purpOperDesignationFirst), is(true));
    }

    @Test
    void shouldDeleteById(){
        log.info("Class {}, shouldDeleteById", getClass().getName());
        PurposeOperations purposeOperations = purpOperListAfterSave.get(0);
        Long id = purposeOperations.getId();
        String designation = purposeOperations.getDesignation();
        log.info("Class {}, shouldDeleteById, id: {}", getClass().getName(), id);
        purpOperService.deleteById(id);
        List<String> purpOperServiceList = purpOperService.findAll().stream().map(PurposeOperations::getDesignation).collect(Collectors.toList());
        log.info("Class {}, shouldDeleteById, purpOperServiceList: {}", getClass().getName(), purpOperServiceList);
        assertThat(!purpOperServiceList.contains(designation), is(true));
    }

    @Test
    void shouldFindByDesignation(){
        log.info("Class {}, shouldFindByDesignation", getClass().getName());
        String designationAfterFindFirst = purpOperService.findByDesignation(purpOperDesignationFirst).getDesignation();
        String designationAfterFindSecond = purpOperService.findByDesignation(purpOperDesignationSecond).getDesignation();
        log.info("Class {}, shouldFindByDesignation, designationAfterFindFirst: {}, designationAfterFindSecond: {}"+
                ", purpOperDesignationFirst: {}, purpOperDesignationSecond: {}, "
                , getClass().getName(), designationAfterFindFirst, designationAfterFindSecond
                , purpOperDesignationFirst, purpOperDesignationSecond);
        assertThat(designationAfterFindFirst.equals(purpOperDesignationFirst), is (true));
        assertThat(designationAfterFindSecond.equals(purpOperDesignationSecond), is (true));
    }
}
