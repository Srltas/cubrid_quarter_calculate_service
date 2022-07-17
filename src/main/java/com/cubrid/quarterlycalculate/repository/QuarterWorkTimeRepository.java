package com.cubrid.quarterlycalculate.repository;

import com.cubrid.quarterlycalculate.model.QuarterWorkTime;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@RequiredArgsConstructor
@Repository
public class QuarterWorkTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(QuarterWorkTime quarterWorkTime) {
        String sql = "INSERT INTO total_quarterly_work_time(quarter,quarter_total_time,quarter_legal_time) VALUES (?,?,?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"seq"});
            ps.setString(1, quarterWorkTime.getQuarter());
            ps.setInt(2, quarterWorkTime.getTotalWorkTime());
            ps.setInt(3, quarterWorkTime.getLegalWorkTime());
            return ps;
        });
    }
}
