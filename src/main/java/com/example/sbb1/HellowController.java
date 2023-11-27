package com.example.sbb1;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HellowController {
    @GetMapping("/hellow")
    @ResponseBody
    public String hellow() {
        return "안녕하세요";
    }

    @GetMapping("/")
    public String root() {
        return"redirect:/question/list";
    }
}
