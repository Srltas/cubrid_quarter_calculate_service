package com.cubrid.quarterlycalculate.controller.excel;

import lombok.Getter;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.math.NumberUtils.toInt;
import static org.apache.commons.lang3.math.NumberUtils.toLong;

@Getter
public class WorkTimeInfoDto {

    private LocalDate date;

    private String dayOfTheWeek;

    private String name;

    private int beginWork;

    private int endWork;

    private int totalWork;

    private int nightWork;

    private int holidayWork;

    private int leave;

    private boolean holiday;

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBeginWork(String beginWork) {
        this.beginWork = changeMinutes(beginWork);
    }

    public void setEndWork(String endWork) {
        this.endWork = changeMinutes(endWork);
    }

    public void setTotalWork(String totalWork) {
        this.totalWork = stringToInt(totalWork);
    }

    public void setNightWork(String nightWork) {
        this.nightWork = stringToInt(nightWork);
    }

    public void setHolidayWork(String holidayWork) {
        this.holidayWork = stringToInt(holidayWork);
    }

    public void setLeave(String leave) {
        this.leave = stringToInt(leave);
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday.equals("O") ? true : false;
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
