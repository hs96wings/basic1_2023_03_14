package com.ll.basic1.base.initData;

import com.ll.basic1.boundedContext.article.service.ArticleService;
import com.ll.basic1.boundedContext.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "test"})
// NotProd : 개발환경이 이거나, 테스트환경일 때만 실행
public class NotProd {
    @Bean
    public CommandLineRunner initData(MemberService memberService, ArticleService articleService) {
        return args -> {
            // 이 부분은 스프링부트 앱이 실행되고, 본격적으로 작동하기 전에 실행된다.
            memberService.join("user1", "1234");
            memberService.join("abc", "12345");
            memberService.join("test", "12346");
            memberService.join("love", "12347");
            memberService.join("like", "12348");
            memberService.join("giving", "12349");
            memberService.join("thanks", "123410");
            memberService.join("hello", "123411");
            memberService.join("good", "123412");
            memberService.join("peace", "123413");

            articleService.write("제목1", "내용1");
            articleService.write("제목2", "내용2");
        };
    }
}