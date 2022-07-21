package com.cubrid.quarterlycalculate.service;

import com.cubrid.quarterlycalculate.model.ExcelData;
import com.cubrid.quarterlycalculate.repository.ExcelLoadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.cubrid.quarterlycalculate.util.ExcelUtil.loadExcelData;

@RequiredArgsConstructor
@Service
public class ExcelLoadService {

    private final ExcelLoadRepository excelLoadRepository;

    @Transactional
    public void loadData(MultipartFile file) {
        //TODO startRowNum, columnLength를 외부 파일에서 받을 수 있도록 변경
        excelLoadRepository.save(loadExcelData(file, 4, 26).stream()
                .map(ExcelData::new)
                .collect(Collectors.toList()));
    }

    public List<ExcelData> find(String name) {
        return excelLoadRepository.find(name);
    }

    public List<ExcelData> findAll() {
        return excelLoadRepository.findAll();
    }
}
