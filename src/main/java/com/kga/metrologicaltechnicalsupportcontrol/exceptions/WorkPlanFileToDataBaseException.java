package com.kga.metrologicaltechnicalsupportcontrol.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class WorkPlanFileToDataBaseException extends RuntimeException{
    public WorkPlanFileToDataBaseException(String message){
        super(message);
    }
}
