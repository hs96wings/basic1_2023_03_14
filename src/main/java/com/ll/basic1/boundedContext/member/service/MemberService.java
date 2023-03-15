package com.ll.basic1.boundedContext.member.service;

import com.ll.basic1.base.RsData;

public class MemberService {
    public RsData tryLogin(String username, String password) {
        if (!password.equals("1234"))
            return RsData.of("F-1", "비밀번호가 일치하지 않습니다");
        else if (!username.equals("user1"))
            return RsData.of("F-2", "%s(은)는 존재하지 않는 회원입니다".formatted(username));

        return RsData.of("S-1", "%d님 환영합니다".formatted(username));
    }
}
