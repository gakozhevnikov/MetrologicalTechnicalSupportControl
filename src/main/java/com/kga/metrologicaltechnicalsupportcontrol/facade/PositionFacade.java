package com.kga.metrologicaltechnicalsupportcontrol.facade;

import com.kga.metrologicaltechnicalsupportcontrol.dto.PositionDTO;
import com.kga.metrologicaltechnicalsupportcontrol.model.Position;
import org.springframework.stereotype.Component;

@Component
public class PositionFacade {

    public PositionDTO positionToPositionDTO(Position position){
        return new PositionDTO(position.getId(), position.getTitle());
    }
}
