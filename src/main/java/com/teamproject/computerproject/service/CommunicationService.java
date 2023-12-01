package com.teamproject.computerproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommunicationService {





    public List<String> getDatas(String url, String parameter){

//        log.info(getJsoupElements(null,url,parameter).toString());
        return  getJsoupElements(null,url,parameter);
    }




    //jsoup 크롤링 커넥션 반환
    // 가져올 웹페이지 url 전달
    public Connection getJsoupConnection(String url){
        return Jsoup.connect(url);

    }


    //검색할 element와 url을 가져온다.
    public List<String> getJsoupElements(Connection connection, String url, String parameter){
        Connection conn = null;

        // ObjectUtils 객체 null 체크하는 클래스
        if(!ObjectUtils.isEmpty(connection)){
            conn = connection;
        }else{
            conn =getJsoupConnection(url);
        }
        try{
                List<String> nodes = new ArrayList<>();
                String testResult  = conn.get().select(parameter).text();
                log.info("크롤링 결과값 : "+testResult);
                Elements result = null;
                result = conn.get().select(parameter);

            for(Element data : result){
                nodes.add(data.text());
            }

            return nodes;

        }catch (Exception e){
            log.info("크롤링 에러"+e.getMessage());
            return null;
        }
    }



}
