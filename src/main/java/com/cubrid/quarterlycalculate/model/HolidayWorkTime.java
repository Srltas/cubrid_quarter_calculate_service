package com.cubrid.quarterlycalculate.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class HolidayWorkTime {

    private Long seq;

    private LocalDate days;

    private String name;

    private int holidayHoliday;

    private int holidayWeekday;

    private int weekdayHoliday;

    private int holiday8hOver;

    @Builder
    public HolidayWorkTime(Long seq, LocalDate days, String name, int holidayHoliday, int holidayWeekday, int weekdayHoliday, int holiday8hOver) {
        this.seq = seq;
        this.days = days;
        this.name = name;
        this.holidayHoliday = holidayHoliday;
        this.holidayWeekday = holidayWeekday;
        this.weekdayHoliday = weekdayHoliday;
        this.holiday8hOver = holiday8hOver;
    }
}
