package com.cubrid.quarterlycalculate.service.holidayWork;

import com.cubrid.quarterlycalculate.model.HolidayWorkTime;
import com.cubrid.quarterlycalculate.model.User;
import com.cubrid.quarterlycalculate.repository.ExcelLoadRepository;
import com.cubrid.quarterlycalculate.repository.HolidayRepository;
import com.cubrid.quarterlycalculate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HolidayWorkWebService {

    private final HolidayRepository holidayRepository;

    private final HolidayWorkService holidayWorkService;

    private final UserRepository userRepository;

    private final ExcelLoadRepository excelLoadRepository;

    public List<HolidayWorkTime> find(String name) {
        return holidayRepository.find(name);
    }

    public void calculate() {
        holidayRepository.truncate();

        List<User> users = userRepository.findAll();

        for (User user : users) {
            holidayRepository.save(
                    holidayWorkService.calculate(
                            excelLoadRepository.find(user.getName())));
        }

    }
}
