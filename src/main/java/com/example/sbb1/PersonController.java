package com.example.sbb1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PersonController {
    @GetMapping("/addPerson")
    @ResponseBody
    public String showAddPerson() {
        return"";
    }

}

@Getter
@AllArgsConstructor
class Person {
    private int id;
    private String name;
    private int age;

}
