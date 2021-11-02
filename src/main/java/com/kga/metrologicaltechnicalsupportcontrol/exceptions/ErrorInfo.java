package com.kga.metrologicaltechnicalsupportcontrol.exceptions;

import java.util.List;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String typeMessage;
    private final List<String> details;

    public ErrorInfo(CharSequence url, ErrorType type, String typeMessage, List<String> details) {
        this.url = url.toString();
        this.type = type;
        this.typeMessage = typeMessage;
        this.details = details;
    }
}