package com.cubrid.quarterlycalculate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Builder
@RequiredArgsConstructor
@Getter
public class WorkTime {

    private final long seq;

    private final LocalDate date;

    private final String dayOfTheWeek;

    private final String name;

    private final int beginWork;

    private final int endWork;

    private final int totalWork;

    private final int nightWork;

    private final int holidayWork;

    private final int leave;

    private final Boolean holiday;
}
