package com.example.bcsd.dto;
import java.util.List;

public class BoardPostsDto {
    private String boardName;
    private List<ArticleDto> articles;

    public BoardPostsDto(String boardName, List<ArticleDto> articles) {
        this.boardName = boardName;
        this.articles = articles;
    }

    public String getBoardName() {return boardName;}
    public List<ArticleDto> getArticles() {return articles;}

}