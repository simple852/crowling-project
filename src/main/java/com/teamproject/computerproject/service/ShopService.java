package com.teamproject.computerproject.service;

import com.teamproject.computerproject.dto.ItemDto;
import com.teamproject.computerproject.repositery.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ShopService {
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;


    public List<ItemDto> getItems(Integer categoryId, Pageable page) {
        List<ItemDto> dataList;
        Long count = 0L;
        count = totalCountItem(categoryId);
        dataList = (categoryId == 0?itemRepository.findAllOrder(page):itemRepository.findByCategoryIdOrderByItemGapDesc(categoryId, page))
                .stream().map((element) -> modelMapper.map(element, ItemDto.class))
                .toList();
        dataList.get(0).setTotalItemCount(count);
        return dataList;
    }

    public Long totalCountItem(Integer categoryId){
        if(categoryId == 0){
          return   itemRepository.count();
        }else{
            return itemRepository.countByCategoryId(categoryId);

        }
    }



}
