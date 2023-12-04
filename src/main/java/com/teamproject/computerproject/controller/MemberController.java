package com.teamproject.computerproject.controller;

import com.teamproject.computerproject.dto.UserDto;
import com.teamproject.computerproject.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @PostMapping("/login")
    public String loginMember(@RequestParam("memberId") String memberId, @RequestParam("memberPw") String memberPw, HttpSession httpSession)
    {
       if(memberService.login(memberId,memberPw)){
           httpSession.setAttribute("sessionId",memberId);
           log.info(httpSession.getAttribute("sessionId"));
            return "shop/main";
       }else{
           return "redirect:/main";
       }

    }


    @PostMapping("/join")
    public String joinMember(UserDto userDto){
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(){
        return "login";
    }

    @GetMapping("/")
    public String main2(){
        return "redirect:/main";
    }


}
