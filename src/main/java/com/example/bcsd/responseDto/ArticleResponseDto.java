package com.example.bcsd.responseDto;

public class ArticleResponseDto {
    private Long id;
    private Long authorId;
    private Long boardId;
    private String title;
    private String content;
    private String writeDateTime;
    private String modifyDateTime;

    public ArticleResponseDto(Long id, Long authorId, Long boardId,
                              String title, String content,
                              String writeDateTime, String modifyDateTime) {
        this.id = id;
        this.authorId = authorId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.writeDateTime = writeDateTime;
        this.modifyDateTime = modifyDateTime;
    }

    public Long getId() { return id; }
    public Long getAuthorId() { return authorId; }
    public Long getBoardId() { return boardId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getWriteDateTime() { return writeDateTime; }
    public String getModifyDateTime() { return modifyDateTime; }
}
