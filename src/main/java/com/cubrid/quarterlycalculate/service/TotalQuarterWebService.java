package com.cubrid.quarterlycalculate.service;

import com.cubrid.quarterlycalculate.model.QuarterWorkTime;
import com.cubrid.quarterlycalculate.repository.TotalQuarterCalculateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TotalQuarterWebService {

    private final TotalQuarterCalculateRepository totalQuarterCalculateRepository;

    public List<QuarterWorkTime> find(String name, String year, String quarter) {
        return totalQuarterCalculateRepository.find(name, year, quarter);
    }

    public List<QuarterWorkTime> findAll() {
        return totalQuarterCalculateRepository.findAll();
    }
}
