package com.example.prj2.board.repository;

import com.example.prj2.board.dto.BoardListInfo;
import com.example.prj2.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<BoardListInfo> findAllBy();
}