package com.example.bcsd.repository;

import com.example.bcsd.model.Board;
import com.example.bcsd.model.Article;
import com.example.bcsd.responseDto.ArticleResponseDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class BoardRepository {
    @PersistenceContext
    private EntityManager em;

    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(em.find(Board.class, id));
    }

    public List<Board> findAll() {
        return em.createQuery("SELECT b FROM Board b", Board.class)
                .getResultList();
    }

    @Transactional
    public Board save(Board board) {
        if (board.getBoardId() == null) {
            em.persist(board);
            return board;
        } else {
            return em.merge(board);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        Board b = em.find(Board.class, id);
        if (b == null) {
            throw new RuntimeException("삭제할 Board가 없음: " + id);
        }
        em.remove(b);
    }

    public String findBoardNameById(Long id) {
        Board b = em.find(Board.class, id);
        if (b == null) {
            throw new RuntimeException("존재하지 않는 Board: " + id);
        }
        return b.getBoardTitle();
    }

    public List<ArticleResponseDto> findArticlesByBoardId(Long boardId) {
        List<Article> list = em.createQuery(
                        "SELECT a FROM Article a WHERE a.boardId = :boardId", Article.class)
                .setParameter("boardId", boardId)
                .getResultList();

        return list.stream()
                .map(a -> new ArticleResponseDto(
                        a.getArticleId().longValue(),
                        a.getAuthorId().longValue(),
                        a.getBoardId(),
                        a.getTitle(),
                        a.getContent(),
                        a.getWriteDateTime(),
                        a.getModifyDateTime()
                ))
                .collect(Collectors.toList());
    }
}
