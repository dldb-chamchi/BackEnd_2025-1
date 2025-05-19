package com.example.bcsd.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.bcsd.Service.BoardService;
import com.example.bcsd.Dto.BoardPostsDto;

@Controller
public class PostsController {

    private final BoardService boardService;

    @Autowired
    public PostsController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/posts")
    public String posts(@RequestParam("boardId") Long boardId, Model model) {
        BoardPostsDto dto = boardService.getPostsByBoardId(boardId);
        model.addAttribute("boardName", dto.getBoardName());
        model.addAttribute("articles", dto.getArticles());
        return "posts";
    }
}