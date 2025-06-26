package com.example.prj2.member.controller;

import com.example.prj2.member.dto.MemberForm;
import com.example.prj2.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
            rttr.addFlashAttribute("alert",
                    Map.of("code", "success",
                            "message", "회원 가입되었습니다"));
            return "redirect:/member/list";
        } catch (DuplicateKeyException e) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "warning",
                            "message", e.getMessage()));
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
    public String view(Model model, String id) {
        model.addAttribute("member", memberService.get(id));

        return "member/view";
    }

    @GetMapping("edit")
    public String edit(Model model, String id) {
        model.addAttribute("member", memberService.get(id));
        return "member/edit";
    }

    @PostMapping("edit")
    public String edit(MemberForm data, RedirectAttributes rttr) {

        boolean update = memberService.update(data);
        if (update) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "success",
                            "message", "회원정보가 변경되었습니다."));
            rttr.addAttribute("id", data.getId());
        } else {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "warning",
                            "message", "잘못 입력하셨습니다."));
            rttr.addAttribute("id", data.getId());
        }
        return "redirect:/member/list";
    }

    @PostMapping("changePw")
    public String changePw(String id,
                           String oldPassword,
                           String newPassword,
                           RedirectAttributes rttr) {
        boolean result = memberService.updatePassword(id, oldPassword, newPassword);
        if (result) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "success",
                            "message", "암호가 변경되었습니다."));
        } else {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "danger",
                            "message", "암호가 일치하지 않습니다."));
        }
        rttr.addAttribute("id", id);
        return "redirect:/member/edit";
    }

    @PostMapping("remove")
    public String remove(String id, String password, RedirectAttributes rttr) {

        boolean remove = memberService.remove(id, password);
        if (remove) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "success",
                            "message", "회원 탈퇴 되었습니다."));
            return "redirect:/member/list";
        } else {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "danger",
                            "message", "암호가 일치하지 않습니다."));
            rttr.addAttribute("id", id);
            return "redirect:/member/view";
        }
    }
}



