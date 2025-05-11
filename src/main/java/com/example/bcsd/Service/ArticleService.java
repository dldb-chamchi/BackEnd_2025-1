package com.example.bcsd.Service;

import com.example.bcsd.Model.Article;
import com.example.bcsd.Repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleService {
    private final ArticleRepository repository;

    @Autowired
    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public List<Article> getAllArticles() { //모든 아티클 보기
        return repository.findAll();
    }

    public Article getArticleById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(""));
    }

    public Article createArticle(Article article) {
        return repository.save(article);
    }

    public Article updateArticle(int id, Article article) {
        getArticleById(id);
        return repository.update(id, article);
    }

    public void deleteArticle(int id) {
        getArticleById(id);
        repository.delete(id);
    }

}
