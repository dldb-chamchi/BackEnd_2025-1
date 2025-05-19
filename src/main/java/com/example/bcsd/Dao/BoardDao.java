package com.example.bcsd.Dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.bcsd.Dto.ArticleDto;

@Repository
public class BoardDao {
    private final JdbcTemplate jdbc;

    public BoardDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public String findBoardNameById(Long boardId) {
        String sql = "SELECT name FROM board WHERE id = ?";
        return jdbc.queryForObject(sql, String.class, boardId);
    }

    public List<ArticleDto> findArticlesByBoardId(Long boardId) {
        String sql = "SELECT id, title FROM article WHERE board_id = ?";
        return jdbc.query(sql,
                (rs, rowNum) -> new ArticleDto(
                        rs.getLong("id"),
                        rs.getString("title")
                ),
                boardId
        );
    }
}