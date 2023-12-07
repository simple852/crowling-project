package com.teamproject.computerproject.service;

import com.teamproject.computerproject.domain.BackupDatum;
import com.teamproject.computerproject.domain.Item;

import com.teamproject.computerproject.dto.ItemDto;
import com.teamproject.computerproject.dto.request.NotificationDto;
import com.teamproject.computerproject.repositery.BackupDatumRepository;
import com.teamproject.computerproject.repositery.CategoryRepository;
import com.teamproject.computerproject.repositery.ItemImageRepository;
import com.teamproject.computerproject.repositery.ItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private final NotificationService notificationService;
    String titleClass = "span.title";
    String contentClass= "u";
    String priceClass="em.prc_c";
    String imageClass="baseImage";


    //조건에 맞는 메서드를 실행시킨다
    // 이 메서드에 위임하였음
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
    @Scheduled(fixedDelay = 1600000)
    //가격 변동 사항 업데이트하는 로직
    public void updateGapPrice(){
        List<Item> itemList = itemRepository.findAll();
        LocalDateTime localDate = LocalDateTime.now();
        String localDate2 =  localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        itemList.parallelStream().forEach(data->{
            int backPrice = 0;
            if(backupDatumRepository.findByCreatedDateAndItemAddressOrderByItemPriceDesc(LocalDate.parse(localDate2),data.getItemAddress()).get().getItemPrice() == null){
                backPrice= 0;
            }else{
                backPrice= backupDatumRepository.findByCreatedDateAndItemAddressOrderByItemPriceDesc(LocalDate.parse(localDate2),data.getItemAddress()).get().getItemPrice();

            }
            itemRepository.updateItemGapByItemAddress(data.getItemPrice()-backPrice,data.getItemAddress());
        });
    }

    @Async
    @Scheduled(fixedDelay = 1200000, initialDelay = 60000) // 서버 시작후 1분 => 매 1시간마다 실행 하는 메서드
    public void scheduleUpdate(){
        //크롤링 메서드
        getDatas(0);
        //현재 가격을 저장하는 item table 데이터를 이전 가격 저장하는 테이블로 insert한다
        List<BackupDatum> result = itemRepository.findAll()
                .stream()
                .map((element) -> modelMapper.map(element, BackupDatum.class))
                .toList();

        backupDatumRepository.saveAll(result);
        // item table에서 모든 데이터를 가져오고 가격이 내려갔을 시 notification 메서드 실행
        List<Item> items = itemRepository.findAll();
        items.stream().map((element) -> modelMapper.map(element, ItemDto.class)).toList().stream().forEach(data->{

            int gapPrice= 0;
            if(data.getItemGap() != null){

                gapPrice = data.getItemPrice();

            }
            if(gapPrice < 5000 ){
                NotificationDto notificationDto = NotificationDto.builder().title("파격 할인!!!!").body(data.getItemName() + "이 할인 중 입니다!").itemUrl(data.getItemAddress()).build();
                notificationService.send_notification(notificationDto);
            }
        });

    }


    // item table에 카테고리 파라미터에 맞는 url을 가져와서 반환한다.
    public List<String> getItemAddress(Integer categoryId){
        List<String> list = new ArrayList<>();
        List<ItemDto> dataList =  itemRepository.findByCategoryId(categoryId)
                .stream().map((element) -> modelMapper.map(element, ItemDto.class))
                .toList();
        for (ItemDto data:dataList) {
            list.add(data.getItemAddress());
        }

        return list;

    }
    //카테고리 상관없이 모든 url을 가져온다.
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

    //크롤링 웹url 연결 부분 성공시 jsoup.connect 반환
    public Connection getJsoupConnection(String url){
        if(url == null){
            url = "http://localhost:8080/";
        }
        return Jsoup.connect(url);

    }




    //검색할 element와 url을 가져온다.

    public List<ItemDto> getJsoupElements(List<String> url, Integer category ){
        //연결될 객체들을 담는 list
        List<Connection> conn = new ArrayList<>();
        //크롤링해온 데이터를 임시 저장할 list
        List<ItemDto> saveList = new ArrayList<>();


//        getJsoupConnection()에 파라미터로 전달받은 url을 넣고 5초 대기 시간을 가진다
        // 바로 실행하니 ip밴 당할 위험이 있음.
        for (int i = 0; i < url.size(); i++) {
            conn.add(getJsoupConnection(url.get(i)).timeout(5000)

            );
        }

        try{
            //연결된 url에서 각 객체를 순회한다
                conn.parallelStream().forEach(item->{
                    //가져온 데이터를 담을 변수
                    String title = "";
                    String content = "";
                    String price = "";
                    String address = "";
                    String image = null;

                    // 전역변수에 설정해놓은 각 필요 <태그>.class이름을 전달받고 값을 가져온다
                    try {
                        title = item.get().select(titleClass).text();
                        content = item.get().select(contentClass).text();
                        price = item.get().select(priceClass).text();
                        address =item.get().location();
                        image = Objects.requireNonNull(item.get().getElementById(imageClass)).attr("src");
                    } catch (IOException e){
                        log.info("상품명 에러" + e);
                    }
                    //가격 같은경우 여러개가 들고 와지기 때문에 split로 나눈다
                    String[] sortValue = price.split(" ");
                    // 여러개중 가장 먼저 나온 0번인덱스 데이터를 가져온다
                    price = sortValue[0].replace(",","");
                    Arrays.sort(sortValue);
                    //가져온 데이터를 dto에 담는 메서드에 담아주고 그 객체를 savelist에 추가한다
                    saveList.add(putDto(title,content,price,address,image));
                });


            // savelist 추가한 데이터를 item 엔티티 형태로 변환한다.
            List<Item>  result = saveList.stream().map((element) -> modelMapper.map(element, Item.class)).toList();


            LocalDateTime timestamp = LocalDateTime.now();

            //업데이트한 시간을 카테고리에 업데이트한다.
            if(category >0) {
                categoryRepository.updateUpdateTimeById(timestamp, category);
            }
            else{
                categoryRepository.updateUpdateTimeBy(timestamp);
            }

            result.parallelStream().forEach(data->{
                itemRepository.updateItemNameAndItemPriceAndItemContentAndItemImageByItemAddress(data.getItemName(),data.getItemPrice(),data.getItemContent(),data.getItemImage(),data.getItemAddress());

            });

            log.info("************************************************************");
            log.info("크롤링 완료");
            return result.stream().map((element) -> modelMapper.map(element, ItemDto.class)).collect(Collectors.toList());

        }catch (Exception e){
            log.info("크롤링 에러"+e.getMessage());
            return null;
        }
    }


    //크롤링해온 데이터를 dto객체에 담아서 리턴한다
    public ItemDto putDto(String title,String content ,String price,String address,String image){
        ItemDto itemDto = new ItemDto();
        itemDto.setItemName(title);
        itemDto.setItemPrice(Integer.parseInt(price));
        itemDto.setItemContent(content);
        itemDto.setItemAddress(address);
        itemDto.setItemImage(image);
        log.info(title+" dto에 담음");
        return itemDto;
    }



}
