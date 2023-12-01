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

    //내용 반환 하는 메서드
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

            // 가져온 데이터를 담을 list 생성
                List<String> nodes = new ArrayList<>();
                //가져올 데이터
            // parameter -> html class 이름
            //text()안의 내용
                String testResult  = conn.get().select(parameter).text();
                log.info("크롤링 결과값 : "+testResult);

                //이게 진짜 가져오는것
            //select에 class를 넣어주면 알아서 태그들을 가져온다 ex) <span class = "test">test1</span>  <span class = "test">test2</span> <span class = "test">test3</span>
                Elements result = null;
                result = conn.get().select(parameter);

            //가져온 데이터들을 foreach 로 순회후  list에 추가
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
