package com.cubrid.quarterlycalculate.service.totalQuarterCalculate;

import com.cubrid.quarterlycalculate.model.QuarterWorkTime;
import com.cubrid.quarterlycalculate.model.User;
import com.cubrid.quarterlycalculate.repository.ExcelLoadRepository;
import com.cubrid.quarterlycalculate.repository.HolidayRepository;
import com.cubrid.quarterlycalculate.repository.TotalQuarterCalculateRepository;
import com.cubrid.quarterlycalculate.request.TotalDataDto;
import com.cubrid.quarterlycalculate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TotalQuarterCalculateWebService {

    private final TotalQuarterCalculateRepository totalQuarterCalculateRepository;

    private final TotalQuarterCalculateService totalQuarterCalculateService;

    private final UserService userService;

    private final ExcelLoadRepository excelLoadRepository;

    private final HolidayRepository holidayRepository;

    public List<QuarterWorkTime> find(TotalDataDto totalDataDto) {
        return totalQuarterCalculateRepository.find(totalDataDto);
    }

    public List<QuarterWorkTime> findAll() {
        return totalQuarterCalculateRepository.findAll();
    }

    public void calculate() {
        for (User user : userService.findAll()) {
            totalQuarterCalculateRepository.save(
                totalQuarterCalculateService.calculate(
                    user,
                    excelLoadRepository.find(user.getName()),
                    holidayRepository.find(user.getName()))
            );
        }

    }
}
