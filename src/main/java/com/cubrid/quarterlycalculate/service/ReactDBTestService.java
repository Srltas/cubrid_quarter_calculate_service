package com.cubrid.quarterlycalculate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cubrid.quarterlycalculate.model.ReactDBTestData;
import com.cubrid.quarterlycalculate.repository.ReactDBTestRepository;
import com.cubrid.quarterlycalculate.request.ReactDBTestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReactDBTestService {
	
	private final ReactDBTestRepository loginRepository;
	
	//Login시 name 비교 
	public List<ReactDBTestData> getLoginNameCompare(ReactDBTestDto reactDBTestDto) {
		return loginRepository.selectNameCompare(reactDBTestDto);
	}

}
