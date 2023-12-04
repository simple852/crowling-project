package com.teamproject.computerproject.service;


import com.teamproject.computerproject.repositery.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class CommunicationTest {

    @Autowired
    private  DataController dataController;
    @Autowired
    private  CommunicationService communicationService;
    private ItemRepository itemRepository;



}
