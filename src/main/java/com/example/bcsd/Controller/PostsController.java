package com.example.bcsd.Controller;

import com.example.bcsd.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostsController {
    private final ArticleService service;

    @Autowired
    public PostsController(ArticleService service) {
        this.service = service;
    }

    @GetMapping("/posts")
    public String listPosts(Model model) {
        model.addAttribute("boardName", "자유게시판");
        model.addAttribute("articles", service.getAllArticles());
        return "posts";
    }

}
