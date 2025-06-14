package com.example.bcsd.controller;

import com.example.bcsd.requestDto.BoardRequestDto;
import com.example.bcsd.responseDto.BoardPostsResponseDto;
import com.example.bcsd.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardPostsResponseDto> getBoard(@PathVariable("id") Long boardId) {
        BoardPostsResponseDto dto = boardService.getPostsByBoardId(boardId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Void> createBoard(@RequestBody BoardRequestDto req) {
        boardService.createBoard(req.getBoardName());
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBoard(
            @PathVariable("id") Long boardId,
            @RequestBody BoardRequestDto req
    ) {
        boardService.updateBoard(boardId, req.getBoardName());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("id") Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }
}
