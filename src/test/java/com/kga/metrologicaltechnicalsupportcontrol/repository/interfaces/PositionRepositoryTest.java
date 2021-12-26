package com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces;

import com.kga.metrologicaltechnicalsupportcontrol.model.Position;
import com.kga.metrologicaltechnicalsupportcontrol.model.TechObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
@SpringBootTest
public class PositionRepositoryTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    PositionRepository positionRepository;
    @Autowired
    TechObjectRepository techObjectRepository;

    String titlePosition= "Test in PositionRepositoryTest";
    String titlePositionSecond= "Test Second Position";
    String titleTechObject = "TestTechObject";

    @BeforeEach
    void setUp() {
        positionRepository.deleteAllInBatch();
        log.info("Class {}, method setUp",getClass().getName());
    }

    @AfterEach
    void tearDown() {
        positionRepository.deleteAllInBatch();
        log.info("Class {}, method tearDown",getClass().getName());
    }

    @Test
    void shouldTableMustEmpty() {
        log.info("Class {}, method shouldTableMustEmpty, positionRepository.findAll().isEmpty(): {}",getClass().getName(), positionRepository.findAll().isEmpty());
        assertThat(positionRepository.findAll().isEmpty(), is(true));
    }

    @Test
    void shouldFindPositionByTitle() {
        Position position = new Position();
        position.setTitle(titlePosition);
        log.info("Class {}, method shouldFindPositionByTitle, position.getTitle(): {}",getClass().getName(), position.getTitle());
        positionRepository.save(position);
        List<Position> positionList =positionRepository.findAll();
        log.info("Class {}, method shouldFindPositionByTitle, positionList: {}", getClass().getName(), positionList);
        assertThat(positionList, hasSize(1));
        assertThat(positionList.get(0).getTitle(), is(titlePosition));
    }

    @Test
    void shouldFindPositionByTechObject(){
        TechObject techObject = techObjectRepository.findAll().get(0);
        if(techObject.getTitle().isEmpty()){
            techObject.setId(0L);
            techObject.setTitle(titleTechObject);
        }
        Position position = new Position();
        position.setTitle(titlePosition);
        position.setTechObject(techObject);
        Position positionSecond = new Position();
        positionSecond.setTitle(titlePositionSecond);
        positionSecond.setTechObject(techObject);
        log.info("Class {}, method shouldFindPositionByTechObject, position.getTitle(): {}, position.getTechObject().getTitle(): {}"
                ,getClass().getName(), position.getTitle(), position.getTechObject().getTitle());
        positionRepository.save(position);
        positionRepository.save(positionSecond);
        List<Position> positionList =positionRepository.findAll();
        log.info("Class {}, method shouldFindPositionByTechObject, positionList: {}", getClass().getName(), positionList);
        assertThat(positionList, hasSize(2));
        Set<Position> positionSet = positionRepository.findPositionsByTechObject(techObject);
        log.info("Class {}, method shouldFindPositionByTechObject, positionSet: {}", getClass().getName(), positionSet);
        log.info("Class {}, method shouldFindPositionByTechObject, positionSet.size(): {}", getClass().getName(), positionSet.size());
        assertThat(positionSet, hasSize(2));
        List<String> getTitlePosition = positionSet.stream().map(Position::getTitle).collect(Collectors.toList());
        log.info("Class {}, method shouldFindPositionByTechObject, getTitlePosition: {}", getClass().getName(), getTitlePosition);
        assertThat(getTitlePosition, contains(position.getTitle(), positionSecond.getTitle()));
        TechObject getTechObjectFromPositionSet = positionSet.stream().findFirst().get().getTechObject();
        log.info("Class {}, method shouldFindPositionByTechObject, techObject.getTitle(): {}, getTechObjectFromPositionSet.getTitle(): {}"
                ,getClass().getName(), techObject.getTitle(), getTechObjectFromPositionSet.getTitle());
        assertThat(techObject.getTitle().equals(getTechObjectFromPositionSet.getTitle()), is(true));
    }
}
