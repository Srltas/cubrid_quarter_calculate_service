package com.cubrid.quarterlycalculate.repository;

import com.cubrid.quarterlycalculate.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT seq, id FROM users_tb", mapper);
    }

    static RowMapper<User> mapper = (rs, rowNum) -> User.builder()
            .seq(rs.getLong("seq"))
            .id(rs.getString("id"))
            .build();
}
