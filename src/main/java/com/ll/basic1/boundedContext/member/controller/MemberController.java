package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.RsData;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/login")
    @ResponseBody
    public RsData login(HttpServletResponse res, String username, String password) {
        if (username == null || username.trim().length() == 0) {
            return RsData.of("F-3", "username (을)를 입력해주세요");
        }

        if (password == null || password.trim().length() == 0) {
            return RsData.of("F-4", "password (을)를 입력해주세요");
        }

        return memberService.tryLogin(res, username, password);
    }

    @GetMapping("/member/me")
    @ResponseBody
    public RsData me(HttpServletRequest req, HttpServletResponse res) {
        return memberService.tryMe(req, res);
    }
}
