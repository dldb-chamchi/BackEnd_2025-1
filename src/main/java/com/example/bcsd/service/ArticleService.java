package com.example.bcsd.Service;

import com.example.bcsd.Model.Article;
import com.example.bcsd.Repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleService {
    private final ArticleRepository repository;

    @Autowired
    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Article> getAllArticles() { //모든 아티클 보기
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Article getArticleById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(""));
    }

    @Transactional
    public Article createArticle(Article article) {
        return repository.save(article);
    }

    @Transactional
    public Article updateArticle(int id, Article article) {
        getArticleById(id);
        return repository.update(id, article);
    }

    @Transactional
    public void deleteArticle(int id) {
        getArticleById(id);
        repository.delete(id);
    }

}
