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
    @Scheduled(fixedDelay = 600000, initialDelay = 5000)
    public void scheduleUpdate(){
        getDatas(0);

        List<BackupDatum> result = itemRepository.findAll()
                .stream()
                .map((element) -> modelMapper.map(element, BackupDatum.class))
                .toList();

        backupDatumRepository.saveAll(result);
        getDatas(0);
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


        for (int i = 0; i < url.size(); i++) {
            conn.add(getJsoupConnection(url.get(i)).timeout(5000)

            );
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

                    log.info("*****************************************");
                    log.info("상품이름"+title);
                    log.info("가격"+price);
                    log.info("내용"+content);
                    log.info("주소"+address);
                    log.info("이미지"+image);
                    log.info("*****************************************");
                    saveList.add(putDto(title,content,price,address,image));
                });

            log.info("세이브 리스트"+saveList.toString());
            List<Item>  result = saveList.stream().map((element) -> modelMapper.map(element, Item.class)).collect(Collectors.toList());
           log.info("최종 db 넣기전");


           result.parallelStream().forEach(data->{
               int backPrice=0;
              if( modelMapper.map(backupDatumRepository.findBackData(data.getItemName(), data.getItemAddress()), BackupDatumDto.class).getItemPrice() == null){
                  backPrice = 0;
              }else{
                   backPrice = modelMapper.map(backupDatumRepository.findBackData(data.getItemName(), data.getItemAddress()), BackupDatumDto.class).getItemPrice();
              }

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


    public ItemDto putDto(String title,String content ,String price,String address,String image){
        ItemDto itemDto = new ItemDto();
        itemDto.setItemName(title);
        itemDto.setItemPrice(Integer.parseInt(price));
        itemDto.setItemContent(content);
        itemDto.setItemAddress(address);
        itemDto.setItemImage(image);
        log.info("dto에 담음");
        return itemDto;
    }


}
