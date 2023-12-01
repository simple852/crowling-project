package com.teamproject.computerproject.controller;


import com.teamproject.computerproject.dto.ItemDto;
import com.teamproject.computerproject.dto.request.ParameterDto;
import com.teamproject.computerproject.dto.request.RequestItemDto;
import com.teamproject.computerproject.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;


    @PostMapping("/save")
    public void saveItem(RequestItemDto itemDto){

        itemService.saveItem(itemDto);
    }

    @PostMapping("/delete")
    public void deleteItem(Integer itemId){

        itemService.deleteItem(itemId);
    }



}
