package com.kga.metrologicaltechnicalsupportcontrol.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FactoryFormatterLocalDateTime {
    public static LocalDateTime parseStringFormatDDMMYYYY(String stringDate){
        return LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("dd.MM.yyyy")).atStartOfDay();
    }
}
