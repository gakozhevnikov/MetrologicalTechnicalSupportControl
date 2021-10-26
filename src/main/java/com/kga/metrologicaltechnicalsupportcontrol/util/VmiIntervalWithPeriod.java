package com.kga.metrologicaltechnicalsupportcontrol.util;

import lombok.Getter;
import lombok.Setter;

import java.time.Period;

/**Адаптированный формат для работы с периодом*/
@Getter
@Setter
//@Component
public class VmiIntervalWithPeriod {
    /**java.time.Period*/
    private Period period;
    /**Обозначение периода*/
    private VmiIntervalUnits unit;

    public VmiIntervalWithPeriod(Integer period, VmiIntervalUnits unit) {
        //this.unit = unit;
        this.period=switch (unit){
            case YEAR -> Period.ofYears(period);
            case MONTH -> Period.ofMonths(period);
            case WEEK -> Period.ofWeeks(period);
            case DAY -> Period.ofDays(period);
        };
        System.out.println(this.unit);
    }
}
