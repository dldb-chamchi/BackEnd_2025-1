package com.example.bcsd.service;

import com.example.bcsd.model.Article;
import com.example.bcsd.model.Board;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.BoardRepository;
import com.example.bcsd.repository.MemberRepository;
import com.example.bcsd.requestDto.ArticleRequestDto;
import com.example.bcsd.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepo;
    private final BoardRepository boardRepo;
    private final MemberRepository memberRepo;

    @Transactional(readOnly = true)
    public List<Article> getAllArticles() {
        return articleRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Article> getArticleById(Long id) {
        return articleRepo.findById(id);
    }

    @Transactional
    public Article createArticle(ArticleRequestDto req) {
        if (req.getAuthorId() == null ||
                req.getBoardId()  == null ||
                req.getTitle()    == null ||
                req.getContent()  == null) {
            throw new BadRequestException("요청에 Null 값이 존재함");
        }

        if (!memberRepo.existsById(req.getAuthorId())) {
            throw new BadRequestException("참조된 회원이 존재하지 않음: " + req.getAuthorId());
        }
        if (boardRepo.findById(req.getBoardId()).isEmpty()) {
            throw new BadRequestException("참조된 게시판이 존재하지 않음: " + req.getBoardId());
        }
        Board b = boardRepo.findById(req.getBoardId())
                .orElseThrow(() -> new BadRequestException("게시판 없음: " + req.getBoardId()));
        Article a = new Article(req.getAuthorId(), b, req.getTitle(), req.getContent());
        b.addArticle(a);
        return a;
    }

    @Transactional
    public Optional<Article> updateArticle(Long id, ArticleRequestDto req) {
        if(req.getAuthorId() == null ||
                req.getTitle() == null ||
                req.getContent()  == null ) throw new BadRequestException("누락된 값 존재");

        return articleRepo.findById(id).map(a -> {
            a.setTitle(req.getTitle());
            a.setContent(req.getContent());
            return a;
        });
    }

    @Transactional
    public boolean deleteArticle(Long id) {
        return articleRepo.findById(id).map(a -> {
            a.getBoard().removeArticle(a);
            return true;
        }).orElse(false);
    }
}
