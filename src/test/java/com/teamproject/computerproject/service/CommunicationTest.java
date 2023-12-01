package com.teamproject.computerproject.service;


import com.teamproject.computerproject.controller.DataController;
import com.teamproject.computerproject.repositery.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class CommunicationTest {

    @Autowired
    private  DataController dataController;
    @Autowired
    private  CommunicationService communicationService;
    private ItemRepository itemRepository;


