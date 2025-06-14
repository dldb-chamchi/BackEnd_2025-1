package com.example.bcsd.controller;

import com.example.bcsd.responseDto.BoardPostsResponseDto;
import com.example.bcsd.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostsController {
    private final BoardService boardService;

    public PostsController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/posts")
    public String posts(@RequestParam("boardId") Long boardId, Model model) {
        BoardPostsResponseDto dto = boardService.getPostsByBoardId(boardId);
        model.addAttribute("boardName", dto.getBoardName());
        model.addAttribute("articles", dto.getArticles());
        return "posts";
    }
}
