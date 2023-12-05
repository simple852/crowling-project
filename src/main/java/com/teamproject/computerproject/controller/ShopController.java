package com.teamproject.computerproject.controller;

import com.teamproject.computerproject.dto.ItemDto;
import com.teamproject.computerproject.dto.request.NotificationDto;
import com.teamproject.computerproject.dto.request.ParameterDto;
import com.teamproject.computerproject.repositery.ItemRepository;
import com.teamproject.computerproject.service.CommunicationService;
import com.teamproject.computerproject.service.ItemService;
import com.teamproject.computerproject.service.NotificationService;
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
    private final NotificationService notificationService;

    private final ItemService itemService;
    @GetMapping("/main")
    public void shop() {

    }

    @GetMapping("/getItems")
    public @ResponseBody List<ItemDto> getItems(@RequestParam("category") Integer categoryId, @PageableDefault(page = 0, size = 10)Pageable page) {
//        communicationService.getDatas(categoryId);

        return shopService.getItems(categoryId, page);
    }

    @GetMapping("/reload")
    public @ResponseBody String reload(@RequestParam("categoryId") Integer categoryId) {
        communicationService.getDatas(categoryId);
        return "success";
    }




    @PostMapping("/notification")
    public @ResponseBody void sendMessage(@RequestBody NotificationDto notificationDto){

         notificationService.send_notification(notificationDto );
    }





}
