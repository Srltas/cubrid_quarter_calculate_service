package com.cubrid.quarterlycalculate.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cubrid.quarterlycalculate.model.ReactDBTestData;
import com.cubrid.quarterlycalculate.model.QuarterWorkTime;
import com.cubrid.quarterlycalculate.request.ReactDBTestDto;
import com.cubrid.quarterlycalculate.request.TotalDataDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ReactDBTestRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	public List<ReactDBTestData> selectNameCompare(ReactDBTestDto reactDBTestDto) {
	    String sql = "SELECT "
	    				+ "u.name, q.year "
	    				+ "FROM "
	    				+ "users_tb u inner join quarter_work_time_tb q ON u.name=q.name";
	    List<String> conditions = new ArrayList<>();
	
	    if (reactDBTestDto.getName() != null && !reactDBTestDto.getName().equals("")) {
	        conditions.add("u.name=\'" + reactDBTestDto.getName() + "\' ");
	    }
	
	    if (conditions == null || conditions.isEmpty()) {
	        sql +=  "";
	    } else {
	        sql += " WHERE " + String.join("AND ", conditions);
	        sql += " GROUP BY q.year ORDER BY q.year desc ";
	    }
	
	    return jdbcTemplate.query(sql, mapperSelectNameCompare);
	}
	
	    static RowMapper<ReactDBTestData> mapperSelectNameCompare = (rs, rowNum) -> ReactDBTestData.builder()
	            .name(rs.getString("name"))
	            .year(rs.getString("year"))
	            .build();
	    
    
	public List<QuarterWorkTime> selectDashboardData(TotalDataDto totalDataDto) {
	    String sql = "SELECT * FROM quarter_work_time_tb";
	    List<String> conditions = new ArrayList<>();
	
	    if (totalDataDto.getName() != null && !totalDataDto.getName().equals("")) {
	        conditions.add("name=\'" + totalDataDto.getName() + "\' ");
	    }
	    
	    if (totalDataDto.getYear() != null && !totalDataDto.getYear().equals("")) {
	        conditions.add("[year]=\'" + totalDataDto.getYear() + "\' ");
	    }
	
	    if (conditions == null || conditions.isEmpty()) {
	        sql +=  "";
	    } else {
	        sql += " WHERE " + String.join("AND ", conditions);
	        sql += " ORDER BY [year] desc ";
	    }
	
	    return jdbcTemplate.query(sql, mapperSelectDashboardData);
	}
	
	    static RowMapper<QuarterWorkTime> mapperSelectDashboardData = (rs, rowNum) -> QuarterWorkTime.builder()
	            .seq(rs.getLong("seq"))
	            .name(rs.getString("name"))
	            .year(rs.getString("year"))
	            .quarter(rs.getString("quarter"))
	            .quarterTotalTime(rs.getInt("quarter_total_time"))
	            .quarterLegalTime(rs.getInt("quarter_legal_time"))
	            .quarterWorkTime(rs.getInt("quarter_work_time"))
	            .regulationWorkOverTime(rs.getInt("regulation_work_over_time"))
	            .legalWorkOverTime(rs.getInt("legal_work_over_time"))
	            .nightWorkTime(rs.getInt("night_work_time"))
	            .holidayWorkTime(rs.getInt("holiday_work_time"))
	            .holiday8HOver(rs.getInt("holiday_8H_over"))
	            .leaveTime(rs.getInt("leave_time"))
	            .compensationLeaveTime(rs.getInt("compensation_leave_time"))
	            .calculateMoney(rs.getInt("calculate_money"))
	            .calculateTotal(rs.getInt("calculate_total"))
	            .build();
	
}
