package com.cubrid.quarterlycalculate.repository;

import com.cubrid.quarterlycalculate.model.WorkTime;
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

    public List<WorkTime> findAll() {
        return jdbcTemplate.query("SELECT * FROM excel_data_tb", mapper);
    }

    public List<WorkTime> find(Long seq) {
        return jdbcTemplate.query(
                "SELECT * FROM excel_data_tb WHERE users_seq=? ORDER BY date",
                mapper,
                seq
        );
    }

    public void save(List<WorkTime> workTimes) {
        for (WorkTime workTime : workTimes) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO excel_data_tb(days,day_week,name,begin_time,end_time,total_time,night_time,holiday_time,leave_time,holiday_check) VALUES (?,?,?,?,?,?,?,?,?,?)",
                        new String[]{"seq"});
                ps.setDate(1, timestampOf(workTime.getDays()));
                ps.setString(2, workTime.getDayWeek());
                ps.setString(3, workTime.getName());
                ps.setInt(4, workTime.getBeginTime());
                ps.setInt(5, workTime.getEndTime());
                ps.setInt(6, workTime.getTotalTime());
                ps.setInt(7, workTime.getNightTime());
                ps.setInt(8, workTime.getHolidayTime());
                ps.setInt(9, workTime.getLeaveTime());
                ps.setString(10, workTime.isHolidayCheck() ? "O" : "X");

                return ps;
            });
        }
    }

    static RowMapper<WorkTime> mapper = (rs, rowNum) -> WorkTime.builder()
            .seq(rs.getLong("seq"))
            .days(dateTimeOf(rs.getDate("days")))
            .dayWeek(rs.getString("day_week"))
            .name(rs.getString("name"))
            .beginTime(rs.getInt("begin_time"))
            .endTime(rs.getInt("end_time"))
            .totalTime(rs.getInt("total_time"))
            .nightTime(rs.getInt("night_time"))
            .holidayTime(rs.getInt("holiday_time"))
            .leaveTime(rs.getInt("leave_time"))
            .holidayCheck(rs.getString("holiday_check").equals("O") ? true : false)
            .build();
}
