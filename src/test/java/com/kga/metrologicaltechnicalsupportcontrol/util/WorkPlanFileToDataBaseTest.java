package com.kga.metrologicaltechnicalsupportcontrol.util;

import com.kga.metrologicaltechnicalsupportcontrol.model.TechObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class WorkPlanFileToDataBaseTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    WorkPlanFileToDataBase workPlanFileToDataBase;

    @Value("${work-plan-file.count-tech-object}")
    private Integer countTechObjectTest;

    @Value("${work-plan-file.count-title-equipment}")
    private Integer countTitleEquipmentTest;

    @Value("#{${work-plan-file.start-row}-1}")
    private Integer startRowTest;

    @Value("${work-plan-file.end-row}")
    private Integer endRowTest;

    @Value("#{${work-plan-file.sheet}-1}")
    private Integer sheet;

    @Test
    void countTitleEquipmentTestValueIsEquals(){
        log.info("workPlanFileToDataBase.getCountTitleEquipment: {} and countTitleEquipmentTest: {}", workPlanFileToDataBase.getCountTitleEquipment(),countTitleEquipmentTest);
        assertThat(workPlanFileToDataBase.getCountTitleEquipment(), is(countTitleEquipmentTest));
    }

    @Test
    void columnTitleEquipmentShouldZero(){
        log.info("workPlanFileToDataBase.getColumnTitleEquipment: {}", workPlanFileToDataBase.getColumnTitleEquipment());
        assertThat(workPlanFileToDataBase.getColumnTitleEquipment(), is(0));
    }

    @Test
    void countTechObjectValueIsEquals(){
        log.info("workPlanFileToDataBase.getCountTechObject: {} and countTechObjectTest: {}", workPlanFileToDataBase.getCountTechObject(), countTechObjectTest);
        assertThat(workPlanFileToDataBase.getCountTechObject(), is(countTechObjectTest));
    }

    @Test
    void startRowValueIsEquals(){
        log.info("workPlanFileToDataBase.getStartRow: {} and startRowTest: {}", workPlanFileToDataBase.getStartRow(),startRowTest);
        assertThat(workPlanFileToDataBase.getStartRow(), is(startRowTest));
    }

    @Test
    void endRowValueIsEquals(){
        log.info("workPlanFileToDataBase.getEndRow: {} and endRowTest: {}", workPlanFileToDataBase.getEndRow(),endRowTest);
        assertThat(workPlanFileToDataBase.getEndRow(), is(endRowTest));
    }

    @Test
    void numberSheetIsEquals(){
        log.info("workPlanFileToDataBase.getSheet: {} and endRowTest: {}", workPlanFileToDataBase.getSheet(),sheet);
        assertThat(workPlanFileToDataBase.getSheet(), is(sheet));
    }

    @Test
    void getTechObjectTest(){
        String[] arrayOfTechObject = {"СИКГ", "СИКН", "УЗПВ", "УЗТГ"};
        Set<TechObject> techObjectSetFromWorkPlanFileToDataBase = workPlanFileToDataBase.getTechObjects();
        Set<TechObject> techObjectSet = new TreeSet<>();
        Arrays.stream(arrayOfTechObject).forEach(title ->{
            TechObject techObject = new TechObject();
            techObject.setTitle(title);
                    techObjectSet.add(techObject);
        });
        log.info("Set of techObjectTest: {}, set of techObject from workPlanFileToDataBase: {}",  techObjectSet, techObjectSetFromWorkPlanFileToDataBase);
        assertThat(techObjectSetFromWorkPlanFileToDataBase, is(techObjectSet));
}





}