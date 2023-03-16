package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.Rq;
import com.ll.basic1.base.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/login")
    @ResponseBody
    public RsData login(String username, String password, HttpServletRequest req, HttpServletResponse res) {
        if (username == null || username.trim().length() == 0) {
            return RsData.of("F-3", "username (을)를 입력해주세요");
        }

        if (password == null || password.trim().length() == 0) {
            return RsData.of("F-4", "password (을)를 입력해주세요");
        }

        RsData rsData = memberService.tryLogin(username, password);

        if (rsData.isSuccess()) {
            Member member = (Member) rsData.getData();
            Rq rq = new Rq(req, res);
            rq.setCookie("loginedMemberId", member.getId());
        }
        return rsData;
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout(HttpServletRequest req, HttpServletResponse res) {
        Rq rq = new Rq(req, res);
        boolean cookieRemoved = rq.removeCookie("loginedMemberId");

        if (!cookieRemoved)
            return RsData.of("S-2", "이미 로그아웃된 상태입니다");
        return RsData.of("S-1", "로그아웃 되었습니다");
    }

    @GetMapping("/member/me")
    @ResponseBody
    public RsData showMe(HttpServletRequest req, HttpServletResponse res) {
        Rq rq = new Rq(req, res);

        long loginedMemberId = rq.getCookieAsLonog("loginedMemberId", 0);

        boolean isLogined = loginedMemberId > 0;

        if (!isLogined)
            return RsData.of("F-1", "로그인 후 이용해주세요");

        Member member = memberService.findById(loginedMemberId);

        return RsData.of("S-1", "당신의 username (은)는 %s입니다".formatted(member.getUsername()));
    }
}
