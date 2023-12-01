package com.teamproject.computerproject.controller;

import com.teamproject.computerproject.dto.ItemDto;
import com.teamproject.computerproject.repositery.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
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

    @GetMapping("/main")
    public void shop() {
    }
    @PostMapping("/getItems")
    public @ResponseBody Map<String, Object> getItems(Model model) {
        int categoryId = 1;
        Map<String, Object> result = new HashMap<String, Object>();

        List<ItemDto> dataList =  itemRepository.findByCategoryId(categoryId)
                .stream().map((element) -> modelMapper.map(element, ItemDto.class))
                .toList();
        result.put("dataList", dataList);
        return result;
    }
}
