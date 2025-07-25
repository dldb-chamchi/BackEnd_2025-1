package com.example.bcsd.repository;

import com.example.bcsd.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByBoard_BoardId(Long boardId);
    List<Article> findByAuthorId(Long authorId);
}
