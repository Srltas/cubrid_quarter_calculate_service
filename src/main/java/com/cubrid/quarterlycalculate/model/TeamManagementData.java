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

    private String name;

    private Date first_day_of_work;

    private Date last_day_of_work;
    
    private String front_first_day_of_work;
    
    private String front_last_day_of_work;

    @Builder
    public TeamManagementData(String name, Date first_day_of_work, Date last_day_of_work, String front_first_day_of_work, String front_last_day_of_work) {
        this.name = name;
        this.first_day_of_work = first_day_of_work;
        this.last_day_of_work = last_day_of_work;
        this.front_first_day_of_work = front_first_day_of_work;
        this.front_last_day_of_work = front_last_day_of_work;
    }
}
