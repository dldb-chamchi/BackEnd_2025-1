package com.example.bcsd.Repository;

import com.example.bcsd.Model.Article;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class ArticleRepository {
    private final Map<Integer, Article> struct = new HashMap<>();
    private int idCounter = 1;

    public List<Article> findAll() {
        return new ArrayList<>(struct.values());
    }

    public Optional<Article> findById(int id) {
        return Optional.ofNullable(struct.get(id));
    }

    public Article save(Article article) {
        int id = idCounter++;
        article.setId(id);
        struct.put(id, article);
        return article;
    }

    public Article update(int id, Article article) {
        article.setId(id);
        struct.put(id, article);
        return article;
    }

    public void delete(int id) {
        struct.remove(id);
    }

}
