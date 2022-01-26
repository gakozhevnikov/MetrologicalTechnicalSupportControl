package com.kga.metrologicaltechnicalsupportcontrol.web;


import com.kga.metrologicaltechnicalsupportcontrol.dto.PositionDTO;
import com.kga.metrologicaltechnicalsupportcontrol.facade.PositionFacade;
import com.kga.metrologicaltechnicalsupportcontrol.services.impl.PositionServiceImpl;
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
@CrossOrigin(origins ="*", allowedHeaders = "*")//see https://howtodoinjava.com/spring-boot2/spring-cors-configuration/
@RequestMapping("/positions")
public class PositionController {

    private final PositionServiceImpl positionService;

    private final PositionFacade positionFacade;

    @Autowired
    public PositionController(PositionServiceImpl positionService, PositionFacade positionFacade) {
        this.positionService = positionService;
        this.positionFacade = positionFacade;
    }


    @GetMapping("/")//стоп 23.01.2022 Вывести это на клиенте angular
    public ResponseEntity<List<PositionDTO>> getAllPositionsDTO(){
        List<PositionDTO> positionDTOS = makePositionsDTO();
        if(positionDTOS.isEmpty()){
            positionService.saveAllAndFlushFromFile();
            positionDTOS = makePositionsDTO();
            if(positionDTOS.isEmpty()){
                positionDTOS.add(new PositionDTO("Error dataBase, or error of file with work-plan, or undefine error"));
                return new ResponseEntity<> (positionDTOS, HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(positionDTOS, HttpStatus.OK);
    }

    private List<PositionDTO> makePositionsDTO(){
        return positionService.findAll()
                .stream()
                .map(positionFacade::positionToPositionDTO)
                .collect(Collectors.toList());
    }


}
