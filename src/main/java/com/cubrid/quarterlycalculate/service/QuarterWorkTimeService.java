package com.cubrid.quarterlycalculate.service;

import com.cubrid.quarterlycalculate.controller.excel.WorkTimeInfoDto;
import com.cubrid.quarterlycalculate.model.QuarterWorkTime;
import com.cubrid.quarterlycalculate.repository.QuarterWorkTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuarterWorkTimeService {

    private final QuarterWorkTimeRepository quarterWorkTimeRepository;

    @Transactional
    public void calculate(List<WorkTimeInfoDto> workTimeList) {

        List<WorkTimeInfoDto> adminWorkTimeList = findAdminWorkTimeList(workTimeList);

        int totalWorkTime = 0;

        for (WorkTimeInfoDto workTimeInfoDto : adminWorkTimeList)
            if (!workTimeInfoDto.isHoliday() && !workTimeInfoDto.getDayOfTheWeek().equals("토"))
                totalWorkTime++;

        log.debug("totalWorkTime : {}", totalWorkTime);

        int year = findYear(adminWorkTimeList);
        int month = findFirstMonth(adminWorkTimeList);

        quarterWorkTimeRepository.save(
                new QuarterWorkTime(
                        quarterlyCalculation(month),
                        totalWorkTime(totalWorkTime),
                        legalWorkTime(year, month))
        );
    }

    private List<WorkTimeInfoDto> findAdminWorkTimeList(List<WorkTimeInfoDto> workTimeList) {
        List<WorkTimeInfoDto> adminWorkTimeList = new ArrayList<>();

        //admin 계정의 근무기록 가져오기
        for (WorkTimeInfoDto workTimeInfoDto : workTimeList) {
            if (workTimeInfoDto.getName().equals("admin")) {
                adminWorkTimeList.add(workTimeInfoDto);
            }
        }

        //새벽근무 확인을 위해서 다음 분기 첫날까지 근무기록이 있기 때문에 마지막 날은 빼준다. ex) 2분기(3월~6월) 시 7월 1일까지 근무기록이 있음
        adminWorkTimeList.remove(adminWorkTimeList.size() - 1);
        return adminWorkTimeList;
    }

    private int findYear(List<WorkTimeInfoDto> adminWorkTimeList) {
        return adminWorkTimeList.get(0).getDate().getYear();
    }

    private int findFirstMonth(List<WorkTimeInfoDto> adminWorkTimeList) {
        //분기의 시작월 구하기
        return adminWorkTimeList.get(0).getDate().getMonthValue();
    }

    private int totalWorkTime(int totalWorkTime) {
        return totalWorkTime * 8 * 60 * 60;
    }

    private int legalWorkTime(int year, int month) {
        int legalWorkTime = 0;

        for (int i = 0; i < 3; i++) {
            Integer lengthOfMonth = LocalDate.of(year, month + i, 1).lengthOfMonth();
            //TODO 법정근로시간을 실수로 계산하기 때문에 올림 처리로 계산을 해야하나??
            //TODO 형변환 시 조금 더 좋은 코드는 없을까? 예를 들면 형 변환 시 발생되는 예외 처리가 되어 있는 코드
            legalWorkTime += (int) (((lengthOfMonth.doubleValue() * 40.0) / 7.0) * 60 * 60);
        }

        return legalWorkTime;
    }

    private String quarterlyCalculation(int firstMonth) {
        switch (firstMonth) {
            case 1: case 2: case 3:
                return "1";
            case 4: case 5: case 6:
                return "2";
            case 7: case 8: case 9:
                return "3";
            default:
                return "4";
        }
    }
}
