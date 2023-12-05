package com.teamproject.computerproject.service;


import com.teamproject.computerproject.domain.Item;
import com.teamproject.computerproject.dto.ItemDto;
import com.teamproject.computerproject.dto.request.ParameterDto;
import com.teamproject.computerproject.dto.request.RequestItemDto;
import com.teamproject.computerproject.repositery.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;


    public void saveItem(RequestItemDto itemDto){
        itemRepository.save(modelMapper.map(itemDto, Item.class));
    }


    public void deleteItem(Integer itemId) {
        itemRepository.deleteById(itemId);
    }


    public List<ItemDto> getItem(Integer categoryId, Pageable page) {
        if(categoryId == 0){
          return itemRepository.findAll(page).stream().map((element) -> modelMapper.map(element, ItemDto.class)).collect(Collectors.toList());
        }else{
           return itemRepository.findByCategoryId(categoryId).stream().map((element) -> modelMapper.map(element, ItemDto.class)).collect(Collectors.toList());
        }

    }
}
