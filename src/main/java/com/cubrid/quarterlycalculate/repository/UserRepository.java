package com.cubrid.quarterlycalculate.repository;

import com.cubrid.quarterlycalculate.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cubrid.quarterlycalculate.util.DateTimeUtils.dateTimeOf;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users_tb", mapper);
    }

    public User find(String name) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM users_tb WHERE name=?",
                mapper,
                name
        );
    }

    static RowMapper<User> mapper = (rs, rowNum) -> User.builder()
            .seq(rs.getLong("seq"))
            .name(rs.getString("name"))
            .firstDayOfWork(dateTimeOf(rs.getDate("first_day_of_work")))
            .lastDayOfWork(dateTimeOf(rs.getDate("last_day_of_work")))
            .build();
}
