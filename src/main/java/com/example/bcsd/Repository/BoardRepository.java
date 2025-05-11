package com.example.bcsd.Repository;

import com.example.bcsd.Model.Board;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class BoardRepository {
    private final Map<Integer, Board> struct = new HashMap<>();
    private int idCounter = 1;

    public Optional<Board> findById(int id) {
        return Optional.ofNullable(struct.get(id));
    }

    public Board save(Board board) {
        int id = idCounter++;
        board.setBoardId(id);
        struct.put(id, board);
        return board;
    }

}
