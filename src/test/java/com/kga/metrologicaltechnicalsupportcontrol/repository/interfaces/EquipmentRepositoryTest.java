package com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces;

import com.kga.metrologicaltechnicalsupportcontrol.model.Equipment;
import com.kga.metrologicaltechnicalsupportcontrol.model.TechObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class EquipmentRepositoryTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    EquipmentRepository equipmentRepository;

    @BeforeEach
    void setUp(){
        equipmentRepository.deleteAll();
        log.info("Class {}, method setUp",getClass().getName());
    }

    @Test
    void shouldTableMustEmpty(){
        log.info("Class {}, method shouldTableMustEmpty, techObjectRepository.findAll().isEmpty(): {}",getClass().getName(), equipmentRepository.findAll().isEmpty());
        assertThat(equipmentRepository.findAll().isEmpty(), is(true));
    }

    @Test
    void shouldFindEquipmentByTitle(){
        setUp();
        Equipment equipment = new Equipment();
        String titleTest= "Test in EquipmentRepositoryTest";
        equipment.setTitle(titleTest);
        log.info("Class {}, method shouldFindEquipmentByTitle, equipment.getTitle(): {}",getClass().getName(), equipment.getTitle());
        equipmentRepository.save(equipment);
        List<Equipment> equipmentList =equipmentRepository.findAll();
        log.info("Class {}, method shouldFindEquipmentByTitle, equipmentList: {}", getClass().getName(), equipmentList);
        assertThat(equipmentList, hasSize(1));
        assertThat(equipmentList.get(0).getTitle(), is(titleTest));
        tearDown();
    }

    @Test
    void shouldGetEquipmentById(){
        setUp();
        Equipment equipment = new Equipment();
        String titleTest= "Test in EquipmentRepositoryTest";
        equipment.setTitle(titleTest);
        log.info("Class {}, method shouldGetEquipmentById, equipment.getTitle(): {}",getClass().getName(), equipment.getTitle());
        Long idAfterSave = equipmentRepository.save(equipment).getId();
        log.info("Class {}, method shouldGetEquipmentById, idAfterSave: {}", getClass().getName(), idAfterSave);
        Long idAfterGetId = equipmentRepository.getById(idAfterSave).getId();
        log.info("Class {}, method shouldGetEquipmentById, idAfterGetId: {}", getClass().getName(), idAfterGetId);
        assertThat(Objects.equals(idAfterGetId, idAfterSave), is(true));
        tearDown();
    }


    @AfterEach
    void tearDown(){
        equipmentRepository.deleteAll();
        log.info("Class {}, method tearDown",getClass().getName());
    }
}
