package com.example.bcsd;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private Map<Integer, Article> struct = new HashMap<>();
    private int idCounter = 1;

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable int id) {
        Article article = struct.get(id);
        if(article==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        int newId = idCounter++;
        article.setId(newId);
        struct.put(newId, article);
        return ResponseEntity.created(URI.create("/article/" + newId)).body(article);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable int id, @RequestBody Article article) {
        if(!struct.containsKey(id)){
            return ResponseEntity.notFound().build();
        }
        article.setId(id);
        struct.put(id, article);
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable int id) {
        if(!struct.containsKey(id)){
            return ResponseEntity.notFound().build();
        }
        struct.remove(id);
        return ResponseEntity.noContent().build();
    }

}
