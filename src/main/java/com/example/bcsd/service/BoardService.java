package com.example.bcsd.service;

import com.example.bcsd.dto.ArticleDto;
import com.example.bcsd.dto.BoardPostsDto;
import com.example.bcsd.exception.BadRequestException;
import com.example.bcsd.exception.ResourceNotFoundException;
import com.example.bcsd.model.Board;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;

    public BoardService(BoardRepository boardRepository, ArticleRepository articleRepository) {
        this.boardRepository = boardRepository;
        this.articleRepository = articleRepository;
    }

    @Transactional(readOnly = true)
    public BoardPostsDto getPostsByBoardId(Long boardId) {
        boardRepository.findById(boardId).orElseThrow(() -> new ResourceNotFoundException("해당 게시판을 찾을 수 없음: " + boardId));

        String boardName = boardRepository.findBoardNameById(boardId);
        List<ArticleDto> articles = boardRepository.findArticlesByBoardId(boardId);

        return new BoardPostsDto(boardName, articles);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        boardRepository.findById(boardId).orElseThrow(() -> new ResourceNotFoundException("해당 게시판이 존재하지 않으므로 삭제 불가함: " + boardId));

        if (!articleRepository.findByBoardId((long) boardId.intValue()).isEmpty()) throw new BadRequestException("게시판 " + boardId + "을 지울 수 없음, 게시글이 존재함");

        boardRepository.deleteById(boardId);
    }

    @Transactional
    public void createBoard(String title) {
        if (title == null || title.isBlank()) {
            throw new BadRequestException("게시판 이름이 누락됨");
        }
        Board b = new Board(0, title);
        boardRepository.save(b);
    }

    @Transactional
    public void updateBoard(Long boardId, String newTitle) {
        if (newTitle == null || newTitle.isBlank()) {
            throw new BadRequestException("게시판 이름이 누락됨");
        }

        Board existing = boardRepository.findById(boardId).orElseThrow(() -> new ResourceNotFoundException("수정할 게시판을 찾을 수 없음: " + boardId));

        existing.setBoardTitle(newTitle);
        boardRepository.save(existing);
    }

}
