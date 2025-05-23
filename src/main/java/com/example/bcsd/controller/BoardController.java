package com.example.bcsd.controller;

import com.example.bcsd.dto.BoardPostsDto;
import com.example.bcsd.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardPostsDto> getBoard(@PathVariable("id") Long boardId) {
        BoardPostsDto dto = boardService.getPostsByBoardId(boardId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Void> createBoard(@RequestBody BoardPostsDto req) {
        boardService.createBoard(req.getBoardName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBoard(
            @PathVariable("id") Long boardId,
            @RequestBody BoardPostsDto req
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
