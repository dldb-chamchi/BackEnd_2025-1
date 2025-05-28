package com.example.bcsd.model;

import jakarta.persistence.*;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer articleId;

    @Column(name = "author_id", nullable = false)
    private Integer authorId;

    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_date", updatable = false)
    private String writeDateTime;

    @Column(name = "modified_date")
    private String modifyDateTime;

    public Article() {}

    public Article(Integer articleId, Integer authorId, Long boardId, String title, String content, String writeDateTime, String modifyDateTime) {
        this.articleId = articleId;
        this.authorId = authorId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.writeDateTime = writeDateTime;
        this.modifyDateTime = modifyDateTime;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new RuntimeException();
        }
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null || content.isEmpty()) {
            throw new RuntimeException();
        }
        this.content = content;
    }

    public String getWriteDateTime() {
        return writeDateTime;
    }

    public void setWriteDateTime(String writeDateTime) {
        this.writeDateTime = writeDateTime;
    }

    public String getModifyDateTime() {
        return modifyDateTime;
    }

    public void setModifyDateTime(String modifyDateTime) {
        this.modifyDateTime = modifyDateTime;
    }
}
