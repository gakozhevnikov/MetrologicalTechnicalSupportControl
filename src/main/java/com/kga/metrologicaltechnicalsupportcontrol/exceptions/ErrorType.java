package com.kga.metrologicaltechnicalsupportcontrol.exceptions;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    APP_ERROR("error.appError", HttpStatus.INTERNAL_SERVER_ERROR),
    //  http://stackoverflow.com/a/22358422/548473
    DATA_NOT_FOUND("error.dataNotFound", HttpStatus.UNPROCESSABLE_ENTITY),
    DATA_ERROR("error.dataError", HttpStatus.CONFLICT),//Использовать также при ошибках обработки файла с графиком когда количество элементов не соответствует данным в настройках
    VALIDATION_ERROR("error.validationError", HttpStatus.UNPROCESSABLE_ENTITY),
    WRONG_REQUEST("error.wrongRequest", HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND("error.fileNotFound", HttpStatus.NOT_FOUND);


    private final String errorCode;
    private final HttpStatus status;

    ErrorType(String errorCode, HttpStatus status) {
        this.errorCode = errorCode;
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}