package com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces;

import com.kga.metrologicaltechnicalsupportcontrol.Title;
import com.kga.metrologicaltechnicalsupportcontrol.model.Equipment;
import com.kga.metrologicaltechnicalsupportcontrol.model.EquipmentWithAttributes;
import com.kga.metrologicaltechnicalsupportcontrol.model.Position;
import com.kga.metrologicaltechnicalsupportcontrol.model.TechObject;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class EquipmentWithAttributesRepositoryTest {
    private final Logger log = LoggerFactory.getLogger(EquipmentWithAttributesRepositoryTest.class);

    @Autowired
    EquipmentWithAttributesRepository equipmentWithAttributesRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    TechObjectRepository techObjectRepository;

    @Autowired
    PositionRepository positionRepository;

    EquipmentWithAttributes equipmentWithAttributesFirst = new EquipmentWithAttributes();
    EquipmentWithAttributes equipmentWithAttributesSecond = new EquipmentWithAttributes();

    String equipmentTitleFirst = "Equipment Title First";
    String equipmentTitleSecond = "Equipment Title Second";
    String positionTitleFirst = "Position Title First";
    String positionTitleSecond = "Position Title Second";
    String techObjectTitleFirst = "Tech Object Title First";
    String techObjectTitleSecond = "Tech Object Title Second";
    String serialNumberFirst = "Serial number First";
    String serialNumberSecond = "Serial number Second";
    LocalDateTime localDateTimeFirst = LocalDateTime.of(2022,1,1,0, 0);
    LocalDateTime localDateTimeSecond = LocalDateTime.of(2022,2,2,0,0);

    List<EquipmentWithAttributes> equipmentWithAttributesList;



    @BeforeEach
    void setUp() {
        log.info("Class {}", getClass().getName());
        equipmentWithAttributesRepository.deleteAllInBatch();//deleteAll does not work
        List<Equipment> equipmentList = equipmentRepository.findAll();
        log.info("Class {}, equipmentList: {}", getClass().getName(), equipmentList);
        Equipment equipmentFirst;
        Equipment equipmentSecond;
        if (equipmentList.isEmpty()){
            equipmentFirst = new Equipment();
            equipmentSecond = new Equipment();
            equipmentFirst.setTitle(equipmentTitleFirst);
            equipmentSecond.setTitle(equipmentTitleSecond);
            equipmentRepository.save(equipmentFirst);
            equipmentRepository.save(equipmentSecond);
            equipmentList = equipmentRepository.findAll();
        }
        equipmentFirst = equipmentList.get(0);
        equipmentSecond = equipmentList.get(1);
        log.info("Class {}, before positionRepository.findAll(),", getClass().getName());
        List<Position> positionRepositoryList = positionRepository.findAll();
        log.info("Class {}, positionRepositoryList: {}", getClass().getName(), positionRepositoryList);
        Position positionFirst;
        Position positionSecond;
        if(positionRepositoryList.isEmpty()){
            log.info("Class {} before techObjectListRepository.findAll ", getClass().getName());
            List<TechObject> techObjectListRepository = techObjectRepository.findAll();
            log.info("Class {}, techObjectListRepository: {}", getClass().getName(), techObjectListRepository);
            TechObject techObjectFirst;
            TechObject techObjectSecond;
            if (techObjectListRepository.isEmpty()){
                techObjectFirst = new TechObject();
                techObjectSecond = new TechObject();
                techObjectFirst.setTitle(techObjectTitleFirst);
                techObjectSecond.setTitle(techObjectTitleSecond);
                techObjectRepository.save(techObjectFirst);
                techObjectRepository.save(techObjectSecond);
                techObjectListRepository = techObjectRepository.findAll();
            }
            techObjectFirst = techObjectListRepository.get(0);
            techObjectSecond = techObjectListRepository.get(1);
            positionFirst = new Position();
            positionSecond = new Position();
            positionFirst.setTitle(positionTitleFirst);
            positionFirst.setTechObject(techObjectFirst);
            positionSecond.setTitle(positionTitleSecond);
            positionFirst.setTechObject(techObjectSecond);
            positionRepository.save(positionFirst);
            positionRepository.save(positionSecond);
            positionRepositoryList=positionRepository.findAll();
        }
        positionFirst=positionRepositoryList.get(0);
        positionSecond=positionRepositoryList.get(1);

        equipmentWithAttributesFirst.setEquipment(equipmentFirst);
        equipmentWithAttributesFirst.setPosition(positionFirst);
        equipmentWithAttributesFirst.setSerialNumber(serialNumberFirst);
        equipmentWithAttributesFirst.setDateVMI(localDateTimeFirst);

        equipmentWithAttributesSecond.setEquipment(equipmentSecond);
        equipmentWithAttributesSecond.setPosition(positionSecond);
        equipmentWithAttributesSecond.setSerialNumber(serialNumberSecond);
        equipmentWithAttributesSecond.setDateVMI(localDateTimeSecond);
        log.info("Class {}, equipmentWithAttributesRepository.findAll: {}", getClass().getName(), equipmentWithAttributesRepository.findAll());

        equipmentWithAttributesRepository.save(equipmentWithAttributesFirst);
        equipmentWithAttributesRepository.save(equipmentWithAttributesSecond);

        equipmentWithAttributesList = equipmentWithAttributesRepository.findAll();
        log.info("Class {}, equipmentWithAttributesList: {}", getClass().getName(), equipmentWithAttributesList);
    }

    @AfterEach
    void tearDown() {
        equipmentWithAttributesRepository.deleteAllInBatch();//deleteAll does not work
    }

    @Test
    void shouldFindEquipmentWithAttributesBySerialNumber(){
        log.info("Class {}, shouldFindEquipmentWithAttributesBySerialNumber",getClass().getName());
        EquipmentWithAttributes equipmentWithAttributesFirst = equipmentWithAttributesList.get(0);
        EquipmentWithAttributes equipmentWithAttributesSecond = equipmentWithAttributesList.get(1);
        log.info("Class {}, equipmentWithAttributesFirst.getSerialNumber: {}, equipmentWithAttributesSecond.getSerialNumber: {}"
                , getClass().getName(),equipmentWithAttributesFirst.getSerialNumber(), equipmentWithAttributesSecond.getSerialNumber());
        String serialNumberFirst = equipmentWithAttributesFirst.getSerialNumber();
        String serialNumberSecond = equipmentWithAttributesSecond.getSerialNumber();
        log.info("Class {}, serialNumberFirst: {}, serialNumberSecond: {}", getClass().getName(),serialNumberFirst, serialNumberSecond);
        EquipmentWithAttributes withAttributesBySerialFirst = equipmentWithAttributesRepository.findEquipmentWithAttributesBySerialNumber(serialNumberFirst).get(0);
        EquipmentWithAttributes withAttributesBySerialSecond= equipmentWithAttributesRepository.findEquipmentWithAttributesBySerialNumber(serialNumberSecond).get(0);

        assertThat(withAttributesBySerialFirst.getSerialNumber().equals(serialNumberFirst), is(true));
        assertThat(withAttributesBySerialSecond.getSerialNumber().equals(serialNumberSecond), is(true));
        log.info("Class {}, withAttributesBySerialFirst.getSerialNumber: {}, withAttributesBySerialSecond.getSerialNumber: {}"
                ,getClass().getName(),withAttributesBySerialFirst.getSerialNumber(), withAttributesBySerialSecond.getSerialNumber());
    }

    @Test
    void shouldFindEquipmentWithAttributesByEquipment_Title(){
        log.info("Class {},  shouldFindEquipmentWithAttributesByEquipment_Title",getClass().getName());
        EquipmentWithAttributes equipmentWithAttributesFirst = equipmentWithAttributesList.get(0);
        EquipmentWithAttributes equipmentWithAttributesSecond = equipmentWithAttributesList.get(1);
        String serialTitleFirst = equipmentWithAttributesFirst.getEquipment().getTitle();
        String serialTitleSecond = equipmentWithAttributesSecond.getEquipment().getTitle();
        log.info("Class {}, method  shouldFindEquipmentWithAttributesByEquipment_Title, serialTitleFirst: {}, serialTitleSecond: {}"
                , getClass().getName(),serialTitleFirst, serialTitleSecond);
        EquipmentWithAttributes withAttributesBySerialFirst = equipmentWithAttributesRepository.findEquipmentWithAttributesByEquipment_Title(serialTitleFirst).get(0);
        EquipmentWithAttributes withAttributesBySerialSecond= equipmentWithAttributesRepository.findEquipmentWithAttributesByEquipment_Title(serialTitleSecond).get(0);
        assertThat(withAttributesBySerialFirst.getEquipment().getTitle().equals(serialTitleFirst), is(true));
        assertThat(withAttributesBySerialSecond.getEquipment().getTitle().equals(serialTitleSecond), is(true));
        log.info("Class {}, method  shouldFindEquipmentWithAttributesByEquipment_Title, withAttributesBySerialFirst.getEquipment().getTitle(): {}, withAttributesBySerialSecond.getSerialNumber: {}"
                ,getClass().getName(),withAttributesBySerialFirst.getEquipment().getTitle(), withAttributesBySerialSecond.getEquipment().getTitle());
    }

    @Test
    void shouldFindEquipmentWithAttributesByDateVMI(){
        log.info("Class {},  method: shouldFindEquipmentWithAttributesByDateVMI",getClass().getName());
        EquipmentWithAttributes equipmentWithAttributesFirst = equipmentWithAttributesList.get(0);
        EquipmentWithAttributes equipmentWithAttributesSecond = equipmentWithAttributesList.get(1);
        LocalDateTime dateVMIFirst = equipmentWithAttributesFirst.getDateVMI();
        LocalDateTime dateVMISecond = equipmentWithAttributesSecond.getDateVMI();
        log.info("Class {}, method: shouldFindEquipmentWithAttributesByDateVMI, dateVMIFirst: {}, dateVMISecond: {}"
                , getClass().getName(),dateVMIFirst, dateVMISecond);
        EquipmentWithAttributes withAttributesBySerialFirst = equipmentWithAttributesRepository.findEquipmentWithAttributesByDateVMI(dateVMIFirst).get(0);
        EquipmentWithAttributes withAttributesBySerialSecond= equipmentWithAttributesRepository.findEquipmentWithAttributesByDateVMI(dateVMISecond).get(0);
        assertThat(withAttributesBySerialFirst.getDateVMI().equals(dateVMIFirst), is(true));
        assertThat(withAttributesBySerialSecond.getDateVMI().equals(dateVMISecond), is(true));
        log.info("Class {}, method: shouldFindEquipmentWithAttributesByDateVMI, withAttributesBySerialFirst.getDateVMI(): {}, withAttributesBySerialSecond.getDateVMI(): {}"
                ,getClass().getName(),withAttributesBySerialFirst.getDateVMI(), withAttributesBySerialSecond.getDateVMI());
    }

    @Test
    void shouldFindEquipmentWithAttributesByPosition_TitleAndPosition_TechObject_Title(){
        log.info("Class {},  method: shouldFindEquipmentWithAttributesByPosition_TitleAndPosition_TechObject_Title", getClass().getName());
        EquipmentWithAttributes equipmentWithAttributesFirst = equipmentWithAttributesList.get(0);
        EquipmentWithAttributes equipmentWithAttributesSecond = equipmentWithAttributesList.get(1);
        Position positionFirst = equipmentWithAttributesFirst.getPosition();
        Position positionSecond = equipmentWithAttributesSecond.getPosition();
        String positionStringFirst = positionFirst.getTitle();
        String positionStringSecond = positionSecond.getTitle();
        String techObjectPositionFirst = positionFirst.getTechObject().getTitle();
        String techObjectPositionSecond = positionSecond.getTechObject().getTitle();
        log.info("Class {}, method: shouldFindEquipmentWithAttributesByPosition_TitleAndPosition_TechObject_Title, positionStringFirst: {}, positionStringSecond: {}"
                , getClass().getName(),positionStringFirst, positionStringSecond);
        EquipmentWithAttributes withAttributesBySerialFirst = equipmentWithAttributesRepository
                .findEquipmentWithAttributesByPosition_TitleAndPosition_TechObject_Title(positionStringFirst, techObjectPositionFirst).get(0);
        EquipmentWithAttributes withAttributesBySerialSecond= equipmentWithAttributesRepository
                .findEquipmentWithAttributesByPosition_TitleAndPosition_TechObject_Title(positionStringSecond, techObjectPositionSecond).get(0);
        log.info("Class {}, method: shouldFindEquipmentWithAttributesByPosition_TitleAndPosition_TechObject_Title, "
                        + "withAttributesBySerialFirst.getDateVMI(): {}, withAttributesBySerialSecond.getDateVMI(): {}"
                        + "withAttributesBySerialFirst.getPosition().getTechObject().getTitle(): {}, "
                        + "withAttributesBySerialSecond.getPosition().getTechObject().getTitle(): {}"
                , getClass().getName(),withAttributesBySerialFirst.getPosition(), withAttributesBySerialSecond.getPosition()
                , withAttributesBySerialFirst.getPosition().getTechObject().getTitle()
                ,withAttributesBySerialSecond.getPosition().getTechObject().getTitle());
        assertThat(withAttributesBySerialFirst.getPosition().getTitle().equals(positionStringFirst), is(true));
        assertThat(withAttributesBySerialSecond.getPosition().getTitle().equals(positionStringSecond), is(true));
        assertThat(withAttributesBySerialFirst.getPosition().getTechObject().getTitle().equals(techObjectPositionFirst), is(true));
        assertThat(withAttributesBySerialSecond.getPosition().getTechObject().getTitle().equals(techObjectPositionSecond), is(true));

    }

    @Test
    void shouldFindEquipmentWithAttributesByPosition_TechObject_Title(){
        log.info("Class {},  method: shouldFindEquipmentWithAttributesByPosition_TechObject_Title",getClass().getName());
        EquipmentWithAttributes equipmentWithAttributes = equipmentWithAttributesList.get(0);
        String stringTechObjectTitle = equipmentWithAttributes.getPosition().getTechObject().getTitle();
        log.info("Class {}, method: shouldFindEquipmentWithAttributesByPosition_TechObject_Title, stringTechObjectTitle: {}"
                , getClass().getName(), stringTechObjectTitle);
        EquipmentWithAttributes withAttributesByTechObjectTitle = equipmentWithAttributesRepository.findEquipmentWithAttributesByPosition_TechObject_Title(stringTechObjectTitle).get(0);
        log.info("Class {}, method: shouldFindEquipmentWithAttributesByPosition_TechObject_Title, withAttributesByTechObjectTitle.getPosition().getTechObject().getTitle(): {}"
                ,getClass().getName(), withAttributesByTechObjectTitle.getPosition().getTechObject().getTitle());
        assertThat(withAttributesByTechObjectTitle.getPosition().getTechObject().getTitle().equals(stringTechObjectTitle), is(true));
    }

}
