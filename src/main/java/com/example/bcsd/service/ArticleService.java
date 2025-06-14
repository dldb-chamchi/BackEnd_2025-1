package com.example.bcsd.service;

import com.example.bcsd.requestDto.ArticleRequestDto;
import com.example.bcsd.exception.BadRequestException;
import com.example.bcsd.exception.ResourceNotFoundException;
import com.example.bcsd.model.Article;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.BoardRepository;
import com.example.bcsd.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepo;
    private final MemberRepository memberRepo;
    private final BoardRepository boardRepo;

    public ArticleService(ArticleRepository repo,
                          MemberRepository memberRepo,
                          BoardRepository boardRepo) {
        this.articleRepo = repo;
        this.memberRepo = memberRepo;
        this.boardRepo  = boardRepo;
    }

    @Transactional(readOnly = true)
    public List<Article> getAllArticles() {
        return articleRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Article> getArticleById(Long id) {
        return articleRepo.findById(id.intValue());
    }

    @Transactional
    public Article createArticle(ArticleRequestDto req) {
        if (req.getAuthorId() == null ||
                req.getBoardId()  == null ||
                req.getTitle()    == null ||
                req.getContent()  == null) {
            throw new BadRequestException("요청에 Null 값이 존재함");
        }
        if (!memberRepo.existsById(req.getAuthorId().intValue())) {
            throw new BadRequestException("참조된 회원이 존재하지 않음: " + req.getAuthorId());
        }
        if (boardRepo.findById(req.getBoardId()).isEmpty()) {
            throw new BadRequestException("참조된 게시판이 존재하지 않음: " + req.getBoardId());
        }
        Article a = new Article();
        a.setAuthorId(req.getAuthorId().intValue());
        a.setBoardId(req.getBoardId());
        a.setTitle(req.getTitle());
        a.setContent(req.getContent());
        return articleRepo.save(a);
    }

    @Transactional
    public Optional<Article> updateArticle(Long id, ArticleRequestDto req) {
        Optional<Article> opt = getArticleById(id);
        if (opt.isEmpty()) return Optional.empty();

        if (req.getAuthorId() == null) {
            throw new BadRequestException("author가 누락됨");
        }
        if (req.getTitle() == null || req.getTitle().isBlank()) {
            throw new BadRequestException("title이 누락됨");
        }
        if (req.getContent() == null || req.getContent().isBlank()) {
            throw new BadRequestException("content가 누락됨");
        }
        if (!memberRepo.existsById(req.getAuthorId().intValue())) {
            throw new BadRequestException("존재하지 않는 사용자: " + req.getAuthorId());
        }
        if (boardRepo.findById(req.getBoardId()).isEmpty()) {
            throw new BadRequestException("존재하지 않는 게시판: " + req.getBoardId());
        }

        Article existing = opt.get();
        existing.setAuthorId(req.getAuthorId().intValue());
        existing.setBoardId(req.getBoardId());
        existing.setTitle(req.getTitle());
        existing.setContent(req.getContent());
        return Optional.of(articleRepo.update(id.intValue(), existing));
    }

    @Transactional
    public boolean deleteArticle(Long id) {
        Optional<Article> opt = getArticleById(id);
        if (opt.isEmpty()) return false;
        articleRepo.delete(id.intValue());
        return true;
    }
}
