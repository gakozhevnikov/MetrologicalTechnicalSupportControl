package com.kga.metrologicaltechnicalsupportcontrol.util;

import com.kga.metrologicaltechnicalsupportcontrol.exceptions.WorkPlanFileToDataBaseException;
import com.kga.metrologicaltechnicalsupportcontrol.model.Equipment;
import com.kga.metrologicaltechnicalsupportcontrol.model.TechObject;
import com.kga.metrologicaltechnicalsupportcontrol.model.WorkPlan;
import com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.TypeServiceRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Month;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class WorkPlanFileToDataBaseTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    WorkPlanFileToDataBase workPlanFileToDataBase;

    @Autowired
    TypeServiceRepository typeServiceRepository;

    @Value("${work-plan-file.count-tech-object}")
    private Integer countTechObjectTest;

    @Value("${work-plan-file.count-title-equipment}")
    private Integer countTitleEquipmentTest;

    @Value("#{${work-plan-file.start-row}-1}")
    private Integer startRowTest;

    @Value("${work-plan-file.end-row}")
    private Integer endRowTest;

    @Value("#{${work-plan-file.sheet}-1}")
    private Integer sheetTest;

    @Value("${work-plan-file.message.error.count-tech-object}")
    private String errorCountTechObjectTest;

    @Value("${work-plan-file.message.error.count-equipment}")
    private String errorCountEquipmentTest;

    @Value("${work-plan-file.message.error.sheet.null}")
    private String errorSheetNullTest;



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
    void columnTextObjectShouldZero(){
        log.info("workPlanFileToDataBase.getColumnTitleEquipment: {}", workPlanFileToDataBase.getColumnTextObject());
        assertThat(workPlanFileToDataBase.getColumnTextObject(), is(0));
    }
    @Test
    void columnTitleObjectShouldOne(){
        log.info("workPlanFileToDataBase.getColumnTitleEquipment: {}", workPlanFileToDataBase.getColumnTitleObject());
        assertThat(workPlanFileToDataBase.getColumnTitleObject(), is(1));
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
        log.info("workPlanFileToDataBase.getSheet: {} and sheetTest: {}", workPlanFileToDataBase.getSheet(), sheetTest);
        assertThat(workPlanFileToDataBase.getSheet(), is(sheetTest));
    }

    @Test
    void errorCountTechObjectShouldEqualValueInFileProperties(){
        log.info("workPlanFileToDataBase.errorCountTechObjectShouldEqualValueInFileProperties: {} and errorCountTechObjectTest: {}", workPlanFileToDataBase.getErrorCountTechObject(), errorCountTechObjectTest);
        assertThat(workPlanFileToDataBase.getErrorCountTechObject(), is(errorCountTechObjectTest));
    }

    @Test
    void errorCountEquipmentShouldEqualValueInFileProperties(){
        log.info("workPlanFileToDataBase.errorCountEquipmentShouldEqualValueInFileProperties: {} and errorCountEquipmentTest: {}",
                workPlanFileToDataBase.getErrorCountEquipment(), errorCountEquipmentTest);
        assertThat(workPlanFileToDataBase.getErrorCountEquipment(), is(errorCountEquipmentTest));
    }

    @Test
    void errorSheetNullTestShouldEqualValueInFileProperties(){
        log.info("workPlanFileToDataBase.errorSheetNullTestShouldEqualValueInFileProperties: {} and errorSheetNullTest: {}",
                workPlanFileToDataBase.getErrorSheetNull(), errorSheetNullTest);
        assertThat(workPlanFileToDataBase.getErrorSheetNull(), is(errorSheetNullTest));
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

    @Test
    void getTechObjectWithErrorCountTechObject() {
        workPlanFileToDataBase.setCountTechObject(3);
        log.info("Class {}, getTechObjectWithErrorCountTechObject,count Tech Objects after correct: {}", getClass().getName(), workPlanFileToDataBase.getCountTechObject());
        Exception exception = assertThrows(WorkPlanFileToDataBaseException.class, () -> workPlanFileToDataBase.getTechObjects());
        log.info("Class {}, getTechObjectWithErrorCountTechObject, exception message: {}", getClass().getName(), exception.getMessage());
        log.info("Class {}, getTechObjectWithErrorCountTechObject, workPlanFileToDataBase.getErrorList(): {}", getClass().getName(), workPlanFileToDataBase.getErrorList());
        workPlanFileToDataBase.setCountTechObject(countTechObjectTest);
        log.info("Class {}, getTechObjectWithErrorCountTechObject, count Tech Objects after set origin: {}", getClass().getName(), workPlanFileToDataBase.getCountTechObject());
    }

    @Test
    void getEquipmentsCountsShouldEqualCountTitleEquipmentTest(){
        Set<Equipment> equipmentSetFromWorkPlanFileToDataBase = workPlanFileToDataBase.getEquipments();
        log.info("Count of equipments in properties: {}, size of equipmentSetFromWorkPlanFileToDataBase: {}, set of equipments from workPlanFileToDataBase: {}", countTitleEquipmentTest,equipmentSetFromWorkPlanFileToDataBase.size(), equipmentSetFromWorkPlanFileToDataBase);
        assertThat(equipmentSetFromWorkPlanFileToDataBase.size(), is(countTitleEquipmentTest));
    }

    @Test
    void getEquipmentsWithErrorCountEquipmentsShouldBeWorkPlanFileToDataBaseException(){
        workPlanFileToDataBase.setCountTitleEquipment(countTitleEquipmentTest-1);
        log.info("Class {}, getEquipmentsWithErrorCountEquipmentsShouldBeWorkPlanFileToDataBaseException,count equipments after correct: {}", getClass().getName(), workPlanFileToDataBase.getCountTitleEquipment());
        Exception exception = assertThrows(WorkPlanFileToDataBaseException.class, () -> workPlanFileToDataBase.getEquipments());
        log.info("Class {}, getEquipmentsWithErrorCountEquipmentsShouldBeWorkPlanFileToDataBaseException, exception message: {}", getClass().getName(), exception.getMessage());
        log.info("Class {}, getEquipmentsWithErrorCountEquipmentsShouldBeWorkPlanFileToDataBaseException, workPlanFileToDataBase.getErrorList(): {}", getClass().getName(), workPlanFileToDataBase.getErrorList());
        workPlanFileToDataBase.setCountTechObject(countTitleEquipmentTest);
        log.info("Class {}, getEquipmentsWithErrorCountEquipmentsShouldBeWorkPlanFileToDataBaseException, count equipments after set origin: {}", getClass().getName(), workPlanFileToDataBase.getCountTitleEquipment());
    }

    @Test
    void shouldMapContainsPositions(){
        workPlanFileToDataBase.setMapObjectPositionFromFile();
        Map<String, Set<String>> mapObjectPosition = workPlanFileToDataBase.getMapObjectPosition();
        log.info("Class {}, shouldMapContainsPositions, mapObjectPosition: {}", getClass().getName(), mapObjectPosition);
    }

    @Test
    void shouldReturnWorkPlanFromMethodFactoryWorkPlan(){
        typeServiceRepository.deleteAllInBatch();
        WorkPlan workPlan = new WorkPlan();
        String stringCellFirstValue = "Test type service";
        String stringCellSecondValue = "Test date of work";
        workPlan = workPlanFileToDataBase.factoryWorkPlan(Month.JANUARY, stringCellFirstValue, stringCellSecondValue, workPlan);
        log.info("Class {}, shouldReturnWorkPlanFromMethodFactoryWorkPlan, workPlan: {}", getClass().getName(), workPlan);
        assertThat(workPlan.getMonth().equals(Month.JANUARY), is(true));
        assertThat(workPlan.getTypeService().getDesignation().equals(stringCellFirstValue), is(true));
        assertThat(workPlan.getDateOfWork().equals(stringCellSecondValue), is(true));
        typeServiceRepository.deleteAllInBatch();
    }

    @Test
    void shouldGetNotEmptyWorkPlanList(){
        List<WorkPlan> workPlanSet = workPlanFileToDataBase.getWorkPlans();
        log.info("Class {}, shouldGetNotEmptyWorkPlanSet, workPlanSet: {}", getClass().getName(), workPlanSet);
        assertThat(workPlanSet.isEmpty(), is(false));
    }
}