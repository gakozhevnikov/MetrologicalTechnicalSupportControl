package com.kga.metrologicaltechnicalsupportcontrol.web;

import com.kga.metrologicaltechnicalsupportcontrol.model.TechObject;
import com.kga.metrologicaltechnicalsupportcontrol.services.impl.TechObjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tech_objects")
public class TechObjectsController {

    @Autowired
    private TechObjectServiceImpl techObjectService;

    @GetMapping("/")
    ResponseEntity<List<TechObject>> getAllTechObject(){
        List<TechObject> allTechObjectList = techObjectService.findAll();
        if(allTechObjectList.isEmpty()){

            techObjectService.saveAllAndFlushFromFile();
            allTechObjectList = techObjectService.findAll();
            if (allTechObjectList.isEmpty()){
                TechObject techObjectWithErrorMessage = new TechObject();
                techObjectWithErrorMessage.setTitle("Error dataBase, or error of file with work-plan, or undefine error");
                allTechObjectList.add(new TechObject());
                return new ResponseEntity<>(allTechObjectList, HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(allTechObjectList, HttpStatus.OK);
    }


}
