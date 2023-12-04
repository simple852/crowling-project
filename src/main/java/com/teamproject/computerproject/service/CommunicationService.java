package com.teamproject.computerproject.service;

import com.teamproject.computerproject.domain.BackupDatum;
import com.teamproject.computerproject.domain.Item;
import com.teamproject.computerproject.domain.ItemImage;
import com.teamproject.computerproject.dto.BackupDatumDto;
import com.teamproject.computerproject.dto.ItemDto;
import com.teamproject.computerproject.dto.request.ParameterDto;
import com.teamproject.computerproject.repositery.BackupDatumRepository;
import com.teamproject.computerproject.repositery.CategoryRepository;
import com.teamproject.computerproject.repositery.ItemImageRepository;
import com.teamproject.computerproject.repositery.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class CommunicationService {
    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final CategoryRepository categoryRepository;
    private final BackupDatumRepository backupDatumRepository;

    String titleClass = "span.title";
    String contentClass= "u";
    String priceClass="em.prc_c";
    String imageClass="baseImage";

    public List<ItemDto> getDatas(Integer category){
          if(category ==100){
            List<String>list = getAllItems();
            return  getJsoupElements(list,category);
        } else if (category==0) {
            List<String>list = getAllItems();
            return  getJsoupElements(list,category);
        } else{
            List<String> list = getItemAddress(category);
            return  getJsoupElements(list,category);
        }
    }
    @Async
    @Scheduled(fixedDelay = 300000, initialDelay = 5000)
    public void scheduleUpdate(){

        List<BackupDatum> result = itemRepository.findAll()
                .stream()
                .map((element) -> modelMapper.map(element, BackupDatum.class))
                .toList();


        log.info(result.stream().map((element) -> modelMapper.map(element, BackupDatumDto.class)).toList().toString());
        log.info("스케쥴링 시작");
        backupDatumRepository.saveAll(result);
    }

    public Connection getJsoupConnection(String url){
        if(url == null){
            url = "http://localhost:8080/";
        }
        return Jsoup.connect(url);

    }


    public List<String> getItemAddress(Integer categoryId){
        List<String> list = new ArrayList<>();
        List<ItemDto> dataList =  itemRepository .findByCategoryId(categoryId)
                .stream().map((element) -> modelMapper.map(element, ItemDto.class))
                .toList();
        for (ItemDto data:dataList) {
            list.add(data.getItemAddress());
        }

        return list;

    }

    public List<String> getAllItems(){
        List<String> list = new ArrayList<>();
        List<ItemDto> dataList =  itemRepository.findAll()
                .stream().map((element) -> modelMapper.map(element, ItemDto.class))
                .toList();
        for (ItemDto data:dataList) {
            list.add(data.getItemAddress());
        }

        return list;

    }


    //검색할 element와 url을 가져온다.
    public List<ItemDto> getJsoupElements(List<String> url, Integer category ){
        List<Connection> conn = new ArrayList<>();
        List<ItemDto> saveList = new ArrayList<>();
        List<String> nodes = new ArrayList<>();


        for (int i = 0; i < url.size(); i++) {
            conn.add(getJsoupConnection(url.get(i)));
            log.info(url.get(i));
        }

        try{
                conn.parallelStream().forEach(item->{
                    String title = "";
                    String content = "";
                    String price = "";
                    String address = "";
                    String image = null;
                    try {
                        title = item.get().select(titleClass).text();
                    } catch (IOException e){
                        log.info("상품명 에러" + e);
                    }
                    try {
                        content = item.get().select(contentClass).text();
                    } catch (IOException e) {
                        log.info("컨텐츠 에러" + e);
                    }
                    try {
                        price = item.get().select(priceClass).text();
                    } catch (IOException e) {
                        log.info("가격 에러" + e);
                    }
                    try {
                        address =item.get().location();
                    } catch (IOException e) {
                        log.info("uri 에러" + e);
                    }
                    try {
                        image = Objects.requireNonNull(item.get().getElementById(imageClass)).attr("src");
                    } catch (IOException e) {
                        log.info("이미지 에러" + e);
                    }
                    String[] sortValue = price.split(" ");
                    price = sortValue[0].replace(",","");
                    Arrays.sort(sortValue);
                    log.info("상품명 : "+title);
                    log.info("상품설명 : "+content);
                    log.info("상품가격 : "+price);
                    log.info("상품주소 : "+ address);
                    log.info("상품이미지 : "+ image);
                    log.info("***********************************");


                    ItemDto itemDto = new ItemDto();
                    itemDto.setItemName(title);
                    itemDto.setItemPrice(Integer.parseInt(price));
                    itemDto.setItemContent(content);
                    itemDto.setItemAddress(address);
                    itemDto.setItemImage(image);


                    saveList.add(itemDto);
                });

           List<Item>  result =  saveList.stream().map((element) -> modelMapper.map(element, Item.class)).toList();

           result.parallelStream().forEach(data->{

               log.info("확인"+data.getItemName());
              Integer backPrice =  modelMapper.map(backupDatumRepository.findBackData(data.getItemName()), BackupDatumDto.class).getItemPrice();
               log.info( "뭐가 문제지?"+modelMapper.map(backupDatumRepository.findBackData(data.getItemName()), BackupDatumDto.class).toString());
               itemRepository.updateItemNameAndItemPriceAndItemContentAndItemImageByItemAddress(data.getItemName(), data.getItemPrice(), data.getItemContent(), data.getItemImage(), data.getItemAddress(), data.getItemPrice() - backPrice);
           });




            LocalDateTime timestamp = LocalDateTime.now();
            if(category >0) {
                categoryRepository.updateUpdateTimeById(timestamp, category);
            }
            else{
                categoryRepository.updateUpdateTimeBy(timestamp);
            }
            return result.stream().map((element) -> modelMapper.map(element, ItemDto.class)).collect(Collectors.toList());

        }catch (Exception e){
            log.info("크롤링 에러"+e.getMessage());
            return null;
        }
    }



}
