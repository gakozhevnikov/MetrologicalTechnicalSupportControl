package com.kga.metrologicaltechnicalsupportcontrol.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    //private final MessageSourceAccessor messageSourceAccessor; //При применении инициализируй в конструкторе


    public ResponseEntity<ErrorInfo> countTechObjectError(HttpServletRequest req, WorkPlanFileToDataBaseException workPlanFileToDataBaseException){
        return ResponseEntity.status(ErrorType.DATA_ERROR.getStatus()).body(new ErrorInfo(
                req.getRequestURL(),
                ErrorType.DATA_ERROR,
                ErrorType.DATA_ERROR.getErrorCode(),
                workPlanFileToDataBaseException.errorInfo));
    }
}
