package com.example.bcsd.controller;

import com.example.bcsd.requestDto.ArticleRequestDto;
import com.example.bcsd.responseDto.ArticleResponseDto;
import com.example.bcsd.model.Article;
import com.example.bcsd.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponseDto>> getAll() {
        List<ArticleResponseDto> dtos = service.getAllArticles().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> getById(@PathVariable("id") Long id) {
        return service.getArticleById(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDto> create(@RequestBody ArticleRequestDto req) {
        Article created = service.createArticle(req);
        ArticleResponseDto dto = toResponse(created);
        return ResponseEntity
                .created(URI.create("/articles/" + dto.getId()))
                .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> update(
            @PathVariable("id") Long id,
            @RequestBody ArticleRequestDto req
    ) {
        return service.updateArticle(id, req)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (service.deleteArticle(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private ArticleResponseDto toResponse(Article a) {
        return new ArticleResponseDto(
                a.getArticleId().longValue(),
                a.getAuthorId().longValue(),
                a.getBoardId(),
                a.getTitle(),
                a.getContent(),
                a.getWriteDateTime(),
                a.getModifyDateTime()
        );
    }
}
