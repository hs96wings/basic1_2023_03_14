package com.ll.basic1;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RestController
public class HomeController {
    private int cnt;
    private List<Person> people = new ArrayList<>();

    public HomeController() {
        this.cnt = 0;
        this.people = new ArrayList<>();
    }

    @GetMapping("/home/main")
    @ResponseBody
    public String showMain() {
        return "안녕하세요";
    }

    @GetMapping("/home/main2")
    @ResponseBody
    public String showMain2() {
        return "반갑습니다";
    }

    @GetMapping("/home/main3")
    @ResponseBody
    public String showMain3() {
        return "즐거웠습니다";
    }

    @GetMapping("/home/increase")
    @ResponseBody
    public int showIncrease() {
        return ++cnt;
    }

    @GetMapping("/home/plus")
    @ResponseBody
    public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam int b) {
        return a + b;
    }

    @GetMapping("/home/addPerson")
    @ResponseBody
    public String addPerson(@RequestParam String name, int age) {
        Person p = new Person(name, age);
        people.add(p);
        return "%d번 사람이 추가되었습니다.".formatted(p.getId());
    }

    @GetMapping("/home/people")
    @ResponseBody
    public List<Person> showPeople() {
        return people;
    }

    @GetMapping("/home/removePerson")
    @ResponseBody
    public String removePerson(@RequestParam int id) {
        boolean removed = people.removeIf(person -> person.getId() == id);

        if (!removed)
            return "%d번 사람이 존재하지 않습니다".formatted(id);
        return "%d번 사람이 삭제되었습니다".formatted(id);
    }

    @GetMapping("/home/modifyPerson")
    @ResponseBody
    public String modifyPerson(@RequestParam int id, @RequestParam String name, @RequestParam int age) {
        Person found = people
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (found == null) {
            return "%d번 사람이 존재하지 않습니다".formatted(id);
        }

        found.setName(name);
        found.setAge(age);
        return "%d번 사람이 수정되었습니다".formatted(id);
    }

    @GetMapping("/home/cookie/increase")
    @ResponseBody
    public int showCookieIncrease(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int countInCookie = 0;
        if (req.getCookies() != null) {
            countInCookie = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("count"))
                    .map(cookie -> cookie.getValue())
                    .mapToInt(Integer::parseInt)
                    .findFirst()
                    .orElse(0);
        }

        res.addCookie(new Cookie("count", countInCookie + 1 + ""));

        return countInCookie;
    }
}

@AllArgsConstructor
@Getter
@Setter
@ToString
class Person {
    private static int lastId;
    private final int id;
    private String name;
    private int age;

    static {
        lastId = 0;
    }

    Person(String name, int age) {
        this(++lastId, name, age);
    }
}