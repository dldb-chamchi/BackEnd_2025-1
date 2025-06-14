package com.example.bcsd.responseDto;

import java.util.List;

public class BoardPostsResponseDto {
    private String boardName;
    private List<ArticleResponseDto> articles;

    public BoardPostsResponseDto(String boardName, List<ArticleResponseDto> articles) {
        this.boardName = boardName;
        this.articles = articles;
    }

    public String getBoardName() { return boardName; }
    public List<ArticleResponseDto> getArticles() { return articles; }
}
