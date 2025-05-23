package com.example.bcsd.repository;

import com.example.bcsd.model.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {
    private final JdbcTemplate jdbc;

    public ArticleRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Article> findAll() {
        String sql = "SELECT id, board_id, author_id, title, content, created_date, modified_date FROM article";
        return jdbc.query(sql, (rs, rn) -> {
            Article a = new Article();
            a.setId(rs.getInt("id"));
            a.setBoardId(rs.getInt("board_id"));
            a.setAuthor(String.valueOf(rs.getInt("author_id")));
            a.setTitle(rs.getString("title"));
            a.setContent(rs.getString("content"));
            a.setWriteDateTime(rs.getString("created_date"));
            a.setModifyDateTime(rs.getString("modified_date"));
            return a;
        });
    }

    public Optional<Article> findById(int id) {
        String sql = "SELECT id, board_id, author_id, title, content, created_date, modified_date FROM article WHERE id = ?";
        return jdbc.query(sql, (rs, rn) -> {
            Article a = new Article();
            a.setId(rs.getInt("id"));
            a.setBoardId(rs.getInt("board_id"));
            a.setAuthor(String.valueOf(rs.getInt("author_id")));
            a.setTitle(rs.getString("title"));
            a.setContent(rs.getString("content"));
            a.setWriteDateTime(rs.getString("created_date"));
            a.setModifyDateTime(rs.getString("modified_date"));
            return a;
        }, id).stream().findFirst();
    }

    public List<Article> findByBoardId(int boardId) {
        String sql = "SELECT id, board_id, author_id, title, content, created_date, modified_date FROM article WHERE board_id = ?";
        return jdbc.query(sql, (rs, rn) -> {
            Article a = new Article();
            a.setId(rs.getInt("id"));
            a.setBoardId(rs.getInt("board_id"));
            a.setAuthor(String.valueOf(rs.getInt("author_id")));
            a.setTitle(rs.getString("title"));
            a.setContent(rs.getString("content"));
            a.setWriteDateTime(rs.getString("created_date"));
            a.setModifyDateTime(rs.getString("modified_date"));
            return a;
        }, boardId);
    }

    public Article save(Article a) {
        jdbc.update(
                "INSERT INTO article(author_id, board_id, title, content) VALUES (?,?,?,?)",
                Integer.parseInt(a.getAuthor()),  // author 필드에 ID 문자열이 담긴 경우
                a.getBoardId(),
                a.getTitle(),
                a.getContent()
        );
        Integer newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        return findById(newId).orElseThrow();
    }

    @Transactional
    public Article update(int id, Article a) {
        jdbc.update(
                "UPDATE article SET title = ?, content = ? WHERE id = ?",
                a.getTitle(), a.getContent(), id
        );
        return findById(id).orElseThrow();
    }

    @Transactional
    public void delete(int id) {
        jdbc.update("DELETE FROM article WHERE id = ?", id);
    }

}
