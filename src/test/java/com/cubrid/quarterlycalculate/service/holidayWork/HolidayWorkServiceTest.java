package com.cubrid.quarterlycalculate.service.holidayWork;

import com.cubrid.quarterlycalculate.model.ExcelData;
import com.cubrid.quarterlycalculate.model.HolidayWorkTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class HolidayWorkServiceTest {

    @Autowired
    private HolidayWorkService holidayWorkService;

    @Test
    @DisplayName("휴일 근무 계산")
    void holidayWorkTimeCal() {
        //given
        List excelDataList = List.of(ExcelData.builder()
                .days(LocalDate.of(2022, 4, 1))
                .dayWeek("금")
                .name("김동민")
                .beginWorkTime(30000)
                .endWorkTime(62280)
                .workTime(28622)
                .nightWorkTime(0)
                .holidayWorkTime(0)
                .leaveTime(0)
                .holidayCheck(false)
                .build());
        //when
        List<HolidayWorkTime> holidayWorkTimesList = holidayWorkService.calculate(excelDataList);
        HolidayWorkTime holidayWorkTime = holidayWorkTimesList.get(0);

        //then
        assertEquals(0, holidayWorkTime.getHolidayHoliday());
        assertEquals(0, holidayWorkTime.getHolidayWeekday());
        assertEquals(0, holidayWorkTime.getWeekdayHoliday());
        assertEquals(0, holidayWorkTime.getHoliday8hOver());
    }

    @Test
    @DisplayName("평일 -> 휴일 새벽 근무")
    void weekdayHolidayTimeCel() {
        //given
        List excelDataList = List.of(
                ExcelData.builder()
                    .beginWorkTime(21900)
                    .endWorkTime(7200)
                    .workTime(71692)
                    .nightWorkTime(14400)
                    .holidayWorkTime(71692)
                    .leaveTime(0)
                    .holidayCheck(false)
                    .build(),
                ExcelData.builder()
                    .holidayCheck(true)
                    .build()
        );

        //when
        List<HolidayWorkTime> holidayWorkTimesList = holidayWorkService.calculate(excelDataList);
        HolidayWorkTime holidayWorkTime = holidayWorkTimesList.get(0);

        //then
        assertEquals(0, holidayWorkTime.getHolidayHoliday());
        assertEquals(0, holidayWorkTime.getHolidayWeekday());
        assertEquals(7200, holidayWorkTime.getWeekdayHoliday());
        assertEquals(0, holidayWorkTime.getHoliday8hOver());
    }
}