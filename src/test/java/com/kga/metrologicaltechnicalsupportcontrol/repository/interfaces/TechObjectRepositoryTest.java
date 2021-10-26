package com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces;

import com.kga.metrologicaltechnicalsupportcontrol.model.TechObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TechObjectRepositoryTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    TechObjectRepository techObjectRepository;

    @BeforeEach
    void setUp() {
        techObjectRepository.deleteAll();
        log.info("Class {}, method setUp",getClass().getName());
    }

    @Test
    void shouldTableMustEmpty(){
        log.info("Class {}, method shouldTableMustEmpty, techObjectRepository.findAll().isEmpty(): {}",getClass().getName(), techObjectRepository.findAll().isEmpty());
        assertThat(techObjectRepository.findAll().isEmpty(), is(true));
    }

    @Test
    @DisplayName("Find TechObject by title")
    void findTechObjectByTitle(){
        setUp();
        TechObject techObject = new TechObject();
        String titleTest= "Test in TechObjectRepositoryTest";
        techObject.setTitle(titleTest);
        log.info("Class {}, method findTechObjectByTitle, techObject.getTitle(): {}",getClass().getName(), techObject.getTitle());
        techObjectRepository.save(techObject);
        List<TechObject> techObjectList =techObjectRepository.findAll();
        log.info("Class {}, method findTechObjectByTitle, techObjectList: {}", getClass().getName(), techObjectList);
        assertThat(techObjectList, hasSize(1));
        assertThat(techObjectList.get(0).getTitle(), is(titleTest));
        tearDown();
    }


    @AfterEach
    void tearDown() {
        techObjectRepository.deleteAll();
    }
}