package com.cubrid.quarterlycalculate.model;

import com.cubrid.quarterlycalculate.controller.excel.ExcelDataDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ExcelData {

    private Long seq;

    private LocalDate days;

    private String dayWeek;

    private String name;

    private int beginWorkTime;

    private int endWorkTime;

    private int workTime;

    private int nightWorkTime;

    private int holidayWorkTime;

    private int leaveTime;

    private boolean holidayCheck;

    public ExcelData(ExcelDataDto excelDataDto) {
        this.days = excelDataDto.getDays();
        this.dayWeek = excelDataDto.getDayWeek();
        this.name = excelDataDto.getName();
        this.beginWorkTime = excelDataDto.getBeginWorkTime();
        this.endWorkTime = excelDataDto.getEndWorkTime();
        this.workTime = excelDataDto.getWorkTime();
        this.nightWorkTime = excelDataDto.getNightWorkTime();
        this.holidayWorkTime = excelDataDto.getHolidayWorkTime();
        this.leaveTime = excelDataDto.getLeaveTime();
        this.holidayCheck = excelDataDto.isHolidayCheck();
    }

    @Builder
    public ExcelData(Long seq, LocalDate days, String dayWeek, String name, int beginWorkTime, int endWorkTime, int workTime, int nightWorkTime, int holidayWorkTime, int leaveTime, Boolean holidayCheck) {
        this.seq = seq;
        this.days = days;
        this.dayWeek = dayWeek;
        this.name = name;
        this.beginWorkTime = beginWorkTime;
        this.endWorkTime = endWorkTime;
        this.workTime = workTime;
        this.nightWorkTime = nightWorkTime;
        this.holidayWorkTime = holidayWorkTime;
        this.leaveTime = leaveTime;
        this.holidayCheck = holidayCheck;
    }
}
