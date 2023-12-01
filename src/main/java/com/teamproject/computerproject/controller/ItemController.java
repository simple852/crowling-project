package com.teamproject.computerproject.controller;


import com.teamproject.computerproject.dto.ItemDto;
import com.teamproject.computerproject.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/save")
public class ItemController {
    private final ItemService itemService;


    @PostMapping("/item")
    public void saveItem(ItemDto itemDto){

        itemService.saveItem(itemDto);
    }



}
