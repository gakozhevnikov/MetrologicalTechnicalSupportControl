package com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces;


import com.kga.metrologicaltechnicalsupportcontrol.model.maintenance.TypeService;
import com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces.maintenance.TypeServiceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class TypeServiceRepositoryTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    TypeServiceRepository typeServiceRepository;

    String designationFirstTest = "Designation First Test";
    String designationSecondTest = "Designation Second Test";
    TypeService typeServiceFirstTest = new TypeService();
    TypeService typeServiceSecondTest = new TypeService();

    @BeforeEach//выполняется перед каждым тестовым методом
    void setUp(){
        typeServiceRepository.deleteAllInBatch();
        log.info("Class {}, method setUp",getClass().getName());
    }

    @AfterEach
    void tearDown(){
        typeServiceRepository.deleteAllInBatch();
        log.info("Class {}, method tearDown",getClass().getName());
    }

    @Test
    void shouldTableMustEmpty() {
        log.info("Class {}, method shouldTableMustEmpty, typeServiceRepository.findAll().isEmpty(): {}",getClass().getName(), typeServiceRepository.findAll().isEmpty());
        assertThat(typeServiceRepository.findAll().isEmpty(), is(true));
    }

    @Test
    void shouldFindTypeServiceByDesignation(){
        log.info("Class {}, method shouldFindTypeServiceByDesignation",getClass().getName());
        typeServiceFirstTest.setDesignation(designationFirstTest);
        typeServiceSecondTest.setDesignation(designationSecondTest);
        log.info("Class {}, method shouldFindTypeServiceByDesignation, typeServiceFirstTest {}, typeServiceSecondTest {}"
                ,getClass().getName(), typeServiceFirstTest, typeServiceSecondTest);
        typeServiceRepository.saveAndFlush(typeServiceFirstTest);
        typeServiceRepository.saveAndFlush(typeServiceSecondTest);
        TypeService typeServiceReturnByDesignation = typeServiceRepository.findTypeServiceByDesignation(designationFirstTest);
        log.info("Class {}, method shouldFindTypeServiceByDesignation, typeServiceReturnByDesignation {}"
                ,getClass().getName(), typeServiceReturnByDesignation);
        assertThat(typeServiceReturnByDesignation.getDesignation().equals(designationFirstTest), is(true));
    }
    @Test
    void shouldReturnEmptyTypeService(){//Only see log file
        log.info("Class {}, method shouldReturnEmptyTypeService, typeServiceRepository.findTypeServiceByDesignation() {}"
                ,getClass().getName(), typeServiceRepository.findTypeServiceByDesignation("No"));
    }

}
