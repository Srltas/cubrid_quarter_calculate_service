package com.cubrid.quarterlycalculate.repository;

import com.cubrid.quarterlycalculate.model.QuarterWorkTime;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class TotalQuarterCalculateRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(List<QuarterWorkTime> quarterWorkTimes) {
        String sql = "INSERT INTO quarter_work_time_tb" +
                "(name" +
                ",quarter" +
                ",quarter_total_time" +
                ",quarter_legal_time" +
                ",quarter_work_time" +
                ",regulation_work_over_time" +
                ",legal_work_over_time" +
                ",night_work_time" +
                ",holiday_work_time" +
                ",holiday_8H_over" +
                ",leave_time" +
                ",compensation_leave_time" +
                ",calculate_money" +
                ",calculate_total) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        for (QuarterWorkTime quarterWorkTime : quarterWorkTimes) {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"seq"});
                ps.setString(1, quarterWorkTime.getName());
                ps.setString(2, quarterWorkTime.getQuarter());
                ps.setInt(3, quarterWorkTime.getQuarterTotalTime());
                ps.setInt(4, quarterWorkTime.getQuarterLegalTime());
                ps.setInt(5, quarterWorkTime.getQuarterWorkTime());
                ps.setInt(6, quarterWorkTime.getRegulationWorkOverTime());
                ps.setInt(7, quarterWorkTime.getLegalWorkOverTime());
                ps.setInt(8, quarterWorkTime.getNightWorkTime());
                ps.setInt(9, quarterWorkTime.getHolidayWorkTime());
                ps.setInt(10, quarterWorkTime.getHoliday8HOver());
                ps.setInt(11, quarterWorkTime.getLeaveTime());
                ps.setInt(12, quarterWorkTime.getCompensationLeaveTime());
                ps.setInt(13, quarterWorkTime.getCalculateMoney());
                ps.setInt(14, quarterWorkTime.getCalculateTotal());
                return ps;
            });
        }
    }
}
