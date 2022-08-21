package com.cubrid.quarterlycalculate.controller;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cubrid.quarterlycalculate.model.QuarterWorkTime;
import com.cubrid.quarterlycalculate.model.ReactDBTestData;
import com.cubrid.quarterlycalculate.request.ReactDBTestDto;
import com.cubrid.quarterlycalculate.request.TotalDataDto;
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
	
	//로그인 시 user 값 비교
	@GetMapping("/api/login")
    public List<ReactDBTestData> findName(@RequestParam (required=false) String name, ReactDBTestDto reactDBTestDto, Model model) {
		
    	System.out.println("name : " + name);
    	reactDBTestDto.setName(name);
    	System.out.println("reactDBTestDto : " + reactDBTestDto.getName());
    	
    	List<ReactDBTestData> reactDBTestData = reactDBTestService.getLoginNameCompare(reactDBTestDto);

    	//model.addAttribute("loginDto", loginDto);
    	
    	//if(!reactDBTestData.isEmpty()) {
    		System.out.println("loginData : " + reactDBTestData.get(0).getName());
    		System.out.println("loginyear : " + reactDBTestData.get(0).getYear());
    		
    		//return reactDBTestData.get(0).getName();
    		return reactDBTestData;
    	//} else {
    		//return "빈값";
    		//return null;
    	//}
    }
	
	//로그인 후 main 화면 출력
	@GetMapping("/api/main_dashboard")
    public List<QuarterWorkTime> MainDashboard(@RequestParam (required=false) String name, @RequestParam (required=false) String year, TotalDataDto totalDataDto, Model model) {
		
    	System.out.println("name : " + name);
    	System.out.println("year : " + year);
    	totalDataDto.setName(name);
    	totalDataDto.setYear(year);
    	System.out.println("MainDashboard1 : " + totalDataDto.getName());
    	System.out.println("MainDashboard2 : " + totalDataDto.getYear());
    	
    	List<QuarterWorkTime> reactDBTestData = reactDBTestService.getDashboardData(totalDataDto);

    	//model.addAttribute("loginDto", loginDto);
    	
    	for(int i=0; i<reactDBTestData.size(); i++) {
    		
    		System.out.println("name[" + i + "] : " + reactDBTestData.get(i).getName());
    		System.out.println("year[" + i + "] : " + reactDBTestData.get(i).getYear());
    		System.out.println("quarter[" + i + "] : " + reactDBTestData.get(i).getQuarter());
    		System.out.println("quarter_total_time[" + i + "] : " + reactDBTestData.get(i).getQuarterTotalTime());
    		System.out.println("quarter_legal_time[" + i + "] : " + reactDBTestData.get(i).getQuarterLegalTime());
    		System.out.println("quarter_work_time[" + i + "] : " + reactDBTestData.get(i).getQuarterWorkTime());
    		System.out.println("regulation_work_over_time[" + i + "] : " + reactDBTestData.get(i).getRegulationWorkOverTime());
    		System.out.println("legal_work_over_time[" + i + "] : " + reactDBTestData.get(i).getLegalWorkOverTime());
    		System.out.println("night_work_time[" + i + "] : " + reactDBTestData.get(i).getNightWorkTime());
    		System.out.println("holiday_work_time[" + i + "] : " + reactDBTestData.get(i).getHolidayWorkTime());
    		System.out.println("holiday_8h_over[" + i + "] : " + reactDBTestData.get(i).getHoliday8HOver());
    		System.out.println("leave_time[" + i + "] : " + reactDBTestData.get(i).getLeaveTime());
    		System.out.println("compensation_leave_time[" + i + "] : " + reactDBTestData.get(i).getCompensationLeaveTime());
    		System.out.println("calculate_money[" + i + "] : " + reactDBTestData.get(i).getCalculateMoney());
    		System.out.println("calculate_total[" + i + "] : " + reactDBTestData.get(i).getCalculateTotal());
    	}
		
		//return reactDBTestData.get(0).getName();
		return reactDBTestData;
    }
}