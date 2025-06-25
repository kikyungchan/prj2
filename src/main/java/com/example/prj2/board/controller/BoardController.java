package com.example.prj2.board.controller;

import com.example.prj2.board.dto.BoardForm;
import com.example.prj2.board.entity.Board;
import com.example.prj2.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("write")
    public String writeForm() {

        return "board/write";
    }

    @PostMapping("write")
    public String writeBoard(BoardForm data) {
        boardService.add(data);

        return "board/write";
    }

    @GetMapping("list")
    public String list(Model model) {
        List<Board> list = boardService.list();
        model.addAttribute("boardList", list);
        return "board/list";
    }
}
