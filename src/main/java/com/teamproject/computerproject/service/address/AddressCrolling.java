//package com.teamproject.computerproject.service.address;
//
//import com.teamproject.computerproject.domain.Item;
//import com.teamproject.computerproject.dto.ItemDto;
//import lombok.extern.slf4j.Slf4j;
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//@Slf4j
//public class AddressCrolling {
//
//    // item table에 카테고리 파라미터에 맞는 url을 가져와서 반환한다.
//    public List<String> getItemAddress(Integer categoryId){
//        List<String> list = new ArrayList<>();
////        List<ItemDto> dataList =  itemRepository.findByCategoryId(categoryId)
////                .stream().map((element) -> modelMapper.map(element, ItemDto.class))
////                .toList();
////        for (ItemDto data:dataList) {
////            list.add(data.getItemAddress());
////        }
//
//        return list;
//
//    }
//    //크롤링 웹url 연결 부분 성공시 jsoup.connect 반환
//    public Connection getJsoupConnection(String url){
//        if(url == null){
//            url = "http://localhost:8080/";
//        }
//        return Jsoup.connect(url);
//
//    }
//
//
//    public List<ItemDto> getJsoupElements(List<String> url, Integer category ){
//        //연결될 객체들을 담는 list
//        List<Connection> conn = new ArrayList<>();
//        //크롤링해온 데이터를 임시 저장할 list
//        List<ItemDto> saveList = new ArrayList<>();
//
//
////        getJsoupConnection()에 파라미터로 전달받은 url을 넣고 5초 대기 시간을 가진다
//        // 바로 실행하니 ip밴 당할 위험이 있음.
//        for (int i = 0; i < url.size(); i++) {
//            conn.add(getJsoupConnection(url.get(i)).timeout(5000)
//
//            );
//        }
//
//        try{
//            //연결된 url에서 각 객체를 순회한다
//            conn.parallelStream().forEach(item->{
//                //가져온 데이터를 담을 변수
//                String title = "";
//                String content = "";
//                String price = "";
//                String address = "";
//                String image = null;
//
//                // 전역변수에 설정해놓은 각 필요 태그.class이름을 전달받고 값을 가져온다
//                try {
//                    title = item.get().select(titleClass).text();
//                    content = item.get().select(contentClass).text();
//                    price = item.get().select(priceClass).text();
//                    address =item.get().location();
//                    image = Objects.requireNonNull(item.get().getElementById(imageClass)).attr("src");
//                } catch (IOException e){
//                    log.info("상품명 에러" + e);
//                }
//                //가격 같은경우 여러개가 들고 와지기 때문에 split로 나눈다
//                String[] sortValue = price.split(" ");
//                // 여러개중 가장 먼저 나온 0번인덱스 데이터를 가져온다
//                price = sortValue[0].replace(",","");
//                Arrays.sort(sortValue);
//                //가져온 데이터를 dto에 담는 메서드에 담아주고 그 객체를 savelist에 추가한다
//                saveList.add(putDto(title,content,price,address,image));
//            });
//
//
//
//
//
//
//        }catch (Exception e){
//            log.info("크롤링 에러"+e.getMessage());
//            return null;
//        }
//    }
//
//    //크롤링해온 데이터를 dto객체에 담아서 리턴한다
//    public ItemDto putDto(String title,String content ,String price,String address,String image){
//        ItemDto itemDto = new ItemDto();
//        itemDto.setItemName(title);
//        itemDto.setItemPrice(Integer.parseInt(price));
//        itemDto.setItemContent(content);
//        itemDto.setItemAddress(address);
//        itemDto.setItemImage(image);
//        log.info(title+" dto에 담음");
//        return itemDto;
//    }
//}
