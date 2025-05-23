package com.example.bcsd.repository;

import com.example.bcsd.model.Board;
import com.example.bcsd.dto.ArticleDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public class BoardRepository {
    private final JdbcTemplate jdbc;

    public BoardRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<Board> findById(Long id) {
        String sql = "SELECT id, name FROM board WHERE id = ?";
        List<Board> list = jdbc.query(sql, boardRowMapper(), id);
        return list.stream().findFirst();
    }

    public List<Board> findAll() {
        String sql = "SELECT id, name FROM board";
        return jdbc.query(sql, boardRowMapper());
    }

    @Transactional
    public void save(Board b) {
        if (b.getBoardId() == 0) {
            jdbc.update("INSERT INTO board(name) VALUES(?)", b.getBoardTitle());
        } else {
            jdbc.update("UPDATE board SET name = ? WHERE id = ?", b.getBoardTitle(), b.getBoardId());
        }
    }

    @Transactional
    public void deleteById(Long id) {
        jdbc.update("DELETE FROM board WHERE id = ?", id);
    }

    public String findBoardNameById(Long boardId) {
        String sql = "SELECT name FROM board WHERE id = ?";
        return jdbc.queryForObject(sql, String.class, boardId);
    }

    public List<ArticleDto> findArticlesByBoardId(Long boardId) {
        String sql = "SELECT id, title FROM article WHERE board_id = ?";
        return jdbc.query(sql, (rs, rn) ->
                new ArticleDto(rs.getLong("id"),
                        rs.getString("title")
                ), boardId
        );
    }

    private RowMapper<Board> boardRowMapper() {
        return (rs, rowNum) -> new Board(
                rs.getInt("id"),
                rs.getString("name")
        );
    }

}
