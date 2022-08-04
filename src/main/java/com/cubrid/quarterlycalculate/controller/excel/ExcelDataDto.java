package com.cubrid.quarterlycalculate.controller.excel;

import lombok.Getter;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.math.NumberUtils.toInt;
import static org.apache.commons.lang3.math.NumberUtils.toLong;

@Getter
public class ExcelDataDto {

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

    public void setDate(LocalDate days) {
        this.days = days;
    }

    public void setDayOfTheWeek(String dayWeek) {
        this.dayWeek = dayWeek;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBeginWork(String beginTime) {
        this.beginWorkTime = changeMinutes(beginTime);
    }

    public void setEndWork(String endTime) {
        this.endWorkTime = changeMinutes(endTime);
    }

    public void setTotalWork(String totalTime) {
        this.workTime = stringToInt(totalTime);
    }

    public void setNightWork(String nightTime) {
        this.nightWorkTime = stringToInt(nightTime);
    }

    public void setHolidayWork(String holidayTime) {
        this.holidayWorkTime = stringToInt(holidayTime);
    }

    public void setLeave(String leaveTime) {
        this.leaveTime = stringToInt(leaveTime);
    }

    public void setHoliday(String holidayCheck) {
        this.holidayCheck = holidayCheck.equals("O") ? true : false;
    }

    public int changeMinutes(String time) {
        if (time.equals("-"))
            return 0;

        String[] timeArray = time.split(":");
        return (toInt(timeArray[0]) * 60 * 60) + (toInt(timeArray[1]) * 60);
    }

    public int stringToInt(String millisString) {
        return Long.valueOf(millisToMinutes(toLong(millisString.split("/")[0], 0))).intValue();
    }

    private long millisToMinutes(long millis) {
        return TimeUnit.MILLISECONDS.toSeconds(millis);
    }
}
