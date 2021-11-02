package com.kga.metrologicaltechnicalsupportcontrol.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


public class WorkPlanFileToDataBaseException extends RuntimeException{

    List<String> errorInfo;

    public WorkPlanFileToDataBaseException(List<String> errorInfo){
        this.errorInfo = errorInfo;
    }
}
