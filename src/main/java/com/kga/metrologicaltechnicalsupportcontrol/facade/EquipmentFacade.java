package com.kga.metrologicaltechnicalsupportcontrol.facade;

import com.kga.metrologicaltechnicalsupportcontrol.dto.EquipmentDTO;
import com.kga.metrologicaltechnicalsupportcontrol.model.Equipment;

public class EquipmentFacade {

    public EquipmentDTO equipmentToEquipmentDTO(Equipment equipment){
        EquipmentDTO equipmentDTO = new EquipmentDTO();
        equipmentDTO.setId(equipment.getId());
        equipmentDTO.setTitle(equipmentDTO.getTitle());
        return equipmentDTO;
    }
}
