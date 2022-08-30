package com.cubrid.quarterlycalculate.controller;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cubrid.quarterlycalculate.model.ExcelDownloadData;
import com.cubrid.quarterlycalculate.model.QuarterWorkTime;
import com.cubrid.quarterlycalculate.model.ReactDBTestData;
import com.cubrid.quarterlycalculate.model.TeamManagementData;
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
	@PostMapping("/api/login")
	public List<ReactDBTestData> Login(@RequestBody ReactDBTestDto reactDBTestDto) {
		
    	List<ReactDBTestData> reactDBTestData = reactDBTestService.getLoginNameCompare(reactDBTestDto);

    	System.out.println("loginData : " + reactDBTestData.get(0).getName());
    	System.out.println("loginyear : " + reactDBTestData.get(0).getYear());
    		
    	return reactDBTestData;
    }
	
	//로그인 후 main 화면 출력
	@GetMapping("/api/main_dashboard")
    public List<QuarterWorkTime> MainDashboard(@RequestParam (required=false) String name, @RequestParam (required=false) String year, TotalDataDto totalDataDto) {
		
    	System.out.println("name : " + name);
    	System.out.println("year : " + year);
    	totalDataDto.setName(name);
    	totalDataDto.setYear(year);
    	System.out.println("MainDashboard1 : " + totalDataDto.getName());
    	System.out.println("MainDashboard2 : " + totalDataDto.getYear());
    	
    	List<QuarterWorkTime> reactDBTestData = reactDBTestService.getDashboardData(totalDataDto);

    	
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
		
		return reactDBTestData;
    }
	
	//관리자 - 직원 totalData 가져오기 
	@GetMapping("/api/admindashboard")
	public List<QuarterWorkTime> AdminDashboard(TotalDataDto totalDataDto) {
		
    	List<QuarterWorkTime> reactDBTestData = reactDBTestService.getAdminDashboard(totalDataDto);
    		
    	return reactDBTestData;
    }
	
	
	//관리자 - 엑셀 다운로드 Data 가져오기 
	@GetMapping("/api/exceldownload")
	public List<ExcelDownloadData> ExcelDownload(@RequestParam (required=false) String year, @RequestParam (required=false) String quarter,TotalDataDto totalDataDto) {
		
    	System.out.println("year : " + year);
    	System.out.println("quarter : " + quarter);
    	totalDataDto.setYear(year);
    	totalDataDto.setQuarter(quarter);
    	System.out.println("MainDashboard1 : " + totalDataDto.getName());
    	System.out.println("MainDashboard2 : " + totalDataDto.getQuarter());
		
    	List<ExcelDownloadData> reactDBTestData = reactDBTestService.getExcelDownload(totalDataDto);
    		
    	return reactDBTestData;
    }
	
	//관리자 - 직원 정보 가져오기 
	@GetMapping("/api/teammanagement")
	public List<TeamManagementData> TeamManagement(TotalDataDto totalDataDto) {
		
    	List<TeamManagementData> reactDBTestData = reactDBTestService.getTeamManagement(totalDataDto);
    		
    	return reactDBTestData;
    }
	
}
