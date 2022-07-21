package com.cubrid.quarterlycalculate.service;

import com.cubrid.quarterlycalculate.model.HolidayWorkTime;
import com.cubrid.quarterlycalculate.model.User;
import com.cubrid.quarterlycalculate.model.ExcelData;
import com.cubrid.quarterlycalculate.repository.ExcelLoadRepository;
import com.cubrid.quarterlycalculate.repository.HolidayRepository;
import com.cubrid.quarterlycalculate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HolidayWorkService {

    private final UserRepository userRepository;

    private final ExcelLoadRepository excelLoadRepository;

    private final HolidayRepository holidayRepository;

    public List<HolidayWorkTime> find(String name) {
        return holidayRepository.find(name);
    }

    public void calculate() {
        List<User> users = userRepository.findAll();

        boolean holidayCheck = false;
        boolean nextHolidayCheck = false;

        List<HolidayWorkTime> holidayWorkTimes = new ArrayList<>();

        for (User user : users) {
            List<ExcelData> excelDataList = excelLoadRepository.find(user.getName());

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
        }
        holidayRepository.save(holidayWorkTimes);
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

        if(workTime < 0) { // 06:00 ~ 00:01
            workTime = excelData.getEndWorkTime();
        }else if((excelData.getBeginWorkTime() / 60) > 0 && (excelData.getEndWorkTime() / 60) <= 6){ // 06:01 ~ 23:59
            workTime = excelData.getEndWorkTime() - excelData.getBeginWorkTime();
        } else {
            workTime = 0;
        }

        if (workTime <= 480) {
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
                    .holiday8hOver(workTime - 480)
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

        if (excelData.getHolidayWorkTime() <= 480) {
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
                    .holiday8hOver(workTime - 480)
                    .build();
        }
    }

    private HolidayWorkTime holidayHoliday(ExcelData excelData) {
        if (excelData.getHolidayWorkTime() <= 480) {
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
                    .holiday8hOver(excelData.getHolidayWorkTime() - 480)
                    .build();
        }
    }
}
