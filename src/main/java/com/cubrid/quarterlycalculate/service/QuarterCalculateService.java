package com.cubrid.quarterlycalculate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class QuarterCalculateService {

    private final ExcelLoadService excelLoadService;

    public void calculate(MultipartFile file) {
        excelLoadService.loadData(file);
    }
}
