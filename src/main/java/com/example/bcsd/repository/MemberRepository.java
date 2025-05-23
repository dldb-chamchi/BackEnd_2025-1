package com.example.bcsd.repository;

import com.example.bcsd.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbc;

    public MemberRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<Member> findById(int id) {
        String sql = "SELECT id, name, email, password FROM member WHERE id = ?";
        List<Member> list = jdbc.query(sql, memberRowMapper(), id);
        return list.stream().findFirst();
    }

    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT id, name, email, password FROM member WHERE email = ?";
        List<Member> list = jdbc.query(sql, memberRowMapper(), email);
        return list.stream().findFirst();
    }

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM member WHERE id = ?";
        Integer cnt = jdbc.queryForObject(sql, Integer.class, id);
        return cnt != null && cnt > 0;
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM member WHERE email = ?";
        Integer cnt = jdbc.queryForObject(sql, Integer.class, email);
        return cnt != null && cnt > 0;
    }

    @Transactional
    public Member save(Member m) {
        String sql = "INSERT INTO member(name, email, password) VALUES(?,?,?)";
        jdbc.update(sql, m.getName(), m.getEmail(), m.getPassword());
        Integer newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        return findById(newId).orElseThrow();
    }

    @Transactional
    public Member update(int id, Member m) {
        String sql = "UPDATE member SET name = ?, email = ?, password = ? WHERE id = ?";
        jdbc.update(sql, m.getName(), m.getEmail(), m.getPassword(), id);
        return findById(id).orElseThrow();
    }

    @Transactional
    public void deleteById(int id) {
        String sql = "DELETE FROM member WHERE id = ?";
        jdbc.update(sql, id);
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> new Member(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password")
        );
    }

}
