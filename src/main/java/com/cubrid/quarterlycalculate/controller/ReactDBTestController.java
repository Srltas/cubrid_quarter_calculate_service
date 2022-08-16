package com.cubrid.quarterlycalculate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cubrid.quarterlycalculate.model.ReactDBTestData;
import com.cubrid.quarterlycalculate.request.ReactDBTestDto;
import com.cubrid.quarterlycalculate.service.ReactDBTestService;
import com.cubrid.quarterlycalculate.service.totalQuarterCalculate.TotalQuarterCalculateWebService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/")
@RestController
//@RequiredArgsConstructor
//@Controller
public class ReactDBTestController {

	@Autowired
	private ReactDBTestService reactDBTestService;
	
	@GetMapping("/api/login")
    public String findName(@RequestParam (required=false) String name, ReactDBTestDto reactDBTestDto, Model model) {
    	System.out.println("name : " + name);
    	
    	reactDBTestDto.setName(name);
    	
    	System.out.println("reactDBTestDto : " + reactDBTestDto.getName());
    	
    	List<ReactDBTestData> reactDBTestData = reactDBTestService.getLoginNameCompare(reactDBTestDto);

    	//model.addAttribute("loginDto", loginDto);
    	
    	if(!reactDBTestData.isEmpty()) {
    		System.out.println("loginData : " + reactDBTestData.get(0).getName());
    		
    		return reactDBTestData.get(0).getName();
    		//return reactDBTestData;
    	} else {
    		return "빈값";
    		//return null;
    	}
    }
}
