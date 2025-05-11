package com.example.bcsd.Model;

public class Board {
    private int boardId;
    private String boardTitle;

    public Board(int boardId, String boardTitle) {
        this.boardId = boardId;
        if(boardTitle == null) { throw new RuntimeException(); }
        this.boardTitle = boardTitle;
    }

    public int getBoardId() {return boardId;}
    public String getBoardTitle() {return boardTitle;}

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }
    public void setBoardTitle(String boardTitle) {
        if(boardTitle == null) { throw new RuntimeException(); }
        this.boardTitle = boardTitle;
    }
}
