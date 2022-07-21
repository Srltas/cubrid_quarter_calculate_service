package com.cubrid.quarterlycalculate.repository;

import com.cubrid.quarterlycalculate.model.ExcelData;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

import static com.cubrid.quarterlycalculate.util.DateTimeUtils.dateTimeOf;
import static com.cubrid.quarterlycalculate.util.DateTimeUtils.timestampOf;

@RequiredArgsConstructor
@Repository
public class ExcelLoadRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<ExcelData> findAll() {
        return jdbcTemplate.query("SELECT * FROM excel_data_tb", mapper);
    }

    public List<ExcelData> find(String name) {
        return jdbcTemplate.query(
                "SELECT * FROM excel_data_tb WHERE name=?",
                mapper,
                name
        );
    }

    public void save(List<ExcelData> excelData) {
        for (ExcelData workTime : excelData) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO excel_data_tb(days,day_week,name,begin_work_time,end_work_time,work_time,night_work_time,holiday_work_time,leave_time,holiday_check) VALUES (?,?,?,?,?,?,?,?,?,?)",
                        new String[]{"seq"});
                ps.setDate(1, timestampOf(workTime.getDays()));
                ps.setString(2, workTime.getDayWeek());
                ps.setString(3, workTime.getName());
                ps.setInt(4, workTime.getBeginWorkTime());
                ps.setInt(5, workTime.getEndWorkTime());
                ps.setInt(6, workTime.getWorkTime());
                ps.setInt(7, workTime.getNightWorkTime());
                ps.setInt(8, workTime.getHolidayWorkTime());
                ps.setInt(9, workTime.getLeaveTime());
                ps.setString(10, workTime.isHolidayCheck() ? "O" : "X");

                return ps;
            });
        }
    }

    static RowMapper<ExcelData> mapper = (rs, rowNum) -> ExcelData.builder()
            .seq(rs.getLong("seq"))
            .days(dateTimeOf(rs.getDate("days")))
            .dayWeek(rs.getString("day_week"))
            .name(rs.getString("name"))
            .beginWorkTime(rs.getInt("begin_work_time"))
            .endWorkTime(rs.getInt("end_work_time"))
            .workTime(rs.getInt("work_time"))
            .nightWorkTime(rs.getInt("night_work_time"))
            .holidayWorkTime(rs.getInt("holiday_work_time"))
            .leaveTime(rs.getInt("leave_time"))
            .holidayCheck(rs.getString("holiday_check").equals("O") ? true : false)
            .build();
}
