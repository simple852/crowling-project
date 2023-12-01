package com.teamproject.computerproject.controller;


import com.teamproject.computerproject.service.CommunicationService;
import lombok.RequiredArgsConstructor;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/data")
public class DataController {
    private final CommunicationService communicationService;

    @GetMapping("/jsoup")
    public List<String> test(){

        //가져올 상품주소
        String url = "https://prod.danawa.com/info/?pcode=19627808&cate=112747&adinflow=Y";
        //상품 주소 html소스안의 css class 이름
        String parameter = "prc_c";
        return  communicationService.getDatas(url, parameter);


    }


}
