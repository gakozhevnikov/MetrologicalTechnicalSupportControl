package com.kga.metrologicaltechnicalsupportcontrol.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class FactoryFormatterLocalDateTimeTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    void shouldFormatStringToddMMyyyy(){
        log.info("Class {}, shouldFormatStringToddMMyyyy", getClass().getName());
        String stringDate = "01.01.2022";
        LocalDateTime localDateTime =FactoryFormatterLocalDateTime.parseStringFormatDDMMYYYY(stringDate);
        log.info("Class {}, shouldFormatStringToddMMyyyy, localDateTime: {}", getClass().getName(), localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        assertThat(localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).equals(stringDate), is(true));
    }


}
