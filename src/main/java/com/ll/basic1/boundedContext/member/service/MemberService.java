package com.ll.basic1.boundedContext.member.service;

import com.ll.basic1.base.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public RsData tryLogin(HttpServletResponse res, String username, String password) {
        Member member = memberRepository.findByUsername(username);

        if (member == null) {
            return RsData.of("F-2", "%s(은)는 존재하지 않는 회원입니다");
        }

        if (!member.getPassword().equals(password)) {
            return RsData.of("F-1", "비밀번호가 일치하지 않습니다");
        }

        res.addCookie(new Cookie("username", username));

        return RsData.of("S-1", "%s 님 환영합니다".formatted(username));
    }

    public RsData tryMe(HttpServletRequest req, HttpServletResponse res) {
        String username = null;

        if (req.getCookies() != null) {
            username = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("username"))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }

        if (username == null) {
            return RsData.of("F-1", "로그인 후 이용해주세요");
        }
        return RsData.of("S-1", "당신의 username(은)는 %s입니다.".formatted(username));
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}
