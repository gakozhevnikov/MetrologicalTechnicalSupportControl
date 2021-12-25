package com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces;

import com.kga.metrologicaltechnicalsupportcontrol.model.Position;
import com.kga.metrologicaltechnicalsupportcontrol.model.TechObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechObjectRepository extends JpaRepository<TechObject, Long> {//CrudRepository

    TechObject findTechObjectByTitle(String title);
    TechObject findTechObjectByPositionsContains(Position position);

}
