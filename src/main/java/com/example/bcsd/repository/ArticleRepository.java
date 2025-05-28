package com.example.bcsd.repository;

import com.example.bcsd.model.Article;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class ArticleRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Article> findAll() {
        return em.createQuery("SELECT a FROM Article a", Article.class)
                .getResultList();
    }

    public Optional<Article> findById(Integer id) {
        return Optional.ofNullable(em.find(Article.class, id));
    }

    public List<Article> findByBoardId(Long boardId) {
        return em.createQuery("SELECT a FROM Article a WHERE a.boardId = :boardId", Article.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    @Transactional
    public Article save(Article article) {
        if (article.getArticleId() == null) {
            em.persist(article);
            return article;
        }
        else {
            return em.merge(article);
        }
    }

    @Transactional
    public Article update(Integer id, Article article) {
        return em.merge(article);
    }

    @Transactional
    public void delete(Integer id) {
        Article a = em.find(Article.class, id);
        if (a == null) {
            throw new RuntimeException("삭제할 Article이 없음: " + id);
        }
        em.remove(a);
    }
}
