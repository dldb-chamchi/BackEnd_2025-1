package com.example.bcsd.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "article")
@Getter @Setter @NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long articleId;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    //Board, Article 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_date", updatable = false)
    private String writeDateTime;

    @Column(name = "modified_date")
    private String modifyDateTime;

    public Article(Long authorId, Board board, String title, String content) {
        this.authorId = authorId;
        this.board = board;
        this.title = title;
        this.content = content;
    }
}
