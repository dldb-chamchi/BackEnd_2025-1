package com.example.bcsd.controller;

import com.example.bcsd.requestDto.BoardRequestDto;
import com.example.bcsd.responseDto.BoardPostsResponseDto;
import com.example.bcsd.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Void> createBoard(@RequestBody BoardRequestDto req) {
        boardService.createBoard(req);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardPostsResponseDto> getBoard(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.getPostsByBoardId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBoard(@PathVariable("id") Long id, @RequestBody BoardRequestDto req) {
        boardService.updateBoard(id, req);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
