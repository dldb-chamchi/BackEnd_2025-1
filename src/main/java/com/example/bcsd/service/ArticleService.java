package com.example.bcsd.service;

import com.example.bcsd.exception.BadRequestException;
import com.example.bcsd.exception.ResourceNotFoundException;
import com.example.bcsd.model.Article;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.BoardRepository;
import com.example.bcsd.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepo;
    private final MemberRepository memberRepo;
    private final BoardRepository boardRepo;

    public ArticleService(ArticleRepository repo, MemberRepository memberRepo, BoardRepository boardRepo) {
        this.articleRepo = repo;
        this.memberRepo = memberRepo;
        this.boardRepo  = boardRepo;
    }

    @Transactional(readOnly = true)
    public List<Article> getAllArticles() {
        return articleRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Article getArticleById(int id) {
        return articleRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("해당 아티클을 찾을 수 없음: " + id));
    }

    @Transactional
    public Article createArticle(Article a) {
        if (a.getAuthor()==null || a.getTitle()==null || a.getContent()==null) throw new BadRequestException("요청에 Null 값이 존재함");

        int authId = Integer.parseInt(a.getAuthor());
        if (!memberRepo.existsById(authId)) throw new BadRequestException("참조된 회원이 존재하지 않음: " + authId);

        if (!boardRepo.findById(a.getBoardId()).isPresent()) throw new BadRequestException("참조된 게시판이 존재하지 않음: " + a.getBoardId());

        return articleRepo.save(a);
    }

    @Transactional
    public Article updateArticle(int id, Article a) {
        Article existing = getArticleById(id);

        if (a.getAuthor() == null || a.getAuthor().isBlank()) throw new BadRequestException("author가 누락됨");

        if (a.getTitle() == null   || a.getTitle().isBlank()) throw new BadRequestException("title이 누락됨");

        if (a.getContent() == null || a.getContent().isBlank()) throw new BadRequestException("content가 누락됨");


        int authId;
        try {
            authId = Integer.parseInt(a.getAuthor());
        }
        catch (NumberFormatException e) {
            throw new BadRequestException("author는 숫자여야함: " + a.getAuthor());
        }
        if (!memberRepo.existsById(authId)) throw new BadRequestException("존재하지 않는 사용자: " + authId);


        if (!boardRepo.findById(a.getBoardId()).isPresent()) throw new BadRequestException("존재하지 않는 게시판: " + a.getBoardId());

        existing.setAuthor(a.getAuthor());
        existing.setBoardId(a.getBoardId());
        existing.setTitle(a.getTitle());
        existing.setContent(a.getContent());

        return articleRepo.update(id, existing);
    }

    @Transactional
    public void deleteArticle(int id) {
        getArticleById(id);
        articleRepo.delete(id);
    }

}
