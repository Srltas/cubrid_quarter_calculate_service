package com.cubrid.quarterlycalculate.model;

import java.util.Date;

import com.cubrid.quarterlycalculate.request.TotalDataDto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class TeamManagementData {
	
	private String id;
	
	private String passwd;
	
	private String department;
	
    private String name;
    
    private String role;

    private Date first_day_of_work;

    private Date last_day_of_work;
    
    private String front_first_day_of_work;
    
    private String front_last_day_of_work;
    
    private String employmentstatus;

    @Builder
    public TeamManagementData(
    		String id, String passwd, String department, String name, String role, Date first_day_of_work, Date last_day_of_work, String front_first_day_of_work, String front_last_day_of_work , String employmentstatus
    ) {
    	this.id = id;
    	this.passwd = passwd;
    	this.department = department;
        this.name = name;
        this.role = role;
        this.first_day_of_work = first_day_of_work;
        this.last_day_of_work = last_day_of_work;
        this.front_first_day_of_work = front_first_day_of_work;
        this.front_last_day_of_work = front_last_day_of_work;
        this.employmentstatus = employmentstatus;
    }
}
