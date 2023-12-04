package com.teamproject.computerproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/user")
public class MemberContoller {
    @GetMapping("/login")
    public void get_main()
    {
        log.info("LoginContoller - get_main() called");
        //정규식으로 아이디 체크
        //정규식으로 비밀번호 체크
        //정규식으로 비밀번호 중복체크

    }
}
