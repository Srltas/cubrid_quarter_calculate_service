package com.cubrid.quarterlycalculate.service.holidayWork;

import com.cubrid.quarterlycalculate.model.ExcelData;
import com.cubrid.quarterlycalculate.model.HolidayWorkTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HolidayWorkService {

    private final int WORK_TIME = 28800;

    public List<HolidayWorkTime> calculate(List<ExcelData> excelDataList) {
        boolean holidayCheck = false;
        boolean nextHolidayCheck = false;

        List<HolidayWorkTime> holidayWorkTimes = new ArrayList<>();

        for (int i = 0; i < excelDataList.size(); i++) {
            ExcelData workTime = excelDataList.get(i);
            holidayCheck = workTime.isHolidayCheck();

            if (i < excelDataList.size() - 1) {
                nextHolidayCheck = excelDataList.get(i + 1).isHolidayCheck();
            } else {
                nextHolidayCheck = false;
            }

            if (holidayCheck && nextHolidayCheck) {
                holidayWorkTimes.add(holidayHoliday(workTime));
            } else if (holidayCheck && !nextHolidayCheck) {
                holidayWorkTimes.add(holidayWeekday(workTime));
            } else if (!holidayCheck && nextHolidayCheck) {
                holidayWorkTimes.add(weekdayHoliday(workTime));
            } else {
                holidayWorkTimes.add(weekdayWeekday(workTime));
            }
        }
        return holidayWorkTimes;
    }

    private HolidayWorkTime weekdayWeekday(ExcelData excelData) {
        return HolidayWorkTime.builder()
                .days(excelData.getDays())
                .name(excelData.getName())
                .build();
    }

    private HolidayWorkTime weekdayHoliday(ExcelData excelData) {

        // 퇴근시간 - 출근시간 = 음수 => 출근시간을 0으로 바꿔서 계산
        // 퇴근시간 - 출근시간 = 양수 && 6시간을 Over 하면 0시간 으로 계산
        // 그외 새벽 시간(00:00 ~ 06:00)은 퇴근시간 - 출근시간 값으로 계산
        int workTime = excelData.getEndWorkTime() - excelData.getBeginWorkTime();

        if(workTime < 0) { // 00:00~06:00 사이 값만 가져온다
            workTime = excelData.getEndWorkTime();
        }else if((excelData.getBeginWorkTime() / 3600) >= 0 && (excelData.getEndWorkTime() / 3600) <= 6){  // 00:00 ~ 05:59
            workTime = excelData.getEndWorkTime() - excelData.getBeginWorkTime();
        } else { //나머지 휴일 근무가 아님
            workTime = 0;
        }

        if (workTime <= WORK_TIME) {
            return HolidayWorkTime.builder()
                    .days(excelData.getDays())
                    .name(excelData.getName())
                    .weekdayHoliday(workTime)
                    .build();
        } else {
            return HolidayWorkTime.builder()
                    .days(excelData.getDays())
                    .name(excelData.getName())
                    .weekdayHoliday(workTime)
                    .holiday8hOver(workTime - WORK_TIME)
                    .build();
        }
    }

    private HolidayWorkTime holidayWeekday(ExcelData excelData) {

        int workTime = 0;
        // 퇴근시간 - 출근시간 = 음수 => 휴일 근로시간 - "00:00 ~ 06:00 시 근무시간"
        // 퇴근시간 - 출근시간 = 양수 => 휴일 근로시간

        if(excelData.getEndWorkTime() - excelData.getBeginWorkTime() < 0) {
            workTime = excelData.getHolidayWorkTime() - excelData.getEndWorkTime();
        }else {
            workTime = excelData.getHolidayWorkTime();
        }

        if (excelData.getHolidayWorkTime() <= WORK_TIME) {
            return HolidayWorkTime.builder()
                    .days(excelData.getDays())
                    .name(excelData.getName())
                    .holidayWeekday(workTime)
                    .build();
        } else {
            return HolidayWorkTime.builder()
                    .days(excelData.getDays())
                    .name(excelData.getName())
                    .holidayWeekday(workTime)
                    .holiday8hOver(workTime - WORK_TIME)
                    .build();
        }
    }

    private HolidayWorkTime holidayHoliday(ExcelData excelData) {
        if (excelData.getHolidayWorkTime() <= WORK_TIME) {
            return HolidayWorkTime.builder()
                    .days(excelData.getDays())
                    .name(excelData.getName())
                    .holidayHoliday(excelData.getHolidayWorkTime())
                    .build();
        } else {
            return HolidayWorkTime.builder()
                    .days(excelData.getDays())
                    .name(excelData.getName())
                    .holidayHoliday(excelData.getHolidayWorkTime())
                    .holiday8hOver(excelData.getHolidayWorkTime() - WORK_TIME)
                    .build();
        }
    }
}
