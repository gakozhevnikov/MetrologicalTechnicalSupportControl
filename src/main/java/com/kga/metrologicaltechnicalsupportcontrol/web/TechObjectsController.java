package com.kga.metrologicaltechnicalsupportcontrol.web;

import com.kga.metrologicaltechnicalsupportcontrol.dto.TechObjectDTO;
import com.kga.metrologicaltechnicalsupportcontrol.facade.TechObjectFacade;
import com.kga.metrologicaltechnicalsupportcontrol.services.impl.TechObjectServiceImpl;
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
@RequestMapping("/tech_objects")
public class TechObjectsController {

    @Autowired
    private TechObjectServiceImpl techObjectService;
    @Autowired
    private TechObjectFacade techObjectFacade;

    @GetMapping("/")
    ResponseEntity<List<TechObjectDTO>> getAllTechObject(){
        List<TechObjectDTO> allTechObjectList = makeListTechObjectsDTO();
        if(allTechObjectList.isEmpty()){
            techObjectService.saveAllAndFlushFromFile();
            allTechObjectList = makeListTechObjectsDTO();
            if (allTechObjectList.isEmpty()){
                TechObjectDTO techObjectDTOWithErrorMessage = new TechObjectDTO();
                techObjectDTOWithErrorMessage.setTitle("Error dataBase, or error of file with work-plan, or undefine error");
                allTechObjectList.add(techObjectDTOWithErrorMessage);
                return new ResponseEntity<>(allTechObjectList, HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(allTechObjectList, HttpStatus.OK);
    }

    private List<TechObjectDTO> makeListTechObjectsDTO(){
        return techObjectService.findAll()
                .stream()
                .map(techObjectFacade::techObjectToTechObjectDTO)
                .collect(Collectors.toList());
    }

}
