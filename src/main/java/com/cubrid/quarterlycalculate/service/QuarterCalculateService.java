package com.cubrid.quarterlycalculate.service;

import com.cubrid.quarterlycalculate.service.holidayWork.HolidayWorkWebService;
import com.cubrid.quarterlycalculate.service.totalQuarterCalculate.TotalQuarterCalculateWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class QuarterCalculateService {

    private final ExcelLoadService excelLoadService;

    private final HolidayWorkWebService holidayWorkWebService;

    private final TotalQuarterCalculateWebService totalQuarterCalculateWebService;

    @Transactional
    public void calculate(MultipartFile file) {
        excelLoadService.loadData(file);

        holidayWorkWebService.calculate();

        totalQuarterCalculateWebService.calculate();
    }
}
