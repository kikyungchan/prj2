package com.example.prj2.board.service;

import com.example.prj2.board.dto.BoardForm;
import com.example.prj2.board.dto.BoardListInfo;
import com.example.prj2.board.entity.Board;
import com.example.prj2.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public void add(BoardForm data) {

        Board board = new Board();
        board.setTitle(data.getTitle());
        board.setContent(data.getContent());
        board.setWriter(data.getWriter());

        boardRepository.save(board);

    }

    public List<BoardListInfo> list(Integer page) {
//        List<Board> list = boardRepository.findAll();
        List<BoardListInfo> boardList = boardRepository
                .findAllBy(PageRequest.of(page - 1, 10, Sort.by("id").descending()));
        return boardList;
    }
}
