package com.example.bcsd.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter @Setter @NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long boardId;

    @Column(name = "name", nullable = false)
    private String boardTitle;

    //Board, Article 1:N 연관관계
    @OneToMany(
            mappedBy = "board",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )

    private List<Article> articles = new ArrayList<>();

    public Board(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    //자식 추가
    public void addArticle(Article a) {
        articles.add(a);
        a.setBoard(this);
    }

    //자식 제거
    public void removeArticle(Article a) {
        articles.remove(a);
        a.setBoard(null);
    }
}
