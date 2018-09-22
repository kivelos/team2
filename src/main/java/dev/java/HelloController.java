package dev.java;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class HelloController {
    @RequestMapping(value = "/hello")
    public String sayHello(ModelMap map) {
        String author = "Kseniya Piliak";
        Date time = new Date();
        map.addAttribute("author", author);
        map.addAttribute("time", time);
        return "hello";

    }
}
