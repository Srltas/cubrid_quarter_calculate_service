package com.cubrid.quarterlycalculate.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    	
    	if(reactDBTestData.size() == 0) {
    		return null;
    	} else {
    		return reactDBTestData;
    	}
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

    	/*
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
    	*/
		
		return reactDBTestData;
    }
	
	//관리자 - 직원 totalData 가져오기 
	@GetMapping("/api/admindashboard")
	public List<QuarterWorkTime> AdminDashboard(@RequestParam (required=false) String department, TotalDataDto totalDataDto) {
		
		System.out.println("department : " + department);
		totalDataDto.setDepartment(department);
    	List<QuarterWorkTime> reactDBTestData = reactDBTestService.getAdminDashboard(totalDataDto);
    		
    	return reactDBTestData;
    }
	
	
	//관리자 - 엑셀 다운로드 Data 가져오기 
	@GetMapping("/api/exceldownload")
	public List<ExcelDownloadData> ExcelDownload(@RequestParam (required=false) String year, @RequestParam (required=false) String quarter,TotalDataDto totalDataDto) {
		
    	totalDataDto.setYear(year);
    	totalDataDto.setQuarter(quarter);
    	//System.out.println("MainDashboard1 : " + totalDataDto.getName());
    	//System.out.println("MainDashboard2 : " + totalDataDto.getQuarter());
		
    	List<ExcelDownloadData> reactDBTestData = reactDBTestService.getExcelDownload(totalDataDto);
    		
    	return reactDBTestData;
    }
	
	//관리자 - 직원 정보 가져오기, totalDataDto 필요 없을지도?
	@GetMapping("/api/teammanagement")
	public List<TeamManagementData> TeamManagement(TotalDataDto totalDataDto) {
		
    	List<TeamManagementData> reactDBTestData = reactDBTestService.getTeamManagement(totalDataDto);
    		
    	return reactDBTestData;
    }
	
	//관리자 - 직원 정보 merge(insert 또는 update), 그런데 요기는 값을 반환 받을게 없지 안나???
	@GetMapping("/api/teammanagement/merge")
	public List<TeamManagementData> TeamManagementMerge(@RequestParam (required=false) String id, @RequestParam (required=false) String department, @RequestParam (required=false) String name, @RequestParam (required=false) String front_first_day_of_work, @RequestParam (required=false) String front_last_day_of_work, TeamManagementData teamManagementData) {
    	
		teamManagementData.setPasswd("0000");
		teamManagementData.setRole("user");
		
    	if(front_last_day_of_work.substring(0, 4).equals("9999")) {
    		front_last_day_of_work = null;
    		teamManagementData.setEmploymentstatus("Y");
    	} else {
    		teamManagementData.setEmploymentstatus("N");
    	}
    	
    	System.out.println("name : " + name);
    	System.out.println("front_first_day_of_work : " + front_first_day_of_work);
    	System.out.println("front_last_day_of_work : " + front_last_day_of_work);
    	
    	teamManagementData.setId(id);
    	teamManagementData.setDepartment(department);
		teamManagementData.setName(name);
		teamManagementData.setFront_first_day_of_work(front_first_day_of_work);
		teamManagementData.setFront_last_day_of_work(front_last_day_of_work);
		
		System.out.println("name2 : " + teamManagementData.getName());
    	System.out.println("front_first_day_of_work2 : " + teamManagementData.getFront_first_day_of_work());
    	System.out.println("front_last_day_of_work2 : " + teamManagementData.getFront_last_day_of_work());
	    	
		
		List<TeamManagementData> reactDBTestData = reactDBTestService.setTeamManagementMerge(teamManagementData);
    		
    	return reactDBTestData;
    }
	
	//패스워드 변경 또는 초기 pw 일 경우 
	@PostMapping("/api/forgetpassword")
	public List<ReactDBTestData> ForgetPassword(@RequestBody ReactDBTestDto reactDBTestDto) {
		
		System.out.println("id : " + reactDBTestDto.getId());
    	System.out.println("pw : " + reactDBTestDto.getPasswd());
		
    	List<ReactDBTestData> reactDBTestData = reactDBTestService.setForgetPassword(reactDBTestDto);
    	
    	return reactDBTestData;
    	//return null;
    }
	
}
