package com.cubrid.quarterlycalculate.service;

import com.cubrid.quarterlycalculate.controller.excel.WorkTimeInfoDto;
import com.cubrid.quarterlycalculate.repository.QuarterWorkTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.cubrid.quarterlycalculate.util.ExcelUtil.loadExcelData;

@RequiredArgsConstructor
@Service
public class LoadService {

    private final QuarterWorkTimeService quarterWorkTimeService;

    @Transactional
    public void loadData(MultipartFile file) {
        //TODO startRowNum, columnLength를 외부 파일에서 받을 수 있도록 변경
        List<WorkTimeInfoDto> workTimeList = loadExcelData(file, 4, 26);

        quarterWorkTimeService.calculate(workTimeList);


    }
}
