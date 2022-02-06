package com.kga.metrologicaltechnicalsupportcontrol.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class factoryFormatterLocalDateTime {

    public static LocalDateTime parseStringFormatDDMMYYYY(String stringDate){
        return LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
