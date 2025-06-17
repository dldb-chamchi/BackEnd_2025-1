package com.example.bcsd.service;

import com.example.bcsd.model.Board;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.BoardRepository;
import com.example.bcsd.requestDto.BoardRequestDto;
import com.example.bcsd.responseDto.BoardPostsResponseDto;
import com.example.bcsd.responseDto.ArticleResponseDto;
import com.example.bcsd.exception.BadRequestException;
import com.example.bcsd.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepo;
    private final ArticleRepository articleRepo;

    @Transactional(readOnly = true)
    public BoardPostsResponseDto getPostsByBoardId(Long id) {
        Board b = boardRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 게시판을 찾을 수 없음: " + id));
        return new BoardPostsResponseDto(
                b.getBoardTitle(),
                b.getArticles().stream()
                        .map(a -> new ArticleResponseDto(
                                a.getArticleId(),
                                a.getAuthorId(),
                                a.getBoard().getBoardId(),
                                a.getTitle(),
                                a.getContent(),
                                a.getWriteDateTime(),
                                a.getModifyDateTime()
                        ))
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public void createBoard(BoardRequestDto req) {
        if (req.getBoardName() == null || req.getBoardName().isBlank()) {
            throw new BadRequestException("게시판 이름이 누락됨");
        }
        boardRepo.save(new Board(req.getBoardName()));
    }

    @Transactional
    public void updateBoard(Long id, BoardRequestDto req) {
        Board b = boardRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("수정할 게시판을 찾을 수 없음: " + id));
        if (req.getBoardName() == null || req.getBoardName().isBlank()) {
            throw new BadRequestException("이름 누락");
        }
        b.setBoardTitle(req.getBoardName());
    }

    @Transactional
    public void deleteBoard(Long id) {
        Board b = boardRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("해당 게시판이 존재하지 않으므로 삭제 불가함: " + id));
        boardRepo.delete(b);
        if (!articleRepo.findByBoard_BoardId(id).isEmpty()) {
            throw new BadRequestException("게시판 " + id + "을 지울 수 없음, 게시글이 존재함");
        }
    }
}
