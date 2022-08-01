package com.cubrid.quarterlycalculate.service;

import com.cubrid.quarterlycalculate.model.QuarterWorkTime;
import com.cubrid.quarterlycalculate.repository.TotalQuarterCalculateRepository;
import com.cubrid.quarterlycalculate.request.TotalDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TotalQuarterWebService {

    private final TotalQuarterCalculateRepository totalQuarterCalculateRepository;

    public List<QuarterWorkTime> find(TotalDataDto totalDataDto) {
        return totalQuarterCalculateRepository.find(totalDataDto);
    }

    public List<QuarterWorkTime> findAll() {
        return totalQuarterCalculateRepository.findAll();
    }
}
