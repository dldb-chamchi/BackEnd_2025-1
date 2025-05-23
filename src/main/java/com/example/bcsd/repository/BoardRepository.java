package com.example.bcsd.Repository;

import com.example.bcsd.Model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class BoardRepository {
    private final JdbcTemplate jdbc;

    @Autowired
    public BoardRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private Board mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Board(
                rs.getInt("id"),
                rs.getString("name")
        );
    }

    public Optional<Board> findById(int id) {
        List<Board> list = jdbc.query(
                "SELECT * FROM board WHERE id = ?",
                this::mapRow, id
        );
        return list.stream().findFirst();
    }

}
