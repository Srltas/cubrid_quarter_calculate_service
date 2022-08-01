package com.cubrid.quarterlycalculate.repository;

import com.cubrid.quarterlycalculate.model.QuarterWorkTime;
import com.cubrid.quarterlycalculate.request.TotalDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class TotalQuarterCalculateRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<QuarterWorkTime> find(TotalDataDto totalDataDto) {
        String sql = "SELECT * FROM quarter_work_time_tb";
        List<String> conditions = new ArrayList<>();

        if (totalDataDto.getName() != null && !totalDataDto.getName().equals("")) {
            conditions.add("name=\'" + totalDataDto.getName() + "\' ");
        }

        if (totalDataDto.getYear() != null && !totalDataDto.getYear().equals("")) {
            conditions.add("`year`=\'" + totalDataDto.getYear() + "\' ");
        }

        if (totalDataDto.getQuarter() != null && !totalDataDto.getQuarter().equals("")) {
            conditions.add("quarter=\'" + totalDataDto.getQuarter() + "\' ");
        }

        if (conditions == null || conditions.isEmpty()) {
            sql +=  "";
        } else {
            sql += " WHERE " + String.join("AND ", conditions);
        }

        return jdbcTemplate.query(sql, mapper);
    }

    public List<QuarterWorkTime> findAll() {
        return jdbcTemplate.query("SELECT * FROM quarter_work_time_tb", mapper);
    }

    public void save(List<QuarterWorkTime> quarterWorkTimes) {
        String sql = "INSERT INTO quarter_work_time_tb" +
                "(name" +
                ",`year`" +
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
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        for (QuarterWorkTime quarterWorkTime : quarterWorkTimes) {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"seq"});
                ps.setString(1, quarterWorkTime.getName());
                ps.setString(2, quarterWorkTime.getYear());
                ps.setString(3, quarterWorkTime.getQuarter());
                ps.setInt(4, quarterWorkTime.getQuarterTotalTime());
                ps.setInt(5, quarterWorkTime.getQuarterLegalTime());
                ps.setInt(6, quarterWorkTime.getQuarterWorkTime());
                ps.setInt(7, quarterWorkTime.getRegulationWorkOverTime());
                ps.setInt(8, quarterWorkTime.getLegalWorkOverTime());
                ps.setInt(9, quarterWorkTime.getNightWorkTime());
                ps.setInt(10, quarterWorkTime.getHolidayWorkTime());
                ps.setInt(11, quarterWorkTime.getHoliday8HOver());
                ps.setInt(12, quarterWorkTime.getLeaveTime());
                ps.setInt(13, quarterWorkTime.getCompensationLeaveTime());
                ps.setInt(14, quarterWorkTime.getCalculateMoney());
                ps.setInt(15, quarterWorkTime.getCalculateTotal());
                return ps;
            });
        }
    }

    static RowMapper<QuarterWorkTime> mapper = (rs, rowNum) -> QuarterWorkTime.builder()
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
