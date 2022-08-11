package com.cubrid.quarterlycalculate.service.totalQuarterCalculate;

import com.cubrid.quarterlycalculate.model.ExcelData;
import com.cubrid.quarterlycalculate.model.HolidayWorkTime;
import com.cubrid.quarterlycalculate.model.QuarterWorkTime;
import com.cubrid.quarterlycalculate.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TotalQuarterCalculateService {

    public List<QuarterWorkTime> calculate(User user, List<ExcelData> excelDataList, List<HolidayWorkTime> holidayWorkTimeList) {
        List<ExcelData> calculatedExcelDataList = findUserWorkTimeList(user, excelDataList);

        //분기 총 근로시간(공휴일을 제외한 일을 해야하는 시간)
        int quarterTotalTime = getQuarterTotalWorkTime(calculatedExcelDataList);
        //분기 총 법정근로시간(법으로 정해진 분기에 일을 해야하는 시간)
        int quarterLegalTime = getQuarterLegalWorkTime(calculatedExcelDataList);
        //분기 근로시간(분기에 내가 실제로 일을 한 시간)
        int quarterWorkTime = 0;
        //분기 휴가 사용시간
        int leaveTime = 0;
        //분기 야간 근로시간
        int nightWorkTime = 0;

        for (ExcelData excelData : calculatedExcelDataList) {
            quarterWorkTime += excelData.getWorkTime();
            leaveTime += excelData.getLeaveTime();
            nightWorkTime += excelData.getNightWorkTime();
        }

        //휴일 근로시간
        int holidayWorkTime = 0;
        for (HolidayWorkTime holidayWork : holidayWorkTimeList) {
            holidayWorkTime += holidayWork.getHolidayHoliday() + holidayWork.getHolidayWeekday() + holidayWork.getWeekdayHoliday();
        }

        //휴일 8시간 이상 근무 했을 경우 초과된 시간
        int holiday8HOver = 0;
        for (HolidayWorkTime workTime : holidayWorkTimeList) {
            holiday8HOver += workTime.getHoliday8hOver();
        }

        //법정근로 연장시간
        int legalOverTime = (quarterWorkTime - leaveTime) > quarterLegalTime ? quarterWorkTime - leaveTime - quarterLegalTime : 0;

        //소정근로 연장시간
        int regulationWorkOverTime = quarterWorkTime > quarterTotalTime ? quarterWorkTime - legalOverTime - quarterTotalTime : 0;

        //분기 수당
        //분기 수당 (분 제외) = 법정근로 연장시간(분 제외) * 1.5 + 야간 근로시간(분 포함) * 0.5 + 휴일 근로시간(분 포함) * 0.5 + 공휴일 8시간 초과(분 포함) * 0.5
        //계산 편의를 위해서 분단위로 계산 진행 후 초로 변환
        int calculateMoney = (int)
            (Math.floor(((legalOverTime / 3600)) * 60 * 1.5) +
             Math.floor((nightWorkTime / 60) * 0.5) +
             Math.floor((holidayWorkTime / 60) * 0.5) +
             Math.floor((holiday8HOver / 60) * 0.5)) * 60;

        //분기 정산 총계
        int calculateTotal = regulationWorkOverTime + calculateMoney;

        return List.of(
            QuarterWorkTime.builder()
                .name(user.getName())
                .year(getYear(calculatedExcelDataList))
                .quarter(quarterlyCalculation(calculatedExcelDataList))
                .quarterTotalTime(quarterTotalTime)
                .quarterLegalTime(quarterLegalTime)
                .quarterWorkTime(quarterWorkTime)
                .regulationWorkOverTime(regulationWorkOverTime)
                .legalWorkOverTime(legalOverTime)
                .nightWorkTime(nightWorkTime)
                .holidayWorkTime(holidayWorkTime)
                .holiday8HOver(holiday8HOver)
                .leaveTime(leaveTime)
                .compensationLeaveTime(regulationWorkOverTime)
                .calculateMoney(calculateMoney)
                .calculateTotal(calculateTotal)
                .build()
        );
    }

    //분기 총 법정근로시간
    private int getQuarterLegalWorkTime(List<ExcelData> excelDataList) {
        return (int) ((excelDataList.size() * 40.0) / 7.0) * 60 * 60;
    }

    //분기 총 근로시간
    private int getQuarterTotalWorkTime(List<ExcelData> excelDataList) {
        int totalWorkTime = 0;
        for (ExcelData excelData : excelDataList) {
            if (!excelData.isHolidayCheck() && !excelData.getDayWeek().equals("토")) {
                totalWorkTime += 1;
            }
        }
        return totalWorkTime * 8 * 60 * 60;
    }

    //사용자 별로 Excel Data 가져오기
    private List<ExcelData> findUserWorkTimeList(User user, List<ExcelData> excelDataList) {

        //신규 입사자인 경우
        int firstDayIndex = 0;
        if (user.getFirstDayOfWork().isAfter(excelDataList.get(0).getDays())) {
            for (int i = 0; i < excelDataList.size(); i++) {
                if (user.getFirstDayOfWork().isEqual(excelDataList.get(i).getDays())) {
                    firstDayIndex = i;
                }
            }
        }

        //퇴사자인 경우
        LocalDate lastDayOfWork = user.getLastDayOfWork();
        LocalDate firstMonth = excelDataList.get(0).getDays();
        LocalDate lastMonth = excelDataList.get(excelDataList.size() - 1).getDays();
        //새벽근무 확인을 위해서 다음 분기 첫날까지 근무기록이 있기 때문에 마지막 날은 빼준다. ex) 2분기(3월~6월) 시 7월 1일까지 근무기록이 있음
        int lastDayIndex = excelDataList.size() - 1;
        if (lastDayOfWork != null && lastDayOfWork.isAfter(firstMonth) && lastDayOfWork.isBefore(lastMonth)) {
            for (int i = 0; i < excelDataList.size(); i++) {
                if (lastDayOfWork.isEqual(excelDataList.get(i).getDays())) {
                    lastDayIndex = i+1;
                }
            }
        }
        return new ArrayList<>(excelDataList.subList(firstDayIndex, lastDayIndex));
    }

    private String getYear(List<ExcelData> excelDataList) {
        return String.valueOf(excelDataList.get(0).getDays().getYear());
    }

    private String quarterlyCalculation(List<ExcelData> excelDataList) {
        int month = excelDataList.get(0).getDays().getMonthValue();
        switch (month) {
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
