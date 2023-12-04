package com.teamproject.computerproject.controller;

import com.teamproject.computerproject.dto.ItemDto;
import com.teamproject.computerproject.dto.request.ParameterDto;
import com.teamproject.computerproject.repositery.ItemRepository;
import com.teamproject.computerproject.service.CommunicationService;
import com.teamproject.computerproject.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;
    private final ShopService shopService;
    private final CommunicationService communicationService;

    @GetMapping("/main")
    public void shop() {

    }

    @GetMapping("/getItems")
    public @ResponseBody List<ItemDto> getItems(@RequestParam("category") Integer categoryId, @PageableDefault(page = 0, size = 10)Pageable page) {
        communicationService.getDatas(categoryId);
        return shopService.getItems(categoryId, page);
    }


//    @PostMapping("/jsoup")
//    public List<String> updateDataS(@RequestBody ParameterDto parameter){
//
//        return  communicationService.getDatas(parameter );
//    }





}
