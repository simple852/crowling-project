package com.teamproject.computerproject.controller;


import com.teamproject.computerproject.dto.request.ParameterDto;
import com.teamproject.computerproject.service.CommunicationService;
import lombok.RequiredArgsConstructor;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/data")
public class DataController {
    private final CommunicationService communicationService;

    @PostMapping("/jsoup")
    public List<String> test(@RequestBody  ParameterDto parameter){

        return  communicationService.getDatas(parameter );
    }


}
