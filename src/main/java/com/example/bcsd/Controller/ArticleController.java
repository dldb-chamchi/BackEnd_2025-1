package com.example.bcsd.Controller;

import com.example.bcsd.Model.Article;
import com.example.bcsd.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService service;

    @Autowired
    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Article>> getAll() {
        return ResponseEntity.ok(service.getAllArticles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getOne(@PathVariable int id) {
        try {
            Article article = service.getArticleById(id);
            return ResponseEntity.ok(article);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Article> create(@RequestBody Article article) {
        Article created = service.createArticle(article);
        return ResponseEntity.created(URI.create("/articles/" + created.getArticleId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> update(@PathVariable int id, @RequestBody Article article) {
        try {
            Article update = service.updateArticle(id, article);
            return ResponseEntity.ok(update);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            service.getArticleById(id);
            service.deleteArticle(id);
            return ResponseEntity.noContent().build();
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
