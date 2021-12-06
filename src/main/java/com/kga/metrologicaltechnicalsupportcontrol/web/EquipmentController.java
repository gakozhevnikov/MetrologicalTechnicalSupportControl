package com.kga.metrologicaltechnicalsupportcontrol.web;

import com.kga.metrologicaltechnicalsupportcontrol.dto.EquipmentDTO;
import com.kga.metrologicaltechnicalsupportcontrol.dto.TechObjectDTO;
import com.kga.metrologicaltechnicalsupportcontrol.facade.EquipmentFacade;
import com.kga.metrologicaltechnicalsupportcontrol.services.impl.EquipmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/equipments")
public class EquipmentController {

    private EquipmentServiceImpl equipmentService;
    private EquipmentFacade equipmentFacade;

    @Autowired
    public EquipmentController(EquipmentServiceImpl equipmentService, EquipmentFacade equipmentFacade) {
        this.equipmentService = equipmentService;
        this.equipmentFacade = equipmentFacade;
    }

    @GetMapping("/")
    public ResponseEntity<List<EquipmentDTO>> getAllEquipments(){
        List<EquipmentDTO> equipmentDTOList = makeListEquipmentDTO();
        if (equipmentDTOList.isEmpty()){
            equipmentService.saveAllAndFlushFromFile();
            equipmentDTOList=makeListEquipmentDTO();
            if (equipmentDTOList.isEmpty()){
                EquipmentDTO equipmentDTOWithErrorMessage = new EquipmentDTO();
                equipmentDTOWithErrorMessage.setTitle("Error dataBase, or error of file with work-plan, or undefine error");
                equipmentDTOList.add(equipmentDTOWithErrorMessage);
                return new ResponseEntity<>(equipmentDTOList, HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(equipmentDTOList, HttpStatus.OK);
    }

    private List<EquipmentDTO> makeListEquipmentDTO(){
        return equipmentService.findAll()
                .stream()
                .map(equipmentFacade::equipmentToEquipmentDTO)
                .collect(Collectors.toList());
    }

}
