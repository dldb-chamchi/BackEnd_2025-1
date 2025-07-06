package com.example.bcsd.controller;

import com.example.bcsd.requestDto.ArticleRequestDto;
import com.example.bcsd.responseDto.ArticleResponseDto;
import com.example.bcsd.model.Article;
import com.example.bcsd.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService service;

    //github 실험 주석
    //github 실험 주석2

    @GetMapping
    public ResponseEntity<List<ArticleResponseDto>> getAll() {
        List<ArticleResponseDto> dtos = service.getAllArticles().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> getById(@PathVariable Long id) {
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
            @PathVariable Long id,
            @RequestBody ArticleRequestDto req
    ) {
        return service.updateArticle(id, req)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.deleteArticle(id)
                ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    private ArticleResponseDto toResponse(Article a) {
        return new ArticleResponseDto(
                a.getArticleId(),
                a.getAuthorId(),
                a.getBoard().getBoardId(),
                a.getTitle(),
                a.getContent(),
                a.getWriteDateTime(),
                a.getModifyDateTime()
        );
    }
}
