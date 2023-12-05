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



}
