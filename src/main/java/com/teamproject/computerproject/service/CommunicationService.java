package com.teamproject.computerproject.service;

import com.teamproject.computerproject.domain.Item;
import com.teamproject.computerproject.domain.ItemImage;
import com.teamproject.computerproject.dto.ItemDto;
import com.teamproject.computerproject.dto.request.ParameterDto;
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
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommunicationService {
    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final CategoryRepository categoryRepository;

    String titleClass = "span.title";
    String contentClass= "u";
    String priceClass="em.prc_c";
    String imageClass="baseImage";





    public List<String> getDatas(Integer category){
      
        List<String> list = getItemAddress(category);

        return  getJsoupElements(list,category);
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


    //검색할 element와 url을 가져온다.
    public List<String> getJsoupElements(List<String> url, Integer category ){

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
               log.info("db 넣기전"+data.getItemImage());
               itemRepository.updateItemNameAndItemPriceAndItemContentAndItemImageByItemAddress(data.getItemName(), data.getItemPrice(), data.getItemContent(), data.getItemImage(), data.getItemAddress());
           });



            LocalDateTime timestamp = LocalDateTime.now();
            categoryRepository.updateUpdateTimeById(timestamp,category);
            return nodes;

        }catch (Exception e){
            log.info("크롤링 에러"+e.getMessage());
            return null;
        }
    }



}
