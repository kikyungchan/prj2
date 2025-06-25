package com.example.prj2.board.controller;

import com.example.prj2.board.dto.BoardForm;
import com.example.prj2.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

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
    public String writePost(BoardForm data) {
        boardService.add(data);

        return "redirect:/board/list";
    }

    @GetMapping("list")
    public String list(@RequestParam(defaultValue = "1") Integer page, Model model) {
        var result = boardService.list(page);
        model.addAllAttributes(result);
        return "board/list";
    }

    @GetMapping("view")
    public String view(Integer id, Model model) {
        var dto = boardService.get(id);
        model.addAttribute("board", dto);
        return "board/view";
    }

    @PostMapping("remove")
    public String remove(Integer id, RedirectAttributes rttr) {

        boardService.remove(id);
        rttr.addFlashAttribute("alert",
                Map.of("code", "danger",
                        "message", id + "번 게시글이 삭제되었습니다"));
        return "redirect:/board/list";
    }
}

