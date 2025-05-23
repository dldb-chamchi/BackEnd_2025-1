package com.example.bcsd.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.bcsd.Dao.BoardDao;
import com.example.bcsd.Dto.ArticleDto;
import com.example.bcsd.Dto.BoardPostsDto;

@Service

public class BoardService {

    private final BoardDao boardDao;

    @Autowired
    public BoardService(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Transactional(readOnly = true)
    public BoardPostsDto getPostsByBoardId(Long boardId) {
        String boardName = boardDao.findBoardNameById(boardId);
        List<ArticleDto> articles = boardDao.findArticlesByBoardId(boardId);
        return new BoardPostsDto(boardName, articles);
    }

}