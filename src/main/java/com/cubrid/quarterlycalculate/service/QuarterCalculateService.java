package com.cubrid.quarterlycalculate.service;

import com.cubrid.quarterlycalculate.service.holidayWork.HolidayWorkWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class QuarterCalculateService {

    private final ExcelLoadService excelLoadService;

    private final HolidayWorkWebService holidayWorkWebService;

    private final TotalQuarterCalculateService totalQuarterCalculateService;

    @Transactional
    public void calculate(MultipartFile file) {
        excelLoadService.loadData(file);

        holidayWorkWebService.calculate();

        totalQuarterCalculateService.calculate();
    }
}
