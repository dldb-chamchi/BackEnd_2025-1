package com.example.bcsd.Service;

import com.example.bcsd.Model.Board;
import com.example.bcsd.Repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board getBoardById(int id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(""));
    }

}
