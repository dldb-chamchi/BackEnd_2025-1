package com.example.bcsd.model;

public class Article {
    private int articleId;
    private String author;
    private long boardId;
    private String title;
    private String content;
    private String writeDateTime;
    private String modifyDateTime;

    public Article() {}

    public Article(int articleId, String author, int boardId, String title, String content, String writeDateTime) {
        this.articleId = articleId;
        this.author = author;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.writeDateTime = writeDateTime;
    }

    public Article(int articleId, String author, int boardId, String title, String content, String writeDateTime, String modifyDateTime) {
        this.articleId = articleId;
        this.author = author;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.writeDateTime = writeDateTime;
        this.modifyDateTime = modifyDateTime;
    }

    public int getArticleId() {return articleId;}
    public String getAuthor() {return author;}
    public long getBoardId() {return boardId;}
    public String getTitle() {return title;}
    public String getContent() {return content;}
    public String getWriteDateTime() {return writeDateTime;}
    public String getModifyDateTime() {return modifyDateTime;}

    public void setId(int id) {this.articleId = id;}
    public void setAuthor(String author) {
        if (author == null || author.isEmpty()) {
            throw new RuntimeException();
        }
        this.author = author;
    }
    public void setBoardId(long boardId) {this.boardId = boardId;}
    public void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new RuntimeException();
        }
        this.title = title;
    }
    public void setContent(String content) {
        if (content == null || content.isEmpty()) {
            throw new RuntimeException();
        }
        this.content = content;
    }
    public void setWriteDateTime(String writeDateTime) {this.writeDateTime = writeDateTime;}
    public void setModifyDateTime(String modifyDateTime) {this.modifyDateTime = modifyDateTime;}

}
