package com.example.bcsd;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IntroduceNameController {

    @GetMapping(value = "/introduce2", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String introduce(@RequestParam(name = "name", required = true) String name) {
        return "안녕하세요 제 이름은 " + name + "입니다!";
    }

}
