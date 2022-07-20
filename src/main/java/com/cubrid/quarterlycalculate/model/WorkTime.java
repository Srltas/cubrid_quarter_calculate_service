package com.cubrid.quarterlycalculate.model;

import com.cubrid.quarterlycalculate.controller.excel.WorkTimeDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WorkTime {

    private Long seq;

    private LocalDate days;

    private String dayWeek;

    private String name;

    private int beginTime;

    private int endTime;

    private int totalTime;

    private int nightTime;

    private int holidayTime;

    private int leaveTime;

    private boolean holidayCheck;

    public WorkTime(WorkTimeDto workTimeDto) {
        this.days = workTimeDto.getDays();
        this.dayWeek = workTimeDto.getDayWeek();
        this.name = workTimeDto.getName();
        this.beginTime = workTimeDto.getBeginTime();
        this.endTime = workTimeDto.getEndTime();
        this.totalTime = workTimeDto.getTotalTime();
        this.nightTime = workTimeDto.getNightTime();
        this.holidayTime = workTimeDto.getHolidayTime();
        this.leaveTime = workTimeDto.getLeaveTime();
        this.holidayCheck = workTimeDto.isHolidayCheck();
    }

    @Builder
    public WorkTime(Long seq, LocalDate days, String dayWeek, String name, int beginTime, int endTime, int totalTime, int nightTime, int holidayTime, int leaveTime, Boolean holidayCheck) {
        this.seq = seq;
        this.days = days;
        this.dayWeek = dayWeek;
        this.name = name;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
        this.nightTime = nightTime;
        this.holidayTime = holidayTime;
        this.leaveTime = leaveTime;
        this.holidayCheck = holidayCheck;
    }
}
