package com.example.bcsd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IntroduceNameController {

    @GetMapping("/introduce")
    @ResponseBody
    public String introduce(@RequestParam(name = "name") String name) {
        return "안녕하세요 제 이름은 " + name + "입니다!";
    }

}
