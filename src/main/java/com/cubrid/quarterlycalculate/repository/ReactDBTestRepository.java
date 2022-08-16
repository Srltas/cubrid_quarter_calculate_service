package com.cubrid.quarterlycalculate.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cubrid.quarterlycalculate.model.ReactDBTestData;
import com.cubrid.quarterlycalculate.model.QuarterWorkTime;
import com.cubrid.quarterlycalculate.request.ReactDBTestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ReactDBTestRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	public List<ReactDBTestData> selectNameCompare(ReactDBTestDto reactDBTestDto) {
	    String sql = "SELECT name FROM users_tb";
	    List<String> conditions = new ArrayList<>();
	
	    if (reactDBTestDto.getName() != null && !reactDBTestDto.getName().equals("")) {
	        conditions.add("name=\'" + reactDBTestDto.getName() + "\' ");
	    }
	
	    if (conditions == null || conditions.isEmpty()) {
	        sql +=  "";
	    } else {
	        sql += " WHERE " + String.join("AND ", conditions);
	    }
	
	    return jdbcTemplate.query(sql, mapper);
	}
	
    static RowMapper<ReactDBTestData> mapper = (rs, rowNum) -> ReactDBTestData.builder()
            .name(rs.getString("name"))
            .build();
}
