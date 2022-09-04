package com.cubrid.quarterlycalculate.repository;

import static com.cubrid.quarterlycalculate.util.DateTimeUtils.timestampOf;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cubrid.quarterlycalculate.model.ReactDBTestData;
import com.cubrid.quarterlycalculate.model.TeamManagementData;
import com.cubrid.quarterlycalculate.model.ExcelDownloadData;
import com.cubrid.quarterlycalculate.model.HolidayWorkTime;
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
	
	//Login시 name 비교
	public List<ReactDBTestData> selectNameCompare(ReactDBTestDto reactDBTestDto) {
	    String sql = "SELECT "
	    				+ " u.id, u.passwd, u.department, u.name, u.[role], u.employmentstatus, q.[year], q.quarter "
	    				+ "FROM "
	    				+ "users_tb u inner join quarter_work_time_tb q ON u.name=q.name";
	    List<String> conditions = new ArrayList<>();
	
	    if (reactDBTestDto.getId() != null && !reactDBTestDto.getId().equals("")) {
	        conditions.add("u.id=\'" + reactDBTestDto.getId() + "\' ");
	    }
	    
	    if (reactDBTestDto.getPasswd() != null && !reactDBTestDto.getPasswd().equals("")) {
	        conditions.add("u.passwd=\'" + reactDBTestDto.getPasswd() + "\' ");
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
	    		.id(rs.getString("id"))
	    		.passwd(rs.getString("passwd"))
	    		.department(rs.getString("department"))
	    		.name(rs.getString("name"))
	    		.role(rs.getString("role"))
	    		.employmentstatus(rs.getString("employmentstatus"))
	            .year(rs.getString("year"))
	            .quarter(rs.getString("quarter"))
	            .build();
	    
	//Login후 totalData 가져오기 
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

	//관리자 - 직원 totalData 가져오기     
	public List<QuarterWorkTime> selectAdminDashboard(TotalDataDto totalDataDto) {
	    String sql = "SELECT "
	    			+ " q.* "
	    			+ "FROM quarter_work_time_tb q";
	    List<String> conditions = new ArrayList<>();
	    	
	    if (totalDataDto.getDepartment() != null && !totalDataDto.getDepartment().equals("") && !totalDataDto.getDepartment().equals("ALL")) {
	    	sql += " INNER JOIN users_tb u ON q.name = u.name ";
	        conditions.add(" u.department=\'" + totalDataDto.getDepartment() + "\' ");
	    }
	
	    if (conditions == null || conditions.isEmpty()) {
	    	sql += " ORDER BY q.[year] desc, q.quarter desc";
	    } else {
	        sql += " WHERE " + String.join("AND ", conditions);
	        sql += " ORDER BY q.[year] desc, q.quarter desc";
	    }
	
	    return jdbcTemplate.query(sql, mapperselectAdminDashboardData);
	}
	
	    static RowMapper<QuarterWorkTime> mapperselectAdminDashboardData = (rs, rowNum) -> QuarterWorkTime.builder()
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

	//관리자 - 엑셀 다운로드 Data 가져오기    
	public List<ExcelDownloadData> selectExcelDownload(TotalDataDto totalDataDto) {
	    String sql = "SELECT "
	    			+ " q.name, q.[year], q.quarter, q.compensation_leave_time "
	    			+ " FROM quarter_work_time_tb q ";
	    List<String> conditions = new ArrayList<>();
	
	    if (totalDataDto.getYear() != null && !totalDataDto.getYear().equals("")) {
	        conditions.add("q.[year]=\'" + totalDataDto.getYear() + "\' ");
	    }
	    
	    if (totalDataDto.getQuarter() != null && !totalDataDto.getQuarter().equals("")) {
	        conditions.add("q.quarter=\'" + totalDataDto.getQuarter() + "\' ");
	    }
	
	    if (conditions == null || conditions.isEmpty()) {
	        sql +=  "";
	    } else {
	    	sql += " INNER JOIN users_tb u ON q.name = u.name ";
	        sql += " WHERE " + String.join("AND ", conditions);
	        sql += " AND q.compensation_leave_time / 3600 > 0";
	        sql += " AND u.employmentstatus = 'Y'";
	        sql += " ORDER BY q.name";
	    }
	
	    return jdbcTemplate.query(sql, mapperselectExcelDownloadData);
	}
	
	    static RowMapper<ExcelDownloadData> mapperselectExcelDownloadData = (rs, rowNum) -> ExcelDownloadData.builder()
	            .name(rs.getString("name"))
	            .year(rs.getString("year"))
	            .quarter(rs.getString("quarter"))
	            .compensationLeaveTime(rs.getInt("compensation_leave_time"))
	            .build();
	
	//관리자 - 직원 정보 가져오기 
	public List<TeamManagementData> selectTeamManagement(TotalDataDto totalDataDto) {
	    String sql = "SELECT "
	    			+ " * "
	    			+ " FROM users_tb";
	    List<String> conditions = new ArrayList<>();
	    
	    if (totalDataDto.getDepartment() != null && !totalDataDto.getDepartment().equals("") && !totalDataDto.getDepartment().equals("ALL")) {
	        conditions.add("department=\'" + totalDataDto.getDepartment() + "\' ");
	    }
	
	    if (conditions == null || conditions.isEmpty()) {
	    	sql += " WHERE employmentstatus = 'Y'";
	    } else {
	        sql += " WHERE " + String.join("AND ", conditions);
	        sql += " AND employmentstatus = 'Y'";
	    }
	
	    return jdbcTemplate.query(sql, mapperselectTeamManagementData);
	}
	
	    static RowMapper<TeamManagementData> mapperselectTeamManagementData = (rs, rowNum) -> TeamManagementData.builder()
	    		.id(rs.getString("id"))
	    		.department(rs.getString("department"))
	            .name(rs.getString("name"))
	            .first_day_of_work(rs.getDate("first_day_of_work"))
	            .last_day_of_work(rs.getDate("last_day_of_work"))
	            .build();
	
	//관리자 - 직원 정보 merge(insert 또는 update)
    public List<TeamManagementData> mergeTeamManagement(TeamManagementData teamManagementData) {
    	    	
    	if ( teamManagementData.isPasswdcheck() == true) {
    		jdbcTemplate.update(conn -> {
                PreparedStatement ps = 
                		conn.prepareStatement("MERGE INTO users_tb "
                							+ " USING dual ON (name = ?) "
                							+ " WHEN MATCHED THEN "
                								+ " UPDATE SET passwd = ? , department = ?, name = ?, first_day_of_work = ?, last_day_of_work = ?, employmentstatus = ? "
            								+ " WHEN NOT MATCHED THEN "
            									+ " INSERT (id, passwd, department, name, [role], first_day_of_work, last_day_of_work, employmentstatus) "
            									+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?); ");
                
                ps.setString(1, teamManagementData.getName());
                
                ps.setString(2, teamManagementData.getPasswd());
                ps.setString(3, teamManagementData.getDepartment());
                ps.setString(4, teamManagementData.getName());
                ps.setString(5, teamManagementData.getFront_first_day_of_work());
                ps.setString(6, teamManagementData.getFront_last_day_of_work());
                ps.setString(7, teamManagementData.getEmploymentstatus());
                
                ps.setString(8, teamManagementData.getId());
                ps.setString(9, teamManagementData.getPasswd());
                ps.setString(10, teamManagementData.getDepartment());
                ps.setString(11, teamManagementData.getName());
                ps.setString(12, teamManagementData.getRole());
                ps.setString(13, teamManagementData.getFront_first_day_of_work());
                ps.setString(14, teamManagementData.getFront_last_day_of_work());
                ps.setString(15, teamManagementData.getEmploymentstatus());

                return ps;
            });
	    } else {
	    	jdbcTemplate.update(conn -> {
	            PreparedStatement ps = 
	            		conn.prepareStatement("MERGE INTO users_tb "
	            							+ " USING dual ON (name = ?) "
	            							+ " WHEN MATCHED THEN "
	            								+ " UPDATE SET department = ?, name = ?, first_day_of_work = ?, last_day_of_work = ?, employmentstatus = ? "
	        								+ " WHEN NOT MATCHED THEN "
	        									+ " INSERT (id, passwd, department, name, [role], first_day_of_work, last_day_of_work, employmentstatus) "
	        									+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?); ");
	            
	            ps.setString(1, teamManagementData.getName());
	            
	            ps.setString(2, teamManagementData.getDepartment());
	            ps.setString(3, teamManagementData.getName());
	            ps.setString(4, teamManagementData.getFront_first_day_of_work());
	            ps.setString(5, teamManagementData.getFront_last_day_of_work());
	            ps.setString(6, teamManagementData.getEmploymentstatus());
	            
	            ps.setString(7, teamManagementData.getId());
	            ps.setString(8, teamManagementData.getPasswd());
	            ps.setString(9, teamManagementData.getDepartment());
	            ps.setString(10, teamManagementData.getName());
	            ps.setString(11, teamManagementData.getRole());
	            ps.setString(12, teamManagementData.getFront_first_day_of_work());
	            ps.setString(13, teamManagementData.getFront_last_day_of_work());
	            ps.setString(14, teamManagementData.getEmploymentstatus());

	            return ps;
	        });
	    }
		return null;
    }
    
    //패스워드 변경 또는 초기 pw 일 경우
    public List<ReactDBTestData> updateForgetPassword(ReactDBTestDto reactDBTestDto) {
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = 
            		conn.prepareStatement("UPDATE users_tb SET passwd = ? WHERE id = ?");
            ps.setString(1, reactDBTestDto.getPasswd());
            ps.setString(2, reactDBTestDto.getId());

            return ps;
        });
		return null;
    }
}
