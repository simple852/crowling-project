package com.teamproject.computerproject.service;


import com.teamproject.computerproject.controller.DataController;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class CommunicationTest {

    @Autowired
    private  DataController dataController;
    @Autowired
    private  CommunicationService communicationService;


    @Test
    @DisplayName("크롤링 테스트")
    public void test() {

        String url = "https://prod.danawa.com/info/?pcode=19627808&cate=112747&adinflow=Y";
//        String parameter = "span.title"; //단일 상품 이름 가져오는 파라미터
        String parameter = "em.prc_c";
        List<String> result =  communicationService.getDatas(url,parameter);


//        log.info(result.substring(18,25));
        log.info(result.toString());
    }
}
