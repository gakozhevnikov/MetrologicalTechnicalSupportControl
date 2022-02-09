package com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces;

import com.kga.metrologicaltechnicalsupportcontrol.model.Position;
import com.kga.metrologicaltechnicalsupportcontrol.model.TechObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    Position findPositionByTitle(String title);
    Position findPositionByTitleAndTechObject(String title, TechObject techObject);
    Set<Position> findPositionsByTechObject(TechObject techObject);
}
