package com.example.prj2.member.controller;

import com.example.prj2.member.dto.MemberDto;
import com.example.prj2.member.dto.MemberForm;
import com.example.prj2.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("signup")
    public String signupForm() {

        return "member/signup";
    }

    @PostMapping("signup")
    public String signup(MemberForm data, RedirectAttributes rttr) {
        try {
            memberService.add(data);
            rttr.addFlashAttribute("alert", Map.of("code", "success", "message", "회원 가입되었습니다"));
            return "redirect:/member/login";
        } catch (DuplicateKeyException e) {
            rttr.addFlashAttribute("alert", Map.of("code", "warning", "message", e.getMessage()));
            rttr.addFlashAttribute("member", data);
            return "redirect:/member/signup";
        }
    }

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("memberList", memberService.list());
        return "member/list";
    }

    @GetMapping("view")
    public String view(Model model, String id,
                       @SessionAttribute(value = "loggedInUser", required = false)
                       MemberDto user, RedirectAttributes rttr) {
        MemberDto member = memberService.get(id);
        if (user != null) {
            if (member.getId().equals(user.getId())) {
                model.addAttribute("member", member);
                return "member/view";
            }
        }
        rttr.addFlashAttribute("alert", Map.of("code", "warning", "message", "권한이없다"));
        return "redirect:/board/list";
    }

    @GetMapping("edit")
    public String edit(Model model, String id,
                       @SessionAttribute(value = "loggedInUser", required = false)
                       MemberDto user, RedirectAttributes rttr) {
        MemberDto member = memberService.get(id);
        if (user != null) {
            if (member.getId().equals(user.getId())) {
                model.addAttribute("member", member);
                return "member/edit";
            }
        }
        rttr.addFlashAttribute("alert",
                Map.of("code", "danger", "message", "권한이없습니다"));
        return "redirect:/board/list";
    }

    @PostMapping("edit")
    public String edit(MemberForm data, RedirectAttributes rttr) {

        boolean update = memberService.update(data);
        if (update) {
            rttr.addFlashAttribute("alert", Map.of("code", "success", "message", "회원정보가 변경되었습니다."));
            rttr.addAttribute("id", data.getId());
            return "redirect:/member/edit";
        } else {
            rttr.addFlashAttribute("alert", Map.of("code", "warning", "message", "비밀번호를 다시 입력하세요."));
            rttr.addAttribute("id", data.getId());
            return "redirect:/member/edit";
        }
    }

    @PostMapping("changePw")
    public String changePw(String id, String oldPassword, String newPassword, RedirectAttributes rttr) {
        boolean result = memberService.updatePassword(id, oldPassword, newPassword);
        if (result) {
            rttr.addFlashAttribute("alert", Map.of("code", "success", "message", "암호가 변경되었습니다."));
        } else {
            rttr.addFlashAttribute("alert", Map.of("code", "danger", "message", "암호가 일치하지 않습니다."));
        }
        rttr.addAttribute("id", id);
        return "redirect:/member/edit";
    }

    @PostMapping("remove")
    public String remove(MemberForm data,
                         RedirectAttributes rttr,
                         @SessionAttribute(value = "loggedInUser", required = false)
                         MemberDto user, HttpSession httpSession) {

        boolean remove = memberService.remove(data, user);
        if (remove) {
            rttr.addFlashAttribute("alert", Map.of("code", "success", "message", "회원 탈퇴 되었습니다."));
            httpSession.invalidate();
            return "redirect:/board/list";
        } else {
            rttr.addFlashAttribute("alert", Map.of("code", "danger", "message", "암호가 일치하지 않습니다."));
            rttr.addAttribute("id", data.getId());
            return "redirect:/member/view";
        }
    }

    @GetMapping("login")
    public String loginForm() {

        return "member/login";
    }

    @PostMapping("login")
    public String login(String id, String password, HttpSession httpSession, RedirectAttributes rttr) {

        boolean login = memberService.login(id, password, httpSession);

        if (login) {
            rttr.addFlashAttribute("alert", Map.of("code", "success", "message", "로그인 되었습니다."));
            return "redirect:/board/list";

        } else {
            rttr.addFlashAttribute("alert", Map.of("code", "danger", "message", "아이디/패스워드가 일치하지 않습니다."));
            rttr.addFlashAttribute("id", id);
            return "redirect:/member/login";
        }
    }

    @RequestMapping("logout")
    public String logout(HttpSession httpSession, RedirectAttributes rttr) {
        httpSession.invalidate();
        rttr.addFlashAttribute("alert", Map.of("code", "success", "message", "로그아웃되었습니다."));

        return "redirect:/member/login";
    }
}



