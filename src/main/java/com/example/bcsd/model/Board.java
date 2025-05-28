package com.example.bcsd.model;

import jakarta.persistence.*;

@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer boardId;

    @Column(name = "name", nullable = false)
    private String boardTitle;

    public Board() {}

    public Board(Integer boardId, String boardTitle) {
        this.boardId = boardId;
        if(boardTitle == null) { throw new RuntimeException(); }
        this.boardTitle = boardTitle;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        if(boardTitle == null) { throw new RuntimeException(); }
        this.boardTitle = boardTitle;
    }
}
