package com.example.bcsd;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Json {

    public class Introduction {
        private int age;
        private String name;


        public Introduction(int age, String name) {
            if(name==null){ throw new RuntimeException();}
            this.age = age;
            this.name = name;
        }

        public String getName() {return name;}

        public int getAge() {return age;}
    }

    @GetMapping("/json")
    @ResponseBody
    public Introduction introduction() {
        return new Introduction(26, "허준기");
    }
}
