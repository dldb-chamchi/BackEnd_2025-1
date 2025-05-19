package com.example.bcsd.Dto;

public class ArticleDto {
    private Long id;
    private String title;

    public ArticleDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {return id;}

    public String getTitle() {return title;}

}