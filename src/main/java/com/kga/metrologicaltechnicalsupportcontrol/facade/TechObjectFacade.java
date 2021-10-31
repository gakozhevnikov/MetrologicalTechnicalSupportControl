package com.kga.metrologicaltechnicalsupportcontrol.facade;

import com.kga.metrologicaltechnicalsupportcontrol.dto.TechObjectDTO;
import com.kga.metrologicaltechnicalsupportcontrol.model.TechObject;
import org.springframework.stereotype.Component;

@Component
public class TechObjectFacade {

    public TechObjectDTO techObjectTotechObjectDTO(TechObject techObject){
        TechObjectDTO techObjectDTO = new TechObjectDTO();
        techObjectDTO.setId(techObject.getId());
        techObjectDTO.setTitle(techObject.getTitle());
        return techObjectDTO;
    }
}
