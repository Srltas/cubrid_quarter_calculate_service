package com.cubrid.quarterlycalculate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cubrid.quarterlycalculate.model.QuarterWorkTime;
import com.cubrid.quarterlycalculate.model.ReactDBTestData;
import com.cubrid.quarterlycalculate.repository.ReactDBTestRepository;
import com.cubrid.quarterlycalculate.request.ReactDBTestDto;
import com.cubrid.quarterlycalculate.request.TotalDataDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReactDBTestService {
	
	private final ReactDBTestRepository loginRepository;
	
	//Login시 name 비교 
	public List<ReactDBTestData> getLoginNameCompare(ReactDBTestDto reactDBTestDto) {
		return loginRepository.selectNameCompare(reactDBTestDto);
	}
	
	//Login후 totalData 가져오기 
	public List<QuarterWorkTime> getDashboardData(TotalDataDto totalDataDto) {
		return loginRepository.selectDashboardData(totalDataDto);
	}
	
	//관리자 - 직원 totalData 가져오기 
	public List<QuarterWorkTime> getAdminDashboard(TotalDataDto totalDataDto) {
		return loginRepository.selectAdminDashboard(totalDataDto);
	}

}
