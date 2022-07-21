package com.cubrid.quarterlycalculate.repository;

import com.cubrid.quarterlycalculate.model.HolidayWorkTime;
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
public class HolidayRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<HolidayWorkTime> findAll() {
        return jdbcTemplate.query("SELECT * FROM holiday_work_tb", mapper);
    }

    public List<HolidayWorkTime> find(String name) {
        return jdbcTemplate.query("SELECT * FROM holiday_work_tb WHERE name=?", mapper, name);
    }

    public void save(List<HolidayWorkTime> list) {
        for (HolidayWorkTime holidayWorkTime : list) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO holiday_work_tb(days,name,holiday_holiday,holiday_weekday,weekday_holiday,holiday_8H_over) VALUES (?,?,?,?,?,?)", new String[]{"seq"});
                ps.setDate(1, timestampOf(holidayWorkTime.getDays()));
                ps.setString(2, holidayWorkTime.getName());
                ps.setInt(3, holidayWorkTime.getHolidayHoliday());
                ps.setInt(4, holidayWorkTime.getHolidayWeekday());
                ps.setInt(5, holidayWorkTime.getWeekdayHoliday());
                ps.setInt(6, holidayWorkTime.getHoliday8hOver());

                return ps;
            });
        }
    }

    static RowMapper<HolidayWorkTime> mapper = (rs, rowNum) -> HolidayWorkTime.builder()
            .seq(rs.getLong("seq"))
            .days(dateTimeOf(rs.getDate("days")))
            .name(rs.getString("name"))
            .holidayHoliday(rs.getInt("holiday_holiday"))
            .holidayWeekday(rs.getInt("holiday_weekday"))
            .weekdayHoliday(rs.getInt("weekday_holiday"))
            .holiday8hOver(rs.getInt("holiday_8H_over"))
            .build();
}
