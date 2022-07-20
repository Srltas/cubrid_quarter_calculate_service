package com.cubrid.quarterlycalculate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class HolidayWorkTime {

    private LocalDate date;

    private String id;

    private int holidayHoliday;

    private int holidayWeekday;

    private int weekdayHoliday;

    private int holiday8hOver;
}
