package com.cubrid.quarterlycalculate.service.totalQuarterCalculate;

import com.cubrid.quarterlycalculate.model.ExcelData;
import com.cubrid.quarterlycalculate.model.HolidayWorkTime;
import com.cubrid.quarterlycalculate.model.QuarterWorkTime;
import com.cubrid.quarterlycalculate.model.User;
import com.cubrid.quarterlycalculate.repository.ExcelLoadRepository;
import com.cubrid.quarterlycalculate.repository.UserRepository;
import com.cubrid.quarterlycalculate.service.holidayWork.HolidayWorkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TotalQuarterCalculateServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExcelLoadRepository excelLoadRepository;

    @Autowired
    private HolidayWorkService holidayWorkService;

    @Autowired
    private TotalQuarterCalculateService totalQuarterCalculateService;

    @Test
    @DisplayName("total 근무 시간 계산")
    void calculateTotalWorkTime() {
        //given
        User user = userRepository.find("김동민");

        //excel data
        List<ExcelData> excelDataList = excelLoadRepository.find(user.getName());

        //휴일 근무 시간
        List<HolidayWorkTime> holidayWorkTimeList = holidayWorkService.calculate(excelDataList);

        //when
        List<QuarterWorkTime> quarterWorkTimeList = totalQuarterCalculateService.calculate(user, excelDataList, holidayWorkTimeList);
        QuarterWorkTime quarterWorkTime = quarterWorkTimeList.get(0);

        //then
        assertEquals("2022", quarterWorkTime.getYear());
        assertEquals("2", quarterWorkTime.getQuarter());
        assertEquals(1036800, quarterWorkTime.getQuarterTotalTime());
        assertEquals(1087200, quarterWorkTime.getQuarterLegalTime());
        assertEquals(1027378, quarterWorkTime.getQuarterWorkTime());
        assertEquals(0, quarterWorkTime.getRegulationWorkOverTime());
        assertEquals(0, quarterWorkTime.getLegalWorkOverTime());
        assertEquals(0, quarterWorkTime.getNightWorkTime());
        assertEquals(0, quarterWorkTime.getHolidayWorkTime());
        assertEquals(0, quarterWorkTime.getHoliday8HOver());
        assertEquals(28800, quarterWorkTime.getLeaveTime());
        assertEquals(0, quarterWorkTime.getCompensationLeaveTime());
        assertEquals(0, quarterWorkTime.getCalculateMoney());
        assertEquals(0, quarterWorkTime.getCalculateTotal());

    }
}