package dev.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {
    @GetMapping("/")
    public String hello(HttpServletRequest request) {
        System.out.println(request.isUserInRole("ADMIN"));
        return "index";
    }

    @GetMapping("/hello")
    public String success() {
        return "hello";
    }
}
