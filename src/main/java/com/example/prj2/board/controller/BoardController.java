package com.example.prj2.board.controller;

import com.example.prj2.board.dto.BoardDto;
import com.example.prj2.board.dto.BoardForm;
import com.example.prj2.board.service.BoardService;
import com.example.prj2.member.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("write")
    public String writeForm(HttpSession session, RedirectAttributes rttr) {
        Object user = session.getAttribute("loggedInUser");
        if (user == null) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "warning",
                            "message", "로그인 후 글을 작성 해주세요."));
            return "redirect:/member/login";
        } else {

            return "board/write";
        }


    }

    @PostMapping("write")
    public String writePost(BoardForm data, RedirectAttributes rttr,
                            @SessionAttribute(name = "loggedInUser", required = false) MemberDto user) {

        if (user != null) {
            boardService.add(data, user);
            rttr.addFlashAttribute("alert",
                    Map.of("code", "primary",
                            "message", "새 게시물이 등록되었습니다."));


            return "redirect:/board/list";
        } else {
            return "redirect:/member/login";
        }

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

    @GetMapping("edit")
    public String edit(Integer id, Model model) {
        var dto = boardService.get(id);
        model.addAttribute("board", dto);
        return "board/edit";
    }

    @PostMapping("edit")
    public String editPost(BoardForm data, RedirectAttributes rttr) {
        boardService.update(data);
        rttr.addFlashAttribute("alert",
                Map.of("code", "success",
                        "message", data.getId() + "번 게시물이 수정되었습니다."));
        rttr.addAttribute("id", data.getId());
        return "redirect:/board/list";
    }
}

