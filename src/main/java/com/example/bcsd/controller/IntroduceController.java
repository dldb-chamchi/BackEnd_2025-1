package com.example.bcsd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IntroduceController {

    @GetMapping(value = "/introduce", params = "!name")
    public String Introduce() {return "introduce";}

}
