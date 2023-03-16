package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.Rq;
import com.ll.basic1.base.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;

    private final Rq rq;

    @GetMapping("/member/login")
    @ResponseBody
    public RsData login(String username, String password) {
        if (username == null || username.trim().length() == 0) {
            return RsData.of("F-3", "username (을)를 입력해주세요");
        }

        if (password == null || password.trim().length() == 0) {
            return RsData.of("F-4", "password (을)를 입력해주세요");
        }

        RsData rsData = memberService.tryLogin(username, password);

        if (rsData.isSuccess()) {
            Member member = (Member) rsData.getData();
            rq.setSession("loginedMemberId", member.getId());
        }
        return rsData;
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout() {
        boolean cookieRemoved = rq.removeSession("loginedMemberId");

        if (!cookieRemoved)
            return RsData.of("S-2", "이미 로그아웃된 상태입니다");
        return RsData.of("S-1", "로그아웃 되었습니다");
    }

    @GetMapping("/member/me")
    @ResponseBody
    public RsData showMe() {
        long loginedMemberId = rq.getSessionAsLong("loginedMemberId", 0);

        boolean isLogined = loginedMemberId > 0;

        if (!isLogined)
            return RsData.of("F-1", "로그인 후 이용해주세요");

        Member member = memberService.findById(loginedMemberId);

        return RsData.of("S-1", "당신의 username (은)는 %s입니다".formatted(member.getUsername()));
    }

    // 디버깅용 함수
    @GetMapping("/member/session")
    @ResponseBody
    public String showSession() {
        return rq.getSessionDebugContents().replaceAll("\n", "<br>");
    }
}
