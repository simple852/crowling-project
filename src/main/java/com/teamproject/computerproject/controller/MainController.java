package com.teamproject.computerproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/main")
    public String main(){
        return "login";
    }

    @GetMapping("/")
    public String main2(){
        return "redirect:/main";
    }
}
