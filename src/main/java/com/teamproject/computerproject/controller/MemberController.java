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
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @PostMapping("/login")
    public String loginMember(UserDto userDto, HttpSession httpSession)
    {
        log.info(userDto.getMemberId());
       if(memberService.login(userDto)){
           httpSession.setAttribute("sessionId",userDto);
           log.info(httpSession.getAttribute("sessionId"));
            return "shop/main";
       }else{
           return "redirect:/";
       }

    }


    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/main";
    }


    @PostMapping("/join")
    public String joinMember(@RequestParam("joinId") String memberId, @RequestParam("joinPw") String memberPw){
        UserDto userDto = UserDto.builder().memberId(memberId).memberPw(memberPw).build();
        memberService.joinMember(userDto);
        return "redirect:/main";
    }




}
